package himedia.project.careops.repository;

/**
 * @author 최은지 
 * @editDate 2024-09-26, 2024-10-14 
 */

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import himedia.project.careops.entity.Claim;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer>{

	Page<Claim> findByManagerDeptNo(Integer managerDetpNo, Pageable pageble);
	
	Page<Claim> findByManagerId(String managerId, Pageable pageble);
}
