package cz.kst.btdd.api;

import cz.kst.btdd.api.dto.CreateReservationRequest;
import cz.kst.btdd.persistence.EntityRental;
import cz.kst.btdd.service.RentalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<EntityRental> createReservation(@Valid @RequestBody CreateReservationRequest request) {
        EntityRental created = rentalService.createReservation(
                request.getUserId(),
                request.getToolId(),
                request.getStartDate(),
                request.getEndDate()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/{rentalId}/activate")
    public ResponseEntity<EntityRental> activate(@PathVariable Long rentalId, @RequestParam Long employeeUserId) {
        EntityRental updated = rentalService.activate(rentalId, employeeUserId);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{rentalId}/return")
    public ResponseEntity<EntityRental> returnRental(
            @PathVariable Long rentalId,
            @RequestParam Long employeeUserId,
            @RequestParam(required = false) LocalDate actualReturnDate) {
        EntityRental updated = rentalService.returnRental(rentalId, employeeUserId, actualReturnDate);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{rentalId}/cancel")
    public ResponseEntity<EntityRental> cancel(@PathVariable Long rentalId, @RequestParam Long userId) {
        EntityRental updated = rentalService.cancelReservation(rentalId, userId);
        return ResponseEntity.ok(updated);
    }
}
