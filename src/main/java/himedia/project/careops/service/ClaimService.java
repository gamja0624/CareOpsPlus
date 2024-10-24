package himedia.project.careops.service;

/**
 * @author 최은지
 * @editDate 2024-09-27 ~ 2024-10-16 
 */

import java.io.IOException;
import java.sql.Date;

import java.util.Calendar;
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
	            .orElseThrow(() -> new IllegalArgumentException("민원 번호가 유효하지 않습니다."));
	    byte[] imageData = claim.getClaimImageData();
	    
	    return imageData; 
	}

	// 작성자 : 최은지
	// 민원 상세 조회 
	public ClaimDTO findByClaimNo(Integer claimNo) {
		
		Claim claim = claimRepository.findById(claimNo).orElseThrow(IllegalArgumentException::new);
		
		ClaimDTO claimDTO = modelMapper.map(claim, ClaimDTO.class);
		claimDTO.setClaimImageData(claim.getClaimImageData()); 
		
		return modelMapper.map(claim, ClaimDTO.class);	
	}
	
	// 작성자 : 최은지 
	// 민원 검색 
	public List<Claim> searchClaimByFilter(String filter, String value) {
	    
		if (value == null || value.trim().isEmpty()) {
	        return List.of(); 
	    }
		
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
                                    return !srt.getClaimApprove(); 
                                } else if (sort.equals("claimApprove")) {
                                    return srt.getClaimApprove() && !srt.getClaimComplete(); 
                                } else if (sort.equals("claimComplete")) {
                                    return srt.getClaimComplete(); 
                                }
                                return false;
                            }).collect(Collectors.toList());
    }
	// 작성자 : 최은지
	// 민원 승인
	public void approveClaim(ClaimDTO claimDTO) {
		
		Claim claim =  claimRepository.findById(claimDTO.getClaimNo()).orElseThrow(IllegalArgumentException::new);
		claim.setClaimApprove(true);		
		claimRepository.save(claim);
		
		Optional<ClaimSubCategory> subCategory = claimSubCategoryRepository.findById(claim.getClaimSubCategoryNo());
		String catogoryCode = subCategory.get().getLmdMinorCateCode();
		
		if(catogoryCode != null) {
			ListMedicalDevices medicalStatus = medicalRepository.findById(catogoryCode).get();		
			medicalStatus.setLmdStatus(claim.getClaimCategoryStatus());	
			medicalRepository.save(medicalStatus);
		}
		
		
	}
	
	// 작성자 : 최은지 
	// 민원 처리
	public void completeClaim(ClaimDTO claimDTO) {
		
		Claim claim =  claimRepository.findById(claimDTO.getClaimNo()).orElseThrow(IllegalArgumentException::new);
		Date currentDate = new Date(System.currentTimeMillis()); 
		
		Optional<ClaimSubCategory> subCategory = claimSubCategoryRepository.findById(claim.getClaimSubCategoryNo());
		String catogoryCode = subCategory.get().getLmdMinorCateCode();
		
		if(catogoryCode != null) {
			ListMedicalDevices medicalStatus = medicalRepository.findById(catogoryCode).get();		
		
			medicalStatus.setLmdStatus("정상");
			medicalStatus.setLmdLastCheckDate(currentDate);
			medicalRepository.save(medicalStatus);
		}
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
	    Map<String, Integer> claimStatusList = new HashMap<>();
	    
	    // 변수 초기화
	    claimStatusList.put("standby", 0);     // 접수 대기
	    claimStatusList.put("progress", 0);    // 민원 처리
	    claimStatusList.put("medicalCnt", 0);  // 의료기기 대기
	    claimStatusList.put("safetyCnt", 0);   // 안전관리 대기
	    
	    List<Claim> claimList = claimRepository.findAll();
	    
	    for (Claim claim : claimList) {
	        updateClaimStatus(claim, claimStatusList);
	    }
	    
	    return claimStatusList;
	}

	private void updateClaimStatus(Claim claim, Map<String, Integer> claimStatusList) {
	    if (!claim.getClaimApprove()) {
	        claimStatusList.put("standby", claimStatusList.get("standby") + 1);
	        
	        // 카테고리에 따라 카운트 증가
	        String category = claim.getClaimCategoryName();
	        if (category.equals("의료기기")) {
	            claimStatusList.put("medicalCnt", claimStatusList.get("medicalCnt") + 1);
	        } else if (category.equals("안전관리")) {
	            claimStatusList.put("safetyCnt", claimStatusList.get("safetyCnt") + 1);
	        }
	    } else {
	        claimStatusList.put("progress", claimStatusList.get("progress") + 1);
	    }
	}
	
	// 작성자 : 진혜정
	// 년도, 월별로 민원수 카운트
	public Map<String, Integer> findByClaimDateStatus(int year) {
	    // 키, 값 형태로 저장할 변수 선언
	    Map<String, Integer> claimDateStatusList = new HashMap<>();
	    
	    // 1 ~ 12월 초기화
	    String[] months = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
	    for (String month : months) {
	        claimDateStatusList.put(month, 0);
	    }
	    
	    List<Claim> claimList = claimRepository.findAll();
	    
	    for (Claim claim : claimList) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(claim.getClaimDate());
	        
	        int dateYear = calendar.get(Calendar.YEAR); // 연도 추출
	        int dateMonth = calendar.get(Calendar.MONTH); // 월 추출 (0부터 시작)
	        
	        if (dateYear == year) {
	            // 각 월에 해당하는 카운트 증가
	            String monthKey = months[dateMonth]; // 월 키를 배열에서 추출
	            claimDateStatusList.put(monthKey, claimDateStatusList.get(monthKey) + 1);
	        }
	    }
	    
	    return claimDateStatusList;
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
		
		page = PageRequest.of(page.getPageNumber() <= 0 ? 0 : page.getPageNumber() - 1,
                page.getPageSize(),
                Sort.by("claimSubCategoryNo").ascending());
		
		Page<ClaimSubCategory> claimSubCategoryPage = claimSubCategoryRepository.findAll(page);
		
	    List<ClaimSubCategoryDTO> filteredList = claimSubCategoryPage.stream()
	            .filter(subCategory -> subCategory.getLmdMinorCateName() != null)
	            .map(subCategory -> modelMapper.map(subCategory, ClaimSubCategoryDTO.class))
	            .collect(Collectors.toList());
        
	    return new PageImpl<>(filteredList, page, claimSubCategoryPage.getTotalElements());
	}
	
	// 작성자 : 최은지
	// 민원 소분류 검색
	public List<ClaimSubCategoryDTO> searchSubCategories(String filter, String value) {
        
		List<ClaimSubCategory> claimSubCategories;
        
        if ("lmdMinorCateName".equals(filter)) {
            claimSubCategories = claimSubCategoryRepository.findByLmdMinorCateNameContaining(value);
        } else {
            claimSubCategories = List.of();  
        }
        
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
		Date currentDate = new Date(System.currentTimeMillis()); 
		
		// 이미지 저장
		if (file != null && !file.isEmpty()) {
			claim.setClaimImageData(file.getBytes());
	        claim.setClaimAttachment(file.getOriginalFilename());	
		}
		
		String managerId = (String) session.getAttribute("userId");
	    String managerName = (String) session.getAttribute("userName");
	    String deptNo = (String) session.getAttribute("deptNo");
	    int managerDeptNo = Integer.parseInt(deptNo);
	   
	    claim.setManagerId(managerId);
	    claim.setManagerDeptNo(managerDeptNo);
	    claim.setClaimManagerName(managerName);
	    
	    ClaimCategory category = findBycategoryName(claimDTO.getClaimCategoryName());
	    ClaimSubCategory subCategory = findBySubcategoryName(claimDTO.getClaimSubCategoryName());
		
		claim.setClaimTitle(claimDTO.getClaimTitle());                     
		claim.setClaimCategoryNo(category.getClaimCategoryNo());          
		claim.setClaimCategoryName(claimDTO.getClaimCategoryName());      
		claim.setClaimSubCategoryNo(subCategory.getClaimSubCategoryNo());    
		claim.setClaimSubCategoryName(claimDTO.getClaimSubCategoryName());
		claim.setClaimCategoryStatus(claimDTO.getClaimCategoryStatus()); 
		claim.setClaimContent(claimDTO.getClaimContent());  
	
		claim.setClaimDate(currentDate);
		claim.setClaimApprove(false);
		claim.setClaimComplete(false);
		
		claimRepository.save(claim);	
	}
	
	// 작성자 : 최은지 
	// 민원 수정
	public void updateClaim(Integer claimNo, ClaimDTO claimDTO,  MultipartFile file) throws IOException {
		
		Claim claim = claimRepository.findById(claimNo).get();
		
		if (file != null && !file.isEmpty()) {
			claim.setClaimImageData(file.getBytes());
	        claim.setClaimAttachment(file.getOriginalFilename());	
		} 
		
	    ClaimCategory category = findBycategoryName(claimDTO.getClaimCategoryName());
	    ClaimSubCategory subCategory = findBySubcategoryName(claimDTO.getClaimSubCategoryName());
		
		claim.setClaimTitle(claimDTO.getClaimTitle());                   
		claim.setClaimCategoryNo(category.getClaimCategoryNo());         
		claim.setClaimCategoryName(claimDTO.getClaimCategoryName());      
		claim.setClaimSubCategoryNo(subCategory.getClaimSubCategoryNo());    
		claim.setClaimSubCategoryName(claimDTO.getClaimSubCategoryName());
		claim.setClaimCategoryStatus(claimDTO.getClaimCategoryStatus());   
		claim.setClaimContent(claimDTO.getClaimContent());             
		
		claimRepository.save(claim);         
	}
	
	// 작성자 : 최은지
	// 부서별 민원 조회
	public List<Claim> findByManagerDeptClaim(Integer managerDeptNo) {
		
		return claimRepository.findAll()
				.stream()
				.filter(deptNo -> deptNo.getManagerDeptNo() == managerDeptNo)
				.collect(Collectors.toList());
	}
	
	// 작성자 : 최은지 
	// 민원 검색  
	public List<Claim> managerSearchClaimByFilter(String filter, String value, Integer managerDeptNo) {
		
		if (value == null || value.trim().isEmpty()) {
	        return List.of(); 
	    }
		
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

