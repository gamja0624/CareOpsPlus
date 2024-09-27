package himedia.project.careops.repository;

/**
 * @author 이홍준
 * @editDate 2024-09-25
 */

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import himedia.project.careops.entity.SafetyManagement;
import himedia.project.careops.entity.SafetyManagementList;

@Repository
public interface SafetyManagementListRepository extends JpaRepository<SafetyManagementList, Integer> {

	
	/*
	 * // smNo 기준 중복제거
	 * 
	 * @Query("SELECT DISTINCT e FROM SafetyManagementList e WHERE e.smNo IS NOT NULL"
	 * ) List<SafetyManagementList> findDistinctBySmNo();
	 */

	// 작성자 : 이홍준
	// 목록페이지 조회하는 쿼리문 생성(nativeQuery)
	/*
	 * @Query(value =
	 * "SELECT sm.smNo, adminName, smlNo, smlList, smlCheck, smCheck, smDate FROM safetyManagement JOIN safetyManagementList sml ON safetyManagement.smNo = safetyManagementList.smNo ORDER BY safetyManagement.smNo DESC"
	 * , nativeQuery = true)
	 */

	@Query(value = "SELECT sm.sm_no, admin_name, sml_no, sml_list, sml_check, sm_check, sm_date FROM safety_management sm JOIN safety_management_list sml ON sm.sm_no = sml.sm_no ORDER BY sm.sm_no DESC", nativeQuery = true)
	List<SafetyManagementList> findAllList();

	/*
	 * @Query("SELECT DISTINCT sml FROM SafetyManagementList sml")
	 * List<SafetyManagementList> findAllDistinct();
	 */
}
