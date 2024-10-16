package himedia.project.careops.service;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

/**
 * @author 최은지
 * @editDate 2024-09-27 ~ 
 */

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import org.springframework.web.multipart.MultipartFile;

import himedia.project.careops.dto.ClaimCategoryDTO;
import himedia.project.careops.dto.ClaimDTO;
import himedia.project.careops.dto.ClaimSubCategoryDTO;
import himedia.project.careops.entity.Claim;
import himedia.project.careops.entity.ClaimCategory;
import himedia.project.careops.entity.ClaimSubCategory;
import himedia.project.careops.entity.ListMedicalDevices;
import himedia.project.careops.repository.ClaimCategoryRepository;
import himedia.project.careops.repository.ClaimRepository;
import himedia.project.careops.repository.ClaimSubCategoryRepository;
import himedia.project.careops.repository.MedicalRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClaimService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final ClaimRepository claimRepository;
	private final ClaimCategoryRepository claimCategoryRepository;
	private final ClaimSubCategoryRepository claimSubCategoryRepository;
	private final MedicalRepository medicalRepository;
	private final ModelMapper modelMapper;
	
	public ClaimService(ClaimRepository claimRepository, ClaimCategoryRepository claimCategoryRepository ,ClaimSubCategoryRepository claimSubCategoryRepository, 
			MedicalRepository medicalRepository, ModelMapper modelMapper) {
		this.claimRepository = claimRepository;
		this.claimCategoryRepository = claimCategoryRepository;
		this.claimSubCategoryRepository = claimSubCategoryRepository;
		this.medicalRepository = medicalRepository;
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
				  Sort.by("claimNo").descending());
	
		Page<Claim> claimList = claimRepository.findAll(page);
		
		return claimList.map(claim -> modelMapper.map(claim, ClaimDTO.class));
	}
	
	// 작성자 최은지 
	// 이미지 조회 
	public byte[] claimImageData(Integer claimNo) {
	    Claim claim = claimRepository.findById(claimNo)
	            .orElseThrow(() -> new IllegalArgumentException("클레임 번호가 유효하지 않습니다."));
	    byte[] imageData = claim.getClaimImageData();
	    return imageData; 
	}

	// 작성자 : 최은지
	// 민원 상세 조회 
	public ClaimDTO findByClaimNo(Integer claimNo) {
		
		Claim claim = claimRepository.findById(claimNo).orElseThrow(IllegalArgumentException::new);
		
		ClaimDTO claimDTO = modelMapper.map(claim, ClaimDTO.class);
		claimDTO.setClaimImageData(claim.getClaimImageData()); // 이미지 데이터 설정
		return modelMapper.map(claim, ClaimDTO.class);	
		// return claimDTO; 
	}
	
	// 작성자 : 최은지 
	// 민원 검색 ( 혜정님 버전 ) 
	public List<Claim> searchClaimByFilter(String filter, String value) {
		List<Claim> claimSearchList = claimRepository.findAll();
		boolean finalValueApprove = "승인".equals(value);
		boolean finalValueComplete = "완료".equals(value);

		return claimSearchList.stream()
							.filter(srh -> {
								if (filter.equals("claimTitle")) {
									return srh.getClaimTitle().contains(value);
								} else if (filter.equals("claimManagerName")) {
									return srh.getClaimManagerName().contains(value);
								} else if (filter.equals("claimApprove")) {
									return srh.getClaimApprove() == finalValueApprove;
								} else if (filter.equals("claimComplete")) {
									return srh.getClaimComplete() == finalValueComplete;
								}
								return List.of().isEmpty();
							}).collect(Collectors.toList());	
	}
	
	
	// 작성자 : 최은지
	// 민원 정렬 조회
	public List<Claim> sortClaim(String sort, String value) {
        
		List<Claim> claimSortList = claimRepository.findAll();

        return claimSortList.stream()
                            .filter(srt -> {
                                if (sort.equals("claimNotApprove")) {
                                    return !srt.getClaimApprove(); // 미승인 필터링
                                } else if (sort.equals("claimApprove")) {
                                    return srt.getClaimApprove() && !srt.getClaimComplete(); // 승인 필터링
                                } else if (sort.equals("claimComplete")) {
                                    return srt.getClaimComplete(); // 완료 필터링
                                }
                                return false; // 기본값
                            }).collect(Collectors.toList());
    }
	// 작성자 : 최은지
	// 민원 승인
	public void approveClaim(ClaimDTO claimDTO) {
		log.info("민원승인 서비스 실행");
		Claim claim =  claimRepository.findById(claimDTO.getClaimNo()).orElseThrow(IllegalArgumentException::new);
		
		Optional<ClaimSubCategory> subCategory = claimSubCategoryRepository.findById(claim.getClaimSubCategoryNo());
		String catogoryCode = subCategory.get().getLmdMinorCateCode();
		ListMedicalDevices medicalStatus = medicalRepository.findById(catogoryCode).get();		
		
		medicalStatus.setLmdStatus(claim.getClaimCategoryStatus());	
		claim.setClaimApprove(true);		
		
		medicalRepository.save(medicalStatus);
		claimRepository.save(claim);
	}
	
	// 작성자 : 최은지 
	// 민원 처리
	public void completeClaim(ClaimDTO claimDTO) {
		Claim claim =  claimRepository.findById(claimDTO.getClaimNo()).orElseThrow(IllegalArgumentException::new);
		Date currentDate = new Date(System.currentTimeMillis()); // 현재 날짜 가져오기 ( 임시.. )
		
		Optional<ClaimSubCategory> subCategory = claimSubCategoryRepository.findById(claim.getClaimSubCategoryNo());
		String catogoryCode = subCategory.get().getLmdMinorCateCode();
		ListMedicalDevices medicalStatus = medicalRepository.findById(catogoryCode).get();		
		
		medicalStatus.setLmdStatus("정상");
		medicalStatus.setLmdLastCheckDate(currentDate);
		medicalRepository.save(medicalStatus);
		
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
		ClaimStatusList.put("standby", 0);     // 접수     대기
		ClaimStatusList.put("progress", 0);    // 민원     처리
		ClaimStatusList.put("medicalCnt", 0);  // 의료기기 대기
		ClaimStatusList.put("safetyCnt", 0);   // 안전관리 대기
		
		List<Claim> claimList = claimRepository.findAll();
		
		for (Claim c: claimList) {
			if (! c.getClaimApprove()) { 
				ClaimStatusList.put("standby", ClaimStatusList.get("standby") + 1);
			} else if (c.getClaimApprove()) {
				ClaimStatusList.put("progress", ClaimStatusList.get("progress") + 1);
			} 
			
			if (c.getClaimCategoryName().equals("의료기기") && ! c.getClaimApprove()) {
				ClaimStatusList.put("medicalCnt", ClaimStatusList.get("medicalCnt") + 1);
			} else if (c.getClaimCategoryName().equals("안전관리") && ! c.getClaimApprove())	{
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
	    return claimSubCategoryRepository.findAll()
	            .stream()
	            .filter(category -> 
	                category.getLmdMinorCateName() != null && category.getLmdMinorCateName().equals(subCategoryName) || 
	                category.getSubCategoryName() != null && category.getSubCategoryName().equals(subCategoryName)
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
                Sort.by("claimSubCategoryNo").descending());
		
		Page<ClaimSubCategory> claimsubCategory = claimSubCategoryRepository.findAll(page);
		
		return claimsubCategory.map(subCategory -> modelMapper.map(subCategory, ClaimSubCategoryDTO.class));
	}
	
	// 작성자 : 최은지
	// 민원 소분류 검색
	public List<ClaimSubCategoryDTO> searchSubCategories(String filter, String value) {
        List<ClaimSubCategory> claimSubCategories;
        
        switch (filter) {
            case "lmdMinorCateName":
                claimSubCategories = claimSubCategoryRepository.findByLmdMinorCateNameContaining(value);
                log.info("claimSubCategories : {}", claimSubCategories);
                break;
            case "subCategoryName":
                claimSubCategories = claimSubCategoryRepository.findBySubCategoryNameContaining(value);
                break;
            default:
                claimSubCategories = List.of(); // 빈 리스트 반환
        }
        // 1) 현재 리턴 값(DTO list)저장후 , return new PageImpl<>(collect); 페이지로 반환해보기 
        return claimSubCategories.stream()
                .map(subCategory -> modelMapper.map(subCategory, ClaimSubCategoryDTO.class))
                .collect(Collectors.toList());
    }
	
	// 작성자 : 최은지
	// 부서 내 민원 목록 조회 ( 페이지 반환 )
	public Page<ClaimDTO> ManagerDeptClaim(Integer managerDeptNo, Pageable pageable) {

	    pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
	                               pageable.getPageSize(),
	                               Sort.by("claimNo").descending());

	    // 모든 목록을 페이지로 저장
	    Page<Claim> allClaimList = claimRepository.findByManagerDeptNo(managerDeptNo, pageable);

	    return allClaimList.map(cl -> modelMapper.map(cl, ClaimDTO.class));	
    }
	
	
	
	// 작성자 : 최은지 
	// 내가 쓴 민원 페이지로 반환
	public Page<ClaimDTO> managerClaim (String managerId , Pageable pageable) {
	    pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("claimNo").descending());
	    Page<Claim> myClaimList = claimRepository.findByManagerId(managerId, pageable); 
	    
	    return myClaimList.map(cl -> modelMapper.map(cl, ClaimDTO.class));
	}
	
	// 작성자 : 최은지
	// 민원 저장
	public void saveClaim(ClaimDTO claimDTO,  MultipartFile file, HttpSession session) throws IOException {
		
		Claim claim = new Claim();
		Date currentDate = new Date(System.currentTimeMillis()); // 현재 날짜 가져오기 ( 임시.. )
		
		// 이미지 저장
		if (file != null && !file.isEmpty()) {
			claim.setClaimImageData(file.getBytes());
	        claim.setClaimAttachment(file.getOriginalFilename());	
		} else {
		    log.warn("파일이 선택되지 않았습니다.");
		}
		
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
		
	    // 입력 받을 정보 : (1) 제목 , (2) 대분류, (3) 소분류, (4) 요청 구분, (5) 첨부파일, (6) 내용
		claim.setClaimTitle(claimDTO.getClaimTitle());                     // 제목
		claim.setClaimCategoryNo(category.getClaimCategoryNo());           // 대분류 번호   
		claim.setClaimCategoryName(claimDTO.getClaimCategoryName());       // 대분류 이름
		claim.setClaimSubCategoryNo(subCategory.getClaimSubCategoryNo());     // 소분류 번호
		claim.setClaimSubCategoryName(claimDTO.getClaimSubCategoryName()); // 소분류 이름
		claim.setClaimCategoryStatus(claimDTO.getClaimCategoryStatus());   // 요청 구분
		claim.setClaimContent(claimDTO.getClaimContent());  
	
		claim.setClaimDate(currentDate);
		claim.setClaimApprove(false);
		claim.setClaimComplete(false);
		log.info("claim : {}", claim);	
		claimRepository.save(claim);	
	}
	
	// 작성자 : 최은지 
	// 민원 수정
	public void updateClaim(Integer claimNo, ClaimDTO claimDTO,  MultipartFile file) throws IOException {
		
		Claim claim = claimRepository.findById(claimNo).get();
		if (file != null && !file.isEmpty()) {
			claim.setClaimImageData(file.getBytes());
	        claim.setClaimAttachment(file.getOriginalFilename());	
		} else {
		    log.warn("파일이 선택되지 않았습니다.");
		}
		
	    ClaimCategory category = findBycategoryName(claimDTO.getClaimCategoryName());
	    ClaimSubCategory subCategory = findBySubcategoryName(claimDTO.getClaimSubCategoryName());
		
		// 수정 가능한 정보 : (1) 제목 , (2) 대분류, (3) 소분류, (4) 요청 구분, (5) 첨부파일, (6) 내용
		claim.setClaimTitle(claimDTO.getClaimTitle());                     // 제목
		claim.setClaimCategoryNo(category.getClaimCategoryNo());           // 대분류 번호   
		claim.setClaimCategoryName(claimDTO.getClaimCategoryName());       // 대분류 이름
		claim.setClaimSubCategoryNo(subCategory.getClaimSubCategoryNo());     // 소분류 번호
		claim.setClaimSubCategoryName(claimDTO.getClaimSubCategoryName()); // 소분류 이름
		claim.setClaimCategoryStatus(claimDTO.getClaimCategoryStatus());   // 요청 구분
		claim.setClaimContent(claimDTO.getClaimContent());             // 내용
		
		// 변경 사항 저장
		claimRepository.save(claim);         
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
	// 민원 검색  
	public List<Claim> managerSearchClaimByFilter(String filter, String value, Integer managerDeptNo) {
		List<Claim> claimSearchList = findByManagerDeptClaim(managerDeptNo);
		boolean finalValueApprove = "승인".equals(value);
		boolean finalValueComplete = "완료".equals(value);

		return claimSearchList.stream()
							.filter(srh -> {
								if (filter.equals("claimTitle")) {
									return srh.getClaimTitle().contains(value);
								} else if (filter.equals("claimManagerName")) {
									return srh.getClaimManagerName().contains(value);
								} else if (filter.equals("claimApprove")) {
									return srh.getClaimApprove() == finalValueApprove;
								} else if (filter.equals("claimComplete")) {
									return srh.getClaimComplete() == finalValueComplete;
								}
								return List.of().isEmpty();
							}).collect(Collectors.toList());	
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

