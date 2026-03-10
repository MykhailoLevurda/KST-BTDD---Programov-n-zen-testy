package cz.kst.btdd.service;

import cz.kst.btdd.domain.RentalStatus;
import cz.kst.btdd.domain.UserRole;
import cz.kst.btdd.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Jednotkové testy RentalService s mocky repozitářů (test doubles).
 * Struktura AAA, FIRST.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("RentalService")
class RentalServiceTest {

    @Mock
    private RentalRepository rentalRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ToolRepository toolRepository;

    @InjectMocks
    private RentalService rentalService;

    private EntityUser customer;
    private EntityUser employee;
    private EntityTool tool;
    private LocalDate start;
    private LocalDate end;

    @BeforeEach
    void setUp() {
        customer = new EntityUser();
        customer.setId(1L);
        customer.setName("Jan");
        customer.setRole(UserRole.CUSTOMER);
        employee = new EntityUser();
        employee.setId(2L);
        employee.setName("Marie");
        employee.setRole(UserRole.EMPLOYEE);
        tool = new EntityTool();
        tool.setId(10L);
        tool.setName("Kladivo");
        tool.setDailyPriceCzk(new BigDecimal("100"));
        tool.setLateFeePerDay(new BigDecimal("50"));
        start = LocalDate.of(2025, 3, 1);
        end = LocalDate.of(2025, 3, 5);
    }

    @Nested
    @DisplayName("createReservation")
    class CreateReservation {

        @Test
        @DisplayName("vytvoří rezervaci když není kolize")
        void createsReservationWhenNoOverlap() {
            when(userRepository.findById(1L)).thenReturn(Optional.of(customer));
            when(toolRepository.findById(10L)).thenReturn(Optional.of(tool));
            when(rentalRepository.findOverlappingForTool(eq(10L), any(), eq(start), eq(end)))
                    .thenReturn(List.of());
            when(rentalRepository.save(any(EntityRental.class))).thenAnswer(i -> {
                EntityRental r = i.getArgument(0);
                r.setId(100L);
                return r;
            });

            EntityRental result = rentalService.createReservation(1L, 10L, start, end);

            assertThat(result.getStatus()).isEqualTo(RentalStatus.RESERVED);
            assertThat(result.getUser()).isEqualTo(customer);
            assertThat(result.getTool()).isEqualTo(tool);
            assertThat(result.getStartDate()).isEqualTo(start);
            assertThat(result.getEndDate()).isEqualTo(end);
        }

        @Test
        @DisplayName("vyhodí výjimku při kolizi období (pravidlo kapacity/duplicity)")
        void throwsWhenOverlap() {
            when(userRepository.findById(1L)).thenReturn(Optional.of(customer));
            when(toolRepository.findById(10L)).thenReturn(Optional.of(tool));
            EntityRental existing = new EntityRental();
            existing.setStatus(RentalStatus.RESERVED);
            when(rentalRepository.findOverlappingForTool(eq(10L), any(), eq(start), eq(end)))
                    .thenReturn(List.of(existing));

            assertThatThrownBy(() -> rentalService.createReservation(1L, 10L, start, end))
                    .isInstanceOf(BusinessException.class)
                    .hasMessageContaining("neexistuje");
        }

        @Test
        @DisplayName("vyhodí výjimku při neplatném období")
        void throwsWhenInvalidPeriod() {
            assertThatThrownBy(() -> rentalService.createReservation(1L, 10L, end, start))
                    .isInstanceOf(BusinessException.class);
        }
    }

    @Nested
    @DisplayName("activate")
    class Activate {

        @Test
        @DisplayName("pouze zaměstnanec může aktivovat")
        void onlyEmployeeCanActivate() {
            when(userRepository.findById(2L)).thenReturn(Optional.of(employee));
            EntityRental reserved = createEntityRental(RentalStatus.RESERVED);
            when(rentalRepository.findById(100L)).thenReturn(Optional.of(reserved));
            when(rentalRepository.save(any(EntityRental.class))).thenAnswer(i -> i.getArgument(0));

            EntityRental result = rentalService.activate(100L, 2L);

            assertThat(result.getStatus()).isEqualTo(RentalStatus.ACTIVE);
        }

        @Test
        @DisplayName("zákazník nesmí aktivovat")
        void customerCannotActivate() {
            when(userRepository.findById(1L)).thenReturn(Optional.of(customer));

            assertThatThrownBy(() -> rentalService.activate(100L, 1L))
                    .isInstanceOf(BusinessException.class)
                    .hasMessageContaining("zaměstnanec");
        }
    }

    @Nested
    @DisplayName("returnRental")
    class ReturnRental {

        @Test
        @DisplayName("pouze zaměstnanec může evidovat vrácení")
        void onlyEmployeeCanReturn() {
            when(userRepository.findById(1L)).thenReturn(Optional.of(customer));
            // returnRental nejdřív kontroluje roli – findById na rental se nevolá, proto nestubujeme

            assertThatThrownBy(() -> rentalService.returnRental(100L, 1L, end))
                    .isInstanceOf(BusinessException.class)
                    .hasMessageContaining("zaměstnanec");
        }

        @Test
        @DisplayName("po vrácení nastaví cenu a datum vrácení")
        void setsPriceAndReturnDate() {
            when(userRepository.findById(2L)).thenReturn(Optional.of(employee));
            EntityRental active = createEntityRental(RentalStatus.ACTIVE);
            active.setTool(tool);
            active.setStartDate(start);
            active.setEndDate(end);
            when(rentalRepository.findById(100L)).thenReturn(Optional.of(active));
            when(rentalRepository.save(any(EntityRental.class))).thenAnswer(i -> i.getArgument(0));

            EntityRental result = rentalService.returnRental(100L, 2L, end);

            assertThat(result.getStatus()).isEqualTo(RentalStatus.RETURNED);
            assertThat(result.getActualReturnDate()).isEqualTo(end);
            assertThat(result.getTotalPriceCzk()).isEqualByComparingTo("400"); // 4 dny * 100
        }
    }

    @Nested
    @DisplayName("cancelReservation")
    class CancelReservation {

        @Test
        @DisplayName("zákazník může zrušit vlastní rezervaci")
        void customerCanCancelOwn() {
            EntityRental reserved = createEntityRental(RentalStatus.RESERVED);
            reserved.setUser(customer);
            when(rentalRepository.findById(100L)).thenReturn(Optional.of(reserved));
            when(rentalRepository.save(any(EntityRental.class))).thenAnswer(i -> i.getArgument(0));

            EntityRental result = rentalService.cancelReservation(100L, 1L);

            assertThat(result.getStatus()).isEqualTo(RentalStatus.CANCELLED);
        }

        @Test
        @DisplayName("nelze zrušit cizí rezervaci")
        void cannotCancelOthers() {
            EntityRental reserved = createEntityRental(RentalStatus.RESERVED);
            reserved.setUser(customer);
            when(rentalRepository.findById(100L)).thenReturn(Optional.of(reserved));

            assertThatThrownBy(() -> rentalService.cancelReservation(100L, 999L))
                    .isInstanceOf(BusinessException.class)
                    .hasMessageContaining("vlastní");
        }
    }

    private static EntityRental createEntityRental(RentalStatus status) {
        EntityRental r = new EntityRental();
        r.setId(100L);
        r.setStatus(status);
        return r;
    }
}
