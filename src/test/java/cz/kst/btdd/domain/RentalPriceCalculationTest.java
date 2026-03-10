package cz.kst.btdd.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Jednotkové testy: výpočet ceny půjčky a pozdního poplatku (business pravidlo 2).
 */
@DisplayName("Výpočet ceny Rental")
class RentalPriceCalculationTest {

    private static final BigDecimal DAILY_RATE = new BigDecimal("100");
    private static final BigDecimal LATE_FEE_PER_DAY = new BigDecimal("50");

    @Test
    @DisplayName("cena = denní sazba × počet dnů pro vrácení v termínu")
    void price_isDailyRateTimesDaysWhenReturnedOnTime() {
        LocalDate start = LocalDate.of(2025, 3, 1);
        LocalDate end = LocalDate.of(2025, 3, 5);   // 4 dny
        BigDecimal price = RentalPriceCalculator.calculate(DAILY_RATE, LATE_FEE_PER_DAY, start, end, end);
        assertThat(price).isEqualByComparingTo("0"); // 4 * 100
    }

    @Test
    @DisplayName("při pozdním vrácení se přičte poplatek za každý den prodlení")
    void lateReturn_addsFeePerDay() {
        LocalDate start = LocalDate.of(2025, 3, 1);
        LocalDate plannedEnd = LocalDate.of(2025, 3, 3);  // 2 dny půjčky
        LocalDate actualReturn = LocalDate.of(2025, 3, 6); // 3 dny po termínu
        BigDecimal price = RentalPriceCalculator.calculate(DAILY_RATE, LATE_FEE_PER_DAY, start, plannedEnd, actualReturn);
        // Základ: 2 dny * 100 = 200, poplatek: 3 * 50 = 150, celkem 350
        assertThat(price).isEqualByComparingTo("350");
    }

    @Test
    @DisplayName("vrácení v den konce = bez poplatku")
    void returnOnEndDate_noLateFee() {
        LocalDate start = LocalDate.of(2025, 3, 1);
        LocalDate end = LocalDate.of(2025, 3, 4);
        BigDecimal price = RentalPriceCalculator.calculate(DAILY_RATE, LATE_FEE_PER_DAY, start, end, end);
        assertThat(price).isEqualByComparingTo("300"); // 3 dny
    }
}
