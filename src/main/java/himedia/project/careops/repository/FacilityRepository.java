package himedia.project.careops.repository;

/**
 * @author 진혜정
 * @editDate 2024-10-02
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import himedia.project.careops.entity.Facility;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Integer> {
	
	// 예약 번호
	@Query(value = "SELECT MAX(facility_reservation_no) FROM facility", nativeQuery = true)
    Integer findMaxReservationNo();
}
