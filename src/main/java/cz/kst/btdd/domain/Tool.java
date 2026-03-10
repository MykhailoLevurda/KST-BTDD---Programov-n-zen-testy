package cz.kst.btdd.domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Půjčované nářadí.
 */
public class Tool {

    private Long id;
    private String name;
    private BigDecimal dailyPriceCzk;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getDailyPriceCzk() {
        return dailyPriceCzk;
    }

    public void setDailyPriceCzk(BigDecimal dailyPriceCzk) {
        this.dailyPriceCzk = dailyPriceCzk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tool tool = (Tool) o;
        return Objects.equals(id, tool.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
