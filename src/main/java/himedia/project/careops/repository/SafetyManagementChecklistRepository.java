package himedia.project.careops.repository;

/**
 * @author 이홍준
 * @editDate 2024-10-10 ~ 
 */

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import himedia.project.careops.dto.SafetyManagementChecklistDTO;
import himedia.project.careops.entity.SafetyManagementChecklist;

@Repository
public interface SafetyManagementChecklistRepository extends JpaRepository<SafetyManagementChecklist, Integer> {
	
	// 점검항목별 조회
	List<SafetyManagementChecklist> findBySmlList(String smlList);

	List<SafetyManagementChecklist> findBySmcFloor(int smcFloor);

	void save(SafetyManagementChecklistDTO checklist);

	@Modifying
	@Query("UPDATE SafetyManagementChecklist s SET s.smcList = :smcList " +
	           "WHERE s.smcNo = :smcNo AND s.smlNo = :smlNo AND s.smcFloor = :smcFloor")
	void updateSmcList(@Param("smcNo") int smcNo, @Param("smlNo") int smlNo, @Param("smcFloor") int smcFloor, @Param("smcList") String smcList);


	//List<SafetyManagementChecklist> findBySmlListandSmcFloor(String smlList, int smcFloor);

	
}