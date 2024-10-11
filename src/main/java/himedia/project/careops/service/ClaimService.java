package himedia.project.careops.service;

import java.sql.Date;

/**
 * @author 최은지
 * @editDate 2024-09-27 ~ 
 */

import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import himedia.project.careops.repository.ClaimReplyRepository;
import himedia.project.careops.repository.ClaimRepository;
import himedia.project.careops.repository.ClaimSubCategoryRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClaimService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final ClaimRepository claimRepository;
	private final ClaimCategoryRepository claimCategoryRepository;
	private final ClaimSubCategoryRepository calimSubCategoryRepository;
	private final ClaimReplyRepository claimReplyRepository;
	private final ModelMapper modelMapper;
	
	public ClaimService(ClaimRepository claimRepository, ClaimCategoryRepository claimCategoryRepository ,ClaimSubCategoryRepository calimSubCategoryRepository, 
						ClaimReplyRepository claimReplyRepository, ModelMapper modelMapper) {
		this.claimRepository = claimRepository;
		this.claimCategoryRepository = claimCategoryRepository;
		this.calimSubCategoryRepository = calimSubCategoryRepository;
		this.claimReplyRepository = claimReplyRepository;
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
	// 작성자 : 최은지
	// 민원 전체 조회 
	public Page<ClaimDTO> allClaim(Pageable page) {
		
		page = PageRequest.of(page.getPageNumber() <= 0 ? 0 : page.getPageNumber() -1,
				  page.getPageSize(),
				  Sort.by("claimNo").ascending());
	
		Page<Claim> claimList = claimRepository.findAll(page);
		
		return claimList.map(claim -> modelMapper.map(claim, ClaimDTO.class));
	}
	
	// 작성자 : 최은지
	// 민원 상세 조회 
	public ClaimDTO findByClaimNo(Integer claimNo) {
		
		Claim claim = claimRepository.findById(claimNo).orElseThrow(IllegalArgumentException::new);
		
		return modelMapper.map(claim, ClaimDTO.class);	
	}
	
	// 작성자 : 최은지
	// 민원 승인
	public void approveClaim(ClaimDTO claimDTO) {
		log.info("민원승인 서비스 실행");
		Claim claim =  claimRepository.findById(claimDTO.getClaimNo()).orElseThrow(IllegalArgumentException::new);
		
		claim.setClaimApprove(true);
		claimRepository.save(claim);
	}
	// 작성자 : 최은지 
	// 민원 처리
	public void completeClaim(ClaimDTO claimDTO) {
		log.info("민원처리 서비스 실행");
		Claim claim =  claimRepository.findById(claimDTO.getClaimNo()).orElseThrow(IllegalArgumentException::new);
		
		claim.setClaimComplete(true);
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
	// 민원 접수 대기건과 처리 진행건, 의료기기건, 안전관리 건 키, 값 형태로 반환
	public Map<String, Integer> findByClaimStatus() {
		
		// 키, 값 형태로 저장할 변수 선언
		Map<String, Integer> ClaimStatusList = new HashMap<>();
		
		// 변수 
		ClaimStatusList.put("standby", 0);
		ClaimStatusList.put("progress", 0);
		ClaimStatusList.put("medicalCnt", 0);
		ClaimStatusList.put("safetyCnt", 0);
		
		List<Claim> claimList = claimRepository.findAll();
		
		for (Claim c: claimList) {
			if (! c.getClaimApprove()) { 
				ClaimStatusList.put("standby", ClaimStatusList.get("standby") + 1);
			} else if (c.getClaimApprove()) {
				ClaimStatusList.put("progress", ClaimStatusList.get("progress") + 1);
			} 
			
			if (c.getClaimCategoryName().equals("의료기기") && ! c.getClaimApprove()) {
				ClaimStatusList.put("medicalCnt", ClaimStatusList.get("medicalCnt") + 1);
			} else if (c.getClaimCategoryName().equals("안전") && ! c.getClaimApprove())	{
				ClaimStatusList.put("safetyCnt", ClaimStatusList.get("safetyCnt") + 1);
			}		
		}
		
		return ClaimStatusList;
	}
	
	// 작성자 : 진혜정
	// 년도, 월별로 민원수 카운트
	public Map<String, Integer> findByClaimDateStatus(int year) {
	    // 키, 값 형태로 저장할 변수 선언
	    Map<String, Integer> ClaimDateStatusList = new HashMap<>();
	    
	    // 1 ~ 12월 초기화
	    ClaimDateStatusList.put("jan", 0);
	    ClaimDateStatusList.put("feb", 0);
	    ClaimDateStatusList.put("mar", 0);
	    ClaimDateStatusList.put("apr", 0);
	    ClaimDateStatusList.put("may", 0);
	    
	    ClaimDateStatusList.put("jun", 0);
	    ClaimDateStatusList.put("jul", 0);
	    ClaimDateStatusList.put("aug", 0);
	    ClaimDateStatusList.put("sep", 0);
	    ClaimDateStatusList.put("oct", 0);
	    
	    ClaimDateStatusList.put("nov", 0);
	    ClaimDateStatusList.put("dec", 0);
	    
	    List<Claim> claimList = claimRepository.findAll();
	    
	    for (Claim c : claimList) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(c.getClaimDate());
	        
	        int dateYear = calendar.get(Calendar.YEAR); 		// 연도 추출
	        int dateMonth = calendar.get(Calendar.MONTH) + 1; 	// 월 추출 
	        
	        if (dateYear == year) {
	            // 각 월에 해당하는 카운트 증가
	            switch (dateMonth) {
	                case 1: 
	                	ClaimDateStatusList.put("jan", ClaimDateStatusList.get("jan") + 1); break;
	                case 2: 
	                	ClaimDateStatusList.put("feb", ClaimDateStatusList.get("feb") + 1); break;
	                case 3: 
	                	ClaimDateStatusList.put("mar", ClaimDateStatusList.get("mar") + 1); break;
	                case 4: 
	                	ClaimDateStatusList.put("apr", ClaimDateStatusList.get("apr") + 1); break;
	                case 5: 
	                	ClaimDateStatusList.put("may", ClaimDateStatusList.get("may") + 1); break;
	                case 6: 
	                	ClaimDateStatusList.put("jun", ClaimDateStatusList.get("jun") + 1); break;
	                case 7: 
	                	ClaimDateStatusList.put("jul", ClaimDateStatusList.get("jul") + 1); break;
	                case 8: 
	                	ClaimDateStatusList.put("aug", ClaimDateStatusList.get("aug") + 1); break;
	                case 9: 
	                	ClaimDateStatusList.put("sep", ClaimDateStatusList.get("sep") + 1); break;
	                case 10: 
	                	ClaimDateStatusList.put("oct", ClaimDateStatusList.get("oct") + 1); break;
	                case 11: 
	                	ClaimDateStatusList.put("nov", ClaimDateStatusList.get("nov") + 1); break;
	                case 12: 
	                	ClaimDateStatusList.put("dec", ClaimDateStatusList.get("dec") + 1); break;
	            }
	        }
	    }
	    
	    return ClaimDateStatusList;
	}

	
	// [ 부서 담당자 ] =========================================================================
	// 작성자 : 최은지
	// 민원 대분류 전체 조회 
	public List<ClaimCategoryDTO> findAllCategory() {
		
		List<ClaimCategory> claimCategory = claimCategoryRepository.findAll();
		
		log.info("민원 서비스 카테고리 찾기: {} ", claimCategory);
		
	   return claimCategory.stream()
			   		.map(category -> modelMapper.map(category, ClaimCategoryDTO.class))
			   		.collect(Collectors.toList());
	}
	
	// 작성자 : 최은지
	// 민원 대분류 정보 찾기
	public ClaimCategory findBycategoryName(String categoryName) {
	    
		return claimCategoryRepository.findAll()
	            .stream()
	            .filter(category -> category.getClaimCategoryName().equals(categoryName))
	            .findFirst() 
	            .orElse(null);				
	}
	// 작성자 : 최은지
	// 민원 소분류 정보 찾기
	public ClaimSubCategory findBySubcategoryName(String subCategoryName) {
	    return calimSubCategoryRepository.findAll()
	            .stream()
	            .filter(category -> 
	                category.getLmdMinorCateName() != null && category.getLmdMinorCateName().equals(subCategoryName) || 
	                category.getSmlList() != null && category.getSmlList().equals(subCategoryName)
	            )
	            .findFirst() 
	            .orElse(null); // 일치하는 객체가 없을 경우 null 반환
	}
	
	// 작성자 : 최은지
	// 민원 소분류 전체 조회 
	public Page<ClaimSubCategoryDTO> findAllSubCategory(Pageable page) {
		log.info("findAllSubCategory 실행");
		
		page = PageRequest.of(page.getPageNumber() <= 0 ? 0 : page.getPageNumber() - 1,
                page.getPageSize(),
                Sort.by("claimSubCategoryNo").ascending());
		
		Page<ClaimSubCategory> claimsubCategory = calimSubCategoryRepository.findAll(page);
		
		return claimsubCategory.map(subCategory -> modelMapper.map(subCategory, ClaimSubCategoryDTO.class));
	}
	
	// 작성자 : 최은지
	// 부서별 민원 조회 ( 삭제 할 수도 있음 )
	public List<Claim> findByManagerDeptClaim(Integer managerDeptNo) {
		// 전체 목록 조회 -> 담당자 부서 번호와 일치하는 데이터 추출 -> 리스트로 
		return claimRepository.findAll()
				.stream()
				.filter(deptNo -> deptNo.getManagerDeptNo() == managerDeptNo)
				.collect(Collectors.toList());
	}
	
	// 작성자 : 최은지
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

	    return new PageImpl<>(filteredClaims.stream()
	        .map(claim -> modelMapper.map(claim, ClaimDTO.class))
	        .collect(Collectors.toList()),
	        pageable, allClaimList.getTotalElements());
	}
	// 작성자 : 최은지
	// 민원 저장
	public void saveClaim(ClaimDTO claimDTO, HttpSession session) {
		log.info("민원 저장 서비스 실행");
		
		Claim claim = new Claim();
		Date currentDate = new Date(System.currentTimeMillis()); // 현재 날짜 가져오기 ( 임시.. )
		
		// 세션으로 저장할 정보 : 매니저 아이디 매니저 부서 번호 매니저 이름
		String managerId = (String) session.getAttribute("userId");
	    String managerName = (String) session.getAttribute("userName");
	    String deptNo = (String) session.getAttribute("deptNo");
	    int managerDeptNo = Integer.parseInt(deptNo); // String을 int로 변환
	   
	    claim.setManagerId(managerId);
	    claim.setManagerDeptNo(managerDeptNo);
	    claim.setClaimManagerName(managerName);
	    log.info("claim : {}", claim);	
	    
	    ClaimCategory category = findBycategoryName(claimDTO.getClaimCategoryName());
	    ClaimSubCategory subCategory = findBySubcategoryName(claimDTO.getClaimSubCategoryName());
	    log.info("category : {}", category);	
	    log.info("subCategory : {}", subCategory);	
		
	    // 입력 받을 정보 : (1) 제목 , (2) 대분류, (3) 소분류, (4) 요청 구분, (5) 첨부파일, (6) 내용
		claim.setClaimTitle(claimDTO.getClaimTitle());                     // 제목
		claim.setClaimCategoryNo(category.getClaimCategoryNo());           // 대분류 번호   
		claim.setClaimCategoryName(claimDTO.getClaimCategoryName());       // 대분류 이름
		claim.setClaimSubCategoryNo(subCategory.getClaimSubCategoryNo());     // 소분류 번호
		claim.setClaimSubCategoryName(claimDTO.getClaimSubCategoryName()); // 소분류 이름
		claim.setClaimCategoryStatus(claimDTO.getClaimCategoryStatus());   // 요청 구분
		// claim.setClaimAttachment(claimDTO.getClaimAttachment());        // 첨부 파일 ( 이미지 등록 구현 후 수정 )
		claim.setClaimContent(claimDTO.getClaimContent());  
	
		claim.setClaimDate(currentDate);
		claim.setClaimApprove(false);
		claim.setClaimComplete(false);
		log.info("claim : {}", claim);	
		claimRepository.save(claim);	
	}
	
	// 작성자 : 최은지 
	// 민원 수정
	public void updateClaim(ClaimDTO claimDTO) {
		
		Claim claim = claimRepository.findById(claimDTO.getClaimNo()).orElseThrow(IllegalArgumentException::new);
		
		// 수정 가능한 정보 : (1) 제목 , (2) 대분류, (3) 소분류, (4) 요청 구분, (5) 첨부파일, (6) 내용
		claim.setClaimTitle(claimDTO.getClaimTitle());                     // 제목
		claim.setClaimCategoryNo(claimDTO.getClaimCategoryNo());           // 대분류 번호   
		claim.setClaimCategoryName(claimDTO.getClaimCategoryName());       // 대분류 이름
		claim.setClaimSubCategoryNo(claimDTO.getClaimSubCategoryNo());     // 소분류 번호
		claim.setClaimSubCategoryName(claimDTO.getClaimSubCategoryName()); // 소분류 이름
		claim.setClaimCategoryStatus(claimDTO.getClaimCategoryStatus());   // 요청 구분
		// claim.setClaimAttachment(claimDTO.getClaimAttachment());        // 첨부 파일 ( 이미지 등록 구현 후 수정 )
		claim.setClaimContent(claimDTO.getClaimContent());                 // 내용
		
		// 변경 사항 저장
		claimRepository.save(claim);         
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

