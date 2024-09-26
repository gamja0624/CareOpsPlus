package himedia.project.careops.repository;

/**
 * @author 진혜정, 노태윤
 * @editDate 2024-09-25
 */

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



import org.springframework.stereotype.Repository;

import himedia.project.careops.entity.AdminDepartment;

@Repository
public interface AdminDepartmentRepository extends JpaRepository<AdminDepartment, String> {
	
    Optional<AdminDepartment> findByAdminDeptNo(String adminDeptNo);
    
}