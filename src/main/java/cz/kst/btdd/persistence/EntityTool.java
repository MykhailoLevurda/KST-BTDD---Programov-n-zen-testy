package cz.kst.btdd.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tools")
public class EntityTool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "daily_price_czk", nullable = false, precision = 10, scale = 2)
    private BigDecimal dailyPriceCzk;

    @Column(name = "late_fee_per_day", precision = 10, scale = 2)
    private BigDecimal lateFeePerDay = new BigDecimal("50");

    @OneToMany(mappedBy = "tool", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntityRental> rentals = new ArrayList<>();

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

    public BigDecimal getLateFeePerDay() {
        return lateFeePerDay;
    }

    public void setLateFeePerDay(BigDecimal lateFeePerDay) {
        this.lateFeePerDay = lateFeePerDay;
    }

    @JsonIgnore
    public List<EntityRental> getRentals() {
        return rentals;
    }
}
