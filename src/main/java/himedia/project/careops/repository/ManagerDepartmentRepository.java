package himedia.project.careops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 진혜정
 * @editDate 2024-09-25
 */

import org.springframework.stereotype.Repository;

import himedia.project.careops.entity.ManagerDepartment;

@Repository
public interface ManagerDepartmentRepository extends JpaRepository<ManagerDepartment, Integer> {

}
