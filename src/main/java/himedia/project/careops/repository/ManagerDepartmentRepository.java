package himedia.project.careops.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import himedia.project.careops.entity.ManagerDepartment;


@Repository
public interface ManagerDepartmentRepository extends JpaRepository<ManagerDepartment, Integer> {
}
