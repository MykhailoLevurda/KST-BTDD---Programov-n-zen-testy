package cz.kst.btdd.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Jednotkové testy: validace stavových přechodů půjčky (business pravidlo 1).
 * Struktura AAA, FIRST.
 */
@DisplayName("Stavové přechody Rental")
class RentalStateTransitionTest {

    @Nested
    @DisplayName("povolené přechody")
    class AllowedTransitions {

        @Test
        @DisplayName("RESERVED -> ACTIVE je povolen")
        void reservedToActive_isAllowed() {
            // Arrange
            Rental rental = createReservedRental();
            // Act
            rental.activate();
            // Assert
            assertThat(rental.getStatus()).isEqualTo(RentalStatus.ACTIVE);
        }

        @Test
        @DisplayName("RESERVED -> CANCELLED je povolen")
        void reservedToCancelled_isAllowed() {
            Rental rental = createReservedRental();
            rental.cancel();
            assertThat(rental.getStatus()).isEqualTo(RentalStatus.CANCELLED);
        }

        @Test
        @DisplayName("ACTIVE -> RETURNED je povolen")
        void activeToReturned_isAllowed() {
            Rental rental = createActiveRental();
            rental.markReturned();
            assertThat(rental.getStatus()).isEqualTo(RentalStatus.RETURNED);
        }
    }

    @Nested
    @DisplayName("nepovolené přechody")
    class ForbiddenTransitions {

        @Test
        @DisplayName("z RESERVED nelze přejít přímo na RETURNED")
        void reservedToReturned_isForbidden() {
            Rental rental = createReservedRental();
            assertThatThrownBy(rental::markReturned)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("ACTIVE");
        }

        @Test
        @DisplayName("vrátit již vrácenou půjčku nelze")
        void returnedCannotBeReturnedAgain() {
            Rental rental = createActiveRental();
            rental.markReturned();
            assertThatThrownBy(rental::markReturned)
                    .isInstanceOf(IllegalStateException.class);
        }

        @Test
        @DisplayName("zrušenou rezervaci nelze aktivovat")
        void cancelledCannotBeActivated() {
            Rental rental = createReservedRental();
            rental.cancel();
            assertThatThrownBy(rental::activate)
                    .isInstanceOf(IllegalStateException.class);
        }
    }

    private static Rental createReservedRental() {
        Rental r = new Rental();
        r.setStatus(RentalStatus.RESERVED);
        return r;
    }

    private static Rental createActiveRental() {
        Rental r = new Rental();
        r.setStatus(RentalStatus.ACTIVE);
        return r;
    }
}
