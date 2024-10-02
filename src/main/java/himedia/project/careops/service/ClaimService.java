package himedia.project.careops.service;

/**
 * @author 최은지
 * @editDate 2024-09-27
 */

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import himedia.project.careops.dto.ClaimCategoryDTO;
import himedia.project.careops.dto.ClaimDTO;
import himedia.project.careops.dto.ClaimSubCategoryDTO;
import himedia.project.careops.entity.Claim;
import himedia.project.careops.entity.ClaimCategory;
import himedia.project.careops.entity.ClaimSubCategory;
import himedia.project.careops.repository.ClaimCategoryRepository;
import himedia.project.careops.repository.ClaimRepository;
import himedia.project.careops.repository.ClaimSubCategoryRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClaimService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final ClaimRepository claimRepository;
	private final ClaimCategoryRepository claimCategoryRepository;
	private final ClaimSubCategoryRepository calimSubCategoryRepository;
	private final ModelMapper modelMapper;
	
	public ClaimService(ClaimRepository claimRepository, ClaimCategoryRepository claimCategoryRepository ,ClaimSubCategoryRepository calimSubCategoryRepository, ModelMapper modelMapper) {
		this.claimRepository = claimRepository;
		this.claimCategoryRepository = claimCategoryRepository;
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
	// [ 부서 담당자 ] =========================================================================
	// 민원 대분류 전체 조회 
	public List<ClaimCategoryDTO> findAllCategory() {
		
		List<ClaimCategory> claimCategory = claimCategoryRepository.findAll();
		
		log.info("민원 서비스 카테고리 찾기: {} ", claimCategory);
		
		   return claimCategory.stream()
                   .map(category -> modelMapper.map(category, ClaimCategoryDTO.class))
                   .collect(Collectors.toList());
	}
	// 민원 소분류 전체 조회 
	public Page<ClaimSubCategoryDTO> findAllSubCategory(Pageable page) {
		log.info("findAllSubCategory 실행");
		
		page = PageRequest.of(page.getPageNumber() <= 0 ? 0 : page.getPageNumber() - 1,
                page.getPageSize(),
                Sort.by("claimSubCategoryNo").ascending());
		
		Page<ClaimSubCategory> claimsubCategory = calimSubCategoryRepository.findAll(page);
		
		return claimsubCategory.map(subCategory -> modelMapper.map(subCategory, ClaimSubCategoryDTO.class));
	}
	
	// 부서별 민원 조회 ( 삭제 할 수도 있음 )
	public List<Claim> findByManagerDeptClaim(Integer managerDeptNo) {
		log.info("managerDeptNo : {}", managerDeptNo);
		// 전체 목록 조회 -> 담당자 부서 번호와 일치하는 데이터 추출 -> 리스트로 
		return claimRepository.findAll()
				.stream()
				.filter(deptNo -> deptNo.getManagerDeptNo() == managerDeptNo)
				.collect(Collectors.toList());
	}
	
	// 부서 내 민원 목록 조회 ( 페이지 반환 )
	 public Page<ClaimDTO> ManagerDeptClaim(Integer managerDeptNo, Pageable pageable) {
        log.info("managerDeptNo : {}", managerDeptNo);
	        
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                                   pageable.getPageSize(),
                                   Sort.by("claimNo").ascending());

        // 모든 목록을 페이지로 저장
        Page<Claim> allClaimList = claimRepository.findAll(pageable);

        // 부서 번호로 필터링 
        List<Claim> filteredClaims = allClaimList.getContent().stream()
            .filter(claim -> claim.getManagerDeptNo() == (managerDeptNo))
            .collect(Collectors.toList());

        // 필터링된 리스트로 새로운 Page 객체 생성
        return new PageImpl<>(filteredClaims.stream()
    							.map(claim -> modelMapper.map(claim, ClaimDTO.class))
    							.collect(Collectors.toList()), 
    							pageable, 
    							allClaimList.getTotalElements());
    }
	// 작성자 : 진혜정
	// 내 민원 목록 리스트로 반환
	public List<Claim> findByMyClaim(String managerName) {
		
		return claimRepository.findAll()
				.stream() 
				.filter(m -> m.getClaimManagerName().equals(managerName))
				.collect(Collectors.toList());     
	}
	
}

