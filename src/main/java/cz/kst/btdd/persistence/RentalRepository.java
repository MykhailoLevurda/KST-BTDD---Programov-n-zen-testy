package cz.kst.btdd.persistence;

import cz.kst.btdd.domain.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RentalRepository extends JpaRepository<EntityRental, Long> {

    List<EntityRental> findByToolIdAndStatusIn(Long toolId, List<RentalStatus> statuses);

    /**
     * Najde rezervace/aktivní půjčky pro dané nářadí, které se překrývají s intervalem (start, end).
     * Překrývání: (rental.startDate, rental.endDate) ∩ (start, end) ≠ ∅
     */
    @Query("SELECT r FROM EntityRental r WHERE r.tool.id = :toolId AND r.status IN :statuses " +
           "AND r.endDate >= :start AND r.startDate <= :end")
    List<EntityRental> findOverlappingForTool(@Param("toolId") Long toolId,
                                              @Param("statuses") List<RentalStatus> statuses,
                                              @Param("start") LocalDate start,
                                              @Param("end") LocalDate end);
}
