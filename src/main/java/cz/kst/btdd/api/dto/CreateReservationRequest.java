package cz.kst.btdd.api.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class CreateReservationRequest {

    @NotNull(message = "userId je povinný")
    private Long userId;
    @NotNull(message = "toolId je povinný")
    private Long toolId;
    @NotNull(message = "startDate je povinné")
    private LocalDate startDate;
    @NotNull(message = "endDate je povinné")
    private LocalDate endDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getToolId() {
        return toolId;
    }

    public void setToolId(Long toolId) {
        this.toolId = toolId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
