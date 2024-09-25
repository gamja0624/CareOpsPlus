package himedia.project.careops.repository;

/**
 * @author 진혜정
 * @editDate 2024-09-25
 */

import org.springframework.data.jpa.repository.JpaRepository;
import himedia.project.careops.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {

}
