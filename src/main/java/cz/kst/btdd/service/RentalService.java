package cz.kst.btdd.service;

import cz.kst.btdd.domain.RentalPriceCalculator;
import cz.kst.btdd.domain.RentalStatus;
import cz.kst.btdd.domain.Rental;
import cz.kst.btdd.domain.UserRole;
import cz.kst.btdd.persistence.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Služba pro vytváření rezervací, aktivaci a vrácení půjček.
 * Aplikuje business pravidla: stavové přechody, kapacita/kolize, role, duplicity, cena.
 */
@Service
public class RentalService {

    private static final List<RentalStatus> BLOCKING_STATUSES = List.of(RentalStatus.RESERVED, RentalStatus.ACTIVE);

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final ToolRepository toolRepository;

    public RentalService(RentalRepository rentalRepository, UserRepository userRepository, ToolRepository toolRepository) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.toolRepository = toolRepository;
    }

    /**
     * Vytvoří rezervaci. Kontroluje překrývání (kapacita) – stejné nářadí nesmí mít dvě půjčky ve stejném období (zabránění duplicitě i kolizi).
     */
    @Transactional
    public EntityRental createReservation(Long userId, Long toolId, LocalDate start, LocalDate end) {
        if (start == null || end == null || end.isBefore(start)) {
            throw new BusinessException("Neplatné období: start a end musí být vyplněny a end >= start");
        }
        EntityUser user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Uživatel neexistuje: " + userId));
        EntityTool tool = toolRepository.findById(toolId)
                .orElseThrow(() -> new BusinessException("Nářadí neexistuje: " + toolId));

        List<EntityRental> overlapping = rentalRepository.findOverlappingForTool(toolId, BLOCKING_STATUSES, start, end);
        if (!overlapping.isEmpty()) {
            throw new BusinessException("Nářadí je v tomto období již rezervované nebo zapůjčené (kolize / duplicita)");
        }

        EntityRental rental = new EntityRental();
        rental.setUser(user);
        rental.setTool(tool);
        rental.setStartDate(start);
        rental.setEndDate(end);
        rental.setStatus(RentalStatus.RESERVED);
        return rentalRepository.save(rental);
    }

    /**
     * Aktivuje rezervaci (přejde na ACTIVE). Pouze zaměstnanec.
     */
    @Transactional
    public EntityRental activate(Long rentalId, Long employeeUserId) {
        requireEmployee(employeeUserId);
        EntityRental entity = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new BusinessException("Půjčka neexistuje: " + rentalId));
        applyStateTransition(entity, Rental::activate);
        return rentalRepository.save(entity);
    }

    /**
     * Eviduje vrácení a dopočítá cenu. Pouze zaměstnanec.
     */
    @Transactional
    public EntityRental returnRental(Long rentalId, Long employeeUserId, LocalDate actualReturnDate) {
        requireEmployee(employeeUserId);
        EntityRental entity = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new BusinessException("Půjčka neexistuje: " + rentalId));
        applyStateTransition(entity, Rental::markReturned);
        if (actualReturnDate != null) {
            entity.setActualReturnDate(actualReturnDate);
            BigDecimal price = RentalPriceCalculator.calculate(
                    entity.getTool().getDailyPriceCzk(),
                    entity.getTool().getLateFeePerDay() != null ? entity.getTool().getLateFeePerDay() : BigDecimal.ZERO,
                    entity.getStartDate(),
                    entity.getEndDate(),
                    actualReturnDate
            );
            entity.setTotalPriceCzk(price);
        }
        return rentalRepository.save(entity);
    }

    /**
     * Zruší rezervaci (RESERVED -> CANCELLED). Může volat zákazník pro vlastní rezervaci.
     */
    @Transactional
    public EntityRental cancelReservation(Long rentalId, Long userId) {
        EntityRental entity = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new BusinessException("Půjčka neexistuje: " + rentalId));
        if (!entity.getUser().getId().equals(userId)) {
            throw new BusinessException("Zrušit lze pouze vlastní rezervaci");
        }
        applyStateTransition(entity, Rental::cancel);
        return rentalRepository.save(entity);
    }

    private void requireEmployee(Long userId) {
        EntityUser user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Uživatel neexistuje: " + userId));
        if (user.getRole() != UserRole.EMPLOYEE) {
            throw new BusinessException("Operaci smí provést pouze zaměstnanec");
        }
    }

    private void applyStateTransition(EntityRental entity, java.util.function.Consumer<Rental> action) {
        Rental domain = new Rental();
        domain.setStatus(entity.getStatus());
        action.accept(domain);
        entity.setStatus(domain.getStatus());
    }
}
