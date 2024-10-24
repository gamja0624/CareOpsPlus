package himedia.project.careops.repository;

/**
 * @author 최은지 
 * @editDate 2024-09-25
 */

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import himedia.project.careops.entity.ClaimSubCategory;

@Repository
public interface ClaimSubCategoryRepository extends JpaRepository<ClaimSubCategory, Integer > {
	
	// 소분류 - 의료기기 아이디 찾기
	Optional<ClaimSubCategory> findByClaimSubCategoryNo(Integer claimSubCategoryNo);
	List<ClaimSubCategory> findByLmdMinorCateNameContaining(String name);
    List<ClaimSubCategory> findBySubCategoryNameContaining(String name);
}
