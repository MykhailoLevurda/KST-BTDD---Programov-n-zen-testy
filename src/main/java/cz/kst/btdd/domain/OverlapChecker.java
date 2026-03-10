package cz.kst.btdd.domain;

import java.time.LocalDate;

/**
 * Kontrola překrývání dvou intervalů (od–do). Použití: stejné nářadí nesmí mít dvě půjčky ve stejném čase.
 */
public final class OverlapChecker {

    private OverlapChecker() {}

    /** Vrátí true, pokud se intervaly [start1, end1] a [start2, end2] překrývají (včetně hranic). */
    public static boolean overlaps(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !end1.isBefore(start2) && !end2.isBefore(start1);
    }
}
