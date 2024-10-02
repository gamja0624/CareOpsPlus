package himedia.project.careops.service;

/**
 * @author 최은지
 * @editDate 2024-09-27
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import himedia.project.careops.entity.Claim;
import himedia.project.careops.entity.ClaimSubCategory;
import himedia.project.careops.entity.Manager;
import himedia.project.careops.repository.ClaimRepository;
import himedia.project.careops.repository.ClaimSubCategoryRepository;
import lombok.extern.slf4j.Slf4j;

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
	// 작성자 : 진혜정
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
	
	// 작성자 : 진혜정
	// 내가 쓴 민원 리스트로 반환
	public List<Claim> findByClaimListManagerName(String userName) {
		
		return claimRepository.findAll() 										
				.stream() 
				.filter(m -> m.getClaimManagerName().equals(userName))
				.collect(Collectors.toList());
	}
	
	// 작성자 : 진혜정
	// 민원 접수 대기건과 처리 진행건 개수 반환
	public Map<String, Integer> findByClaimStatus() {
		
		// 키, 값 형태로 저장할 변수 선언
		Map<String, Integer> ClaimStatusList = new HashMap<>();
		
		// 변수 
		Integer standby = 0;
		Integer progress = 0;
		
		List<Claim> claimList = claimRepository.findAll();
		
		for (Claim c: claimList) {
			if (! c.getClaimApprove()) { 
				standby ++;
			} else if (c.getClaimApprove()) {
				progress ++;
			}
			ClaimStatusList.put("standby", standby);
			ClaimStatusList.put("progress", progress);
		}
		
		return ClaimStatusList;
	}
	
	// [ 부서 담당자 ] =========================================================================
	// 민원 소분류 전체 조회 
	public Page<ClaimSubCategoryDTO> findAllSubCategory(Pageable page) {
		log.info("findAllSubCategory 실행");
		
		page = PageRequest.of(page.getPageNumber() <= 0 ? 0 : page.getPageNumber() - 1,
                page.getPageSize(),
                Sort.by("claimSubCategoryNo").ascending());
		
		Page<ClaimSubCategory> claimsubCategory = calimSubCategoryRepository.findAll(page);
		
		return claimsubCategory.map(subCategory -> modelMapper.map(subCategory, ClaimSubCategoryDTO.class));
	}
	
	// 부서별 민원 찾기
	public List<Claim> findByManagerDeptClaim(Integer managerDeptNo) {
		return claimRepository.findAll()
				.stream()
				.filter(deptNo -> deptNo.getManagerDeptNo() == managerDeptNo)
				.collect(Collectors.toList());
	}
	// 부서 내 민원 목록 조회 ( 페이지 반환 )
	
	// 작성자 : 진혜정
	// 내 민원 목록 리스트로 반환
	public List<Claim> findByMyClaim(String managerName) {
		
		return claimRepository.findAll()
				.stream() 
				.filter(m -> m.getClaimManagerName().equals(managerName))
				.collect(Collectors.toList());     
	}
	
}
