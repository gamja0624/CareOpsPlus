package himedia.project.careops.repository;

/**
 * @author 이홍준 
 * @editDate 2024-09-26 ~
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import himedia.project.careops.entity.DailyManagementReport;

@Repository
public interface DailyManagementReportRepository extends JpaRepository<DailyManagementReport, Integer> {

}
