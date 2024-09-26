package himedia.project.careops.repository;

/**
 * @author 이홍준
 * @editDate 2024-09-25 ~ 
 */

import java.util.List;

/**
 * @author 이홍준 
 * @editDate 2024-09-25~
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import himedia.project.careops.entity.SafetyManagementList;

@Repository
public interface SafetyManagementListRepository extends JpaRepository<SafetyManagementList, Integer> {

	/*
	 * // smNo 기준 중복제거
	 * 
	 * @Query("SELECT DISTINCT e FROM SafetyManagementList e WHERE e.smNo IS NOT NULL"
	 * ) List<SafetyManagementList> findDistinctBySmNo();
	 */

	/*
	 * @Query(value =
	 * "select sm.sm_no, admin_name, sml_no, sml_list, sml_check, sm_check, sm_date from safety_management sm left join safety_management_list sml on sm.sm_no = sml.sm_no group by sm.sm_no"
	 * , nativeQuery = true) List<SafetyManagementList> findAllList();
	 */
	/*
	 * @Query("SELECT DISTINCT sml FROM SafetyManagementList sml")
	 * List<SafetyManagementList> findAllDistinct();
	 */
}
