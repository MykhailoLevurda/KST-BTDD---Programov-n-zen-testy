package cz.kst.btdd.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Kontrola překrývání období")
class OverlapCheckerTest {

    @Test
    void samePeriod_overlaps() {
        LocalDate a = LocalDate.of(2025, 3, 1);
        LocalDate b = LocalDate.of(2025, 3, 5);
        assertThat(OverlapChecker.overlaps(a, b, a, b)).isTrue();
    }

    @Test
    void adjacentPeriods_doNotOverlap() {
        LocalDate a1 = LocalDate.of(2025, 3, 1);
        LocalDate a2 = LocalDate.of(2025, 3, 5);
        LocalDate b1 = LocalDate.of(2025, 3, 6);
        LocalDate b2 = LocalDate.of(2025, 3, 10);
        assertThat(OverlapChecker.overlaps(a1, a2, b1, b2)).isFalse();
    }

    @Test
    void oneInsideOther_overlaps() {
        LocalDate innerStart = LocalDate.of(2025, 3, 3);
        LocalDate innerEnd = LocalDate.of(2025, 3, 4);
        LocalDate outerStart = LocalDate.of(2025, 3, 1);
        LocalDate outerEnd = LocalDate.of(2025, 3, 10);
        assertThat(OverlapChecker.overlaps(innerStart, innerEnd, outerStart, outerEnd)).isTrue();
    }

    @Test
    void partialOverlap_overlaps() {
        LocalDate p1Start = LocalDate.of(2025, 3, 1);
        LocalDate p1End = LocalDate.of(2025, 3, 5);
        LocalDate p2Start = LocalDate.of(2025, 3, 4);
        LocalDate p2End = LocalDate.of(2025, 3, 8);
        assertThat(OverlapChecker.overlaps(p1Start, p1End, p2Start, p2End)).isTrue();
    }
}
