package himedia.project.careops.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import himedia.project.careops.dto.ClaimDTO;
import himedia.project.careops.dto.ClaimSubCategoryDTO;
import himedia.project.careops.dto.ListMedicalDevicesDTO;
import himedia.project.careops.dto.ManagerDTO;
import himedia.project.careops.entity.Claim;
import himedia.project.careops.entity.ClaimSubCategory;
import himedia.project.careops.entity.Manager;
import himedia.project.careops.repository.ClaimRepository;
import himedia.project.careops.repository.ClaimSubCategoryRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 최은지
 * @editDate 2024-09-27
 */

@Slf4j
@Service
public class ClaimService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final ClaimRepository claimRepository;
	private final ClaimSubCategoryRepository calimSubCategoryRepository;
	private final ModelMapper modelMapper;
	
	public ClaimService(ClaimRepository claimRepository, ClaimSubCategoryRepository calimSubCategoryRepository, ModelMapper modelMapper) {
		this.claimRepository = claimRepository;
		this.calimSubCategoryRepository = calimSubCategoryRepository;
		this.modelMapper = modelMapper;
	}
	
	// [ 공통 ] ================================================================================
	// 담당 부서 이름으로 민원 리스트 반환
	public List<Claim> findByClaimDeptNo(Integer deptNo) {
		
		return claimRepository.findAll()
				.stream()
				.filter(m -> m.getManagerDeptNo() == deptNo)
				.collect(Collectors.toList());     
	}
	
	// [ 작업 관리자 ] =========================================================================
	// 민원 전체 조회 
	public Page<ClaimDTO> allClaim(Pageable page) {
		
		page = PageRequest.of(page.getPageNumber() <= 0 ? 0 : page.getPageNumber() -1,
				  page.getPageSize(),
				  Sort.by("claimNo").ascending());
	
		Page<Claim> claimList = claimRepository.findAll(page);
		
		return claimList.map(claim -> modelMapper.map(claim, ClaimDTO.class));
	}
	
	// 민원 상세 조회 
	public ClaimDTO findByClaimNo(Integer claimNo) {
		
		Claim claim = claimRepository.findById(claimNo).orElseThrow(IllegalArgumentException::new);
		
		return modelMapper.map(claim, ClaimDTO.class);	
	}
	
	// 민원 승인
	public void ApprveClaim(ClaimDTO claimDTO) {
		log.info("민원승인 서비스 실행");
		Claim claim =  claimRepository.findById(claimDTO.getClaimNo()).orElseThrow(IllegalArgumentException::new);
		
		claim.setClaimApprove(true);
		claimRepository.save(claim);
	}
	// [ 부서 담당자 ] =========================================================================
	// 민원 소분류 전체 조회 
	public Page<ClaimSubCategoryDTO> findAllSubCategory(Pageable page) {
		log.info("findAllSubCategory 실행");
		page = PageRequest.of(page.getPageNumber() <= 0 ? 0 : page.getPageNumber() - 1,
                page.getPageSize(),
                Sort.by("claimSubCategoryNo").ascending());
		
		Page<ClaimSubCategory> claimsubCategory = calimSubCategoryRepository.findAll(page);
		
		log.info("findAllSubCategory 종료");
		return claimsubCategory.map(subCategory -> modelMapper.map(subCategory, ClaimSubCategoryDTO.class));
	}
	
}
