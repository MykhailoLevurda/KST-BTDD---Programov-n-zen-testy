package cz.kst.btdd.domain;

/**
 * Stav půjčky. Povolené přechody: RESERVED→ACTIVE→RETURNED, RESERVED→CANCELLED.
 */
public enum RentalStatus {
    RESERVED,
    ACTIVE,
    RETURNED,
    CANCELLED
}
