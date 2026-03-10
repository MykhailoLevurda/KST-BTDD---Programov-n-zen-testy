package cz.kst.btdd.domain;

/**
 * Půjčka – vazba mezi uživatelem a nářadím na období.
 * Validace stavových přechodů: RESERVED→ACTIVE→RETURNED, RESERVED→CANCELLED.
 */
public class Rental {

    private Long id;
    private RentalStatus status = RentalStatus.RESERVED;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }

    /** Povoleno pouze z RESERVED. */
    public void activate() {
        if (status != RentalStatus.RESERVED) {
            throw new IllegalStateException(
                    "Aktivovat lze pouze rezervaci (stav RESERVED), aktuální stav: " + status);
        }
        this.status = RentalStatus.ACTIVE;
    }

    /** Povoleno pouze z RESERVED. */
    public void cancel() {
        if (status != RentalStatus.RESERVED) {
            throw new IllegalStateException("Zrušit lze pouze rezervaci (stav RESERVED)");
        }
        this.status = RentalStatus.CANCELLED;
    }

    /** Povoleno pouze z ACTIVE. Nelze „vrátit“ již vrácenou. */
    public void markReturned() {
        if (status != RentalStatus.ACTIVE) {
            throw new IllegalStateException(
                    "Vrácení lze evidovat pouze u aktivní půjčky (stav ACTIVE), aktuální: " + status);
        }
        this.status = RentalStatus.RETURNED;
    }
}
