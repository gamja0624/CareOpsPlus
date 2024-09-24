package himedia.project.careops.repository;

/*@author 노태윤
@editDate 2024-09-23~2024-09-24*/

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import himedia.project.careops.dto.AdminDTO;
import himedia.project.careops.entity.Admin;
import himedia.project.careops.entity.Manager;

@Repository
public interface LoginRepository extends JpaRepository<Admin, String> {

    @Query(value = "SELECT * FROM admin WHERE admin_dept_no = ?1 AND admin_id = ?2 AND admin_password = ?3", nativeQuery = true)
    Optional<Admin> findByAdminDeptNoAndAdminIdAndAdminPassword(String adminDeptNo, String adminId, String adminPassword);

    @Query(value = "SELECT m FROM manager m WHERE m.manager_dept_no = ?1 AND m.manager_id = ?2 AND m.manager_password = ?3", nativeQuery = true)
    Optional<Manager> findByManagerDeptNoAndManagerIdAndManagerPassword(int managerDeptNo, String managerId, String managerPassword);

    
}

// 이것만 써도 오류는안남
//Optional<Admin> findByAdminDeptNoAndAdminIdAndAdminPassword(String adminDeptNo, String adminId, String adminPassword);
//Optional<Manager> findByManagerDeptNoAndManagerIdAndManagerPassword(int managerDeptNo, String managerId, String managerPassword);