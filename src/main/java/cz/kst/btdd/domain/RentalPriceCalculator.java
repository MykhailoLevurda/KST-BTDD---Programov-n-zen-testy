package cz.kst.btdd.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Výpočet ceny půjčky: denní sazba × dny + pozdní poplatek za každý den po plánovaném konci.
 */
public final class RentalPriceCalculator {

    private RentalPriceCalculator() {}

    /**
     * @param dailyRate       cena za den (Kč)
     * @param lateFeePerDay   poplatek za den prodlení (Kč)
     * @param start           začátek půjčky
     * @param plannedEnd      plánovaný konec
     * @param actualReturn    skutečné datum vrácení (pro výpočet prodlení)
     */
    public static BigDecimal calculate(BigDecimal dailyRate, BigDecimal lateFeePerDay,
                                       LocalDate start, LocalDate plannedEnd, LocalDate actualReturn) {
        // Počet dnů = rozdíl start–plannedEnd (konec půjčky v den vrácení = end je poslední den započítaný)
        long days = ChronoUnit.DAYS.between(start, plannedEnd);
        if (days < 1) {
            days = 1;
        }
        BigDecimal base = dailyRate.multiply(BigDecimal.valueOf(days));

        long lateDays = 0;
        if (actualReturn != null && actualReturn.isAfter(plannedEnd)) {
            lateDays = ChronoUnit.DAYS.between(plannedEnd, actualReturn);
        }
        BigDecimal lateFee = lateFeePerDay.multiply(BigDecimal.valueOf(lateDays));

        return base.add(lateFee);
    }
}
