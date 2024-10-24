package himedia.project.careops.repository;

/**
 * @author 이홍준
 * @editDate 2024-09-25 ~ 2024-10-15 
 */

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import himedia.project.careops.dto.SafetyManagementDTO;
import himedia.project.careops.entity.SafetyManagement;

@Repository
public interface SafetyManagementRepository extends JpaRepository<SafetyManagement, Integer> {

	List<SafetyManagement> findBySmListAndSmFacilityFloor(String smList, int smFacilityFloor);

	SafetyManagement findBySmListAndSmFacilityNoAndSmFacilityFloor(String smList, int smFacilityNo,	int smFacilityFloor);

	void save(SafetyManagementDTO updateData);
	
}
