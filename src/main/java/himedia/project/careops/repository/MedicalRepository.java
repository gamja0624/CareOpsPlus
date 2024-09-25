package himedia.project.careops.repository;

/**
 * @author 진혜정
 * @editDate 2024-09-23
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import himedia.project.careops.entity.ListMedicalDevices;

@Repository
public interface MedicalRepository extends JpaRepository<ListMedicalDevices, String> {
	
}
