package himedia.project.careops.repository;

/**
 * @author 이홍준
 * @editDate 2024-09-26 ~ 2024-10-17
 */

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import himedia.project.careops.entity.DailyManagementReport;

@Repository
public interface DailyManagementReportRepository extends JpaRepository<DailyManagementReport, Integer> {

	// adminName 기준 Page 처리
	Page<DailyManagementReport> findByAdminName(String adminName, Pageable pageable);
	
	// adminName 포함단어 기준 Page 처리
	Page<DailyManagementReport> findByAdminNameContaining(String adminName, Pageable pageable);
	
	// adminDeptName 포함단어 기준 Page 처리
	Page<DailyManagementReport> findByAdminDeptNameContaining(String value, Pageable pageable);
	
	// DmrDate 기준 Page 처리
	Page<DailyManagementReport> findByDmrDate(Date date, Pageable pageable);

	// adminName 기준 리스트
	List<DailyManagementReport> findByAdminName(String adminName);

}
