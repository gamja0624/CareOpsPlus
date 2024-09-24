package himedia.project.careops.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import himedia.project.careops.entity.ListMedicalDevices;

@Repository
public interface MedicalRepository extends JpaRepository<ListMedicalDevices, String> {
}
