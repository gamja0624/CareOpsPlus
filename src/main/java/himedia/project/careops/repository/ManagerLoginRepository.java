package himedia.project.careops.repository;

/*@author 노태윤
@editDate 2024-09-23~2024-09-24*/

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import himedia.project.careops.entity.Admin;
import himedia.project.careops.entity.Manager;

@Repository
public interface ManagerLoginRepository extends JpaRepository<Manager, String> {

    @Query(value = "SELECT * FROM manager WHERE manager_dept_no = ? AND manager_id = ? AND manager_password = ?", nativeQuery = true)
    Optional<Manager> findByManagerDeptNoAndManagerIdAndManagerPassword(int managerDeptNo, String managerId, String managerPassword);
}
// 이것만 써도 오류는안남
//Optional<Admin> findByAdminDeptNoAndAdminIdAndAdminPassword(String adminDeptNo, String adminId, String adminPassword);
//Optional<Manager> findByManagerDeptNoAndManagerIdAndManagerPassword(int managerDeptNo, String managerId, String managerPassword);