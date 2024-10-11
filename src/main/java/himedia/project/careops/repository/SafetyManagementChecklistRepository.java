package himedia.project.careops.repository;

import java.util.List;

/**
 * @author 이홍준
 * @editDate 2024-09-25 ~ 
 */

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import himedia.project.careops.entity.SafetyManagementChecklist;

@Repository
public interface SafetyManagementChecklistRepository extends JpaRepository<SafetyManagementChecklist, Integer> {
	
	// 점검항목별 조회
	List<SafetyManagementChecklist> findBySmlList(String smlList);

	List<SafetyManagementChecklist> findBySmcFloor(int smcFloor);

	
}
