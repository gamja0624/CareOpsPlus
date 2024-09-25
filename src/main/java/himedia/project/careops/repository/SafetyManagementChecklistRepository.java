package himedia.project.careops.repository;

/**
 * @author 이홍준 
 * @editDate 2024-09-25~
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import himedia.project.careops.entity.SafetyManagementChecklist;

@Repository
public interface SafetyManagementChecklistRepository extends JpaRepository<SafetyManagementChecklist, Integer> {

}
