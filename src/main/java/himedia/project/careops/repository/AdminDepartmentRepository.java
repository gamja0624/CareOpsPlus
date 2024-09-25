package himedia.project.careops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 진혜정
 * @editDate 2024-09-25
 */

import org.springframework.stereotype.Repository;

import himedia.project.careops.entity.AdminDepartment;

@Repository
public interface AdminDepartmentRepository extends JpaRepository<AdminDepartment, String> {

}
