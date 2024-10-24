package himedia.project.careops.controller.manager;

/**
 * @author 최은지
 * @editDate 2024-09-24 ~
 */

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import himedia.project.careops.common.Pagenation;
import himedia.project.careops.common.PagingButtonInfo;
import himedia.project.careops.dto.ClaimCategoryDTO;
import himedia.project.careops.dto.ClaimDTO;
import himedia.project.careops.dto.ClaimReplyDTO;
import himedia.project.careops.dto.ClaimSubCategoryDTO;
import himedia.project.careops.dto.ManagerDTO;
import himedia.project.careops.entity.Claim;
import himedia.project.careops.service.ClaimReplyService;
import himedia.project.careops.service.ClaimService;
import himedia.project.careops.service.ManagerService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/manager")
public class ManagerClaimController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final ClaimService claimService;
	private final ClaimReplyService claimReplyService;
	private final ManagerService managerService;
	
	public ManagerClaimController(ClaimService claimService, ClaimReplyService claimReplyService, ManagerService managerService) {
		this.claimService = claimService;
		this.claimReplyService = claimReplyService;
		this.managerService = managerService;
	}

	// [ 민원 조회 ] ==========================================================================
	// (부서 내) 민원 목록
	@GetMapping("/claim-list")
	public String managerClaimList(@PageableDefault Pageable pageable, Model model, HttpSession session) {

		String deptNoStr = (String) session.getAttribute("deptNo");
		Integer managerDeptNo = Integer.valueOf(deptNoStr);
		
		Page<ClaimDTO> claim = claimService.ManagerDeptClaim(managerDeptNo, pageable);
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(claim);
		List<ClaimReplyDTO> claimReply =  claimReplyService.claimReplyList(); 
		int totalPages = claim.getTotalPages();			    // 총 페이지 수 계산
		
		model.addAttribute("claim", claim);
		model.addAttribute("paging", paging);
		model.addAttribute("claimReply", claimReply);
		model.addAttribute("totalPages", totalPages);	
		
		return "/manager/claim/claim-list";
	}
	// (내 민원) 민원 목록
	@GetMapping("/claim-my-list")
	public String myClaimList(@PageableDefault Pageable pageable, Model model, HttpSession session) {
		
		String managerId = (String) session.getAttribute("userId");
		
		Page<ClaimDTO> myClaim = claimService.managerClaim(managerId, pageable);
		List<ClaimReplyDTO> claimReply =  claimReplyService.claimReplyList(); 
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(myClaim);
		int totalPages = myClaim.getTotalPages();
		
		model.addAttribute("myClaim", myClaim);
		model.addAttribute("claimReply", claimReply);
		model.addAttribute("paging", paging);
		model.addAttribute("totalPages", totalPages);	
		
		return "/manager/claim/claim-my-list";
	}
	
	// 민원 검색 결과 조회 페이지
	@GetMapping("/claim-list/search")
	public String claimSearchFitler(@RequestParam String filter, @RequestParam String value, HttpSession session, Model model) {
		
		
		String deptNoStr = (String) session.getAttribute("deptNo");
		Integer managerDeptNo = Integer.valueOf(deptNoStr);
		
		List<Claim> claimSearch = claimService.managerSearchClaimByFilter(filter, value, managerDeptNo);
		List<ClaimReplyDTO> claimReply =  claimReplyService.claimReplyList();
		
		model.addAttribute("claimSearch", claimSearch);
		model.addAttribute("claimReply", claimReply);
		
		return "/manager/claim/claim-search-list";
	}
	
	// 민원 이미지 조회
	@GetMapping("/claim-image/{claimNo}")
	public ResponseEntity<byte[]> MangerGetClaimImage(@PathVariable ("claimNo") Integer claimNo) {
	   
		byte[] imageData = claimService.claimImageData(claimNo);
	    
	    if (imageData != null) {
	        return ResponseEntity.ok()
	                .contentType(MediaType.IMAGE_JPEG) // 필요에 따라 MediaType 타입 변경
	                .body(imageData);
	    } else {
	    	return ResponseEntity.noContent().build(); // 204 No Content 반환 (아무것도 없음)
	    }
	}
	
	// 민원 상세 
	@GetMapping("/claim-detail/{claimNo}") 
	public String managerClaimDetail(@PathVariable("claimNo") Integer claimNo, Model model) {
		
		ClaimDTO claim = claimService.findByClaimNo(claimNo);		
		ManagerDTO manager = managerService.findByManagerId(claim.getManagerId());
		
		model.addAttribute("claim", claim);
		model.addAttribute("manager", manager);
		
		return "/manager/claim/claim-detail";
	}
	

	// [ 민원 수정 ] =========================================================================
	@GetMapping("/claim-edit/{claimNo}")
	public String managerClaimEdit(@PathVariable("claimNo") Integer claimNo, Model model, @PageableDefault Pageable pageable) {
		
		ClaimDTO claim = claimService.findByClaimNo(claimNo);
		ManagerDTO manager = managerService.findByManagerId(claim.getManagerId());
		
		List<ClaimCategoryDTO> claimCategory = claimService.findAllCategory();
		Page<ClaimSubCategoryDTO> claimSubCategory = claimService.findAllSubCategory(pageable);
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(claimSubCategory);
		int totalPages = claimSubCategory.getTotalPages();			    // 총 페이지 수 계산
		
		model.addAttribute("claim", claim);
		model.addAttribute("manager", manager);
		model.addAttribute("claimCategory", claimCategory);
		model.addAttribute("claimSubCategory", claimSubCategory);
		model.addAttribute("paging", paging);
		model.addAttribute("totalPages", totalPages);	
		
		return "/manager/claim/claim-edit";
	}
	
	@PostMapping("/claim-edit-complete/{claimNo}")
	public String managerClaimEditSave(@PathVariable("claimNo") Integer claimNo,  @ModelAttribute ClaimDTO claimDTO,  @RequestParam("file") MultipartFile file) {
		
		try {
			claimService.updateClaim(claimNo, claimDTO, file);
			return "redirect:/manager/claim-list";
		} catch (IOException e) {
			return "이미지 저장에 실패했습니다.";
		}
	}
	
	// [ 민원 신청 ] ==========================================================================
	// 민원 신청 폼 이동 ( 대분류, 소분류 카테고리 보내기 )
	@GetMapping("/claim-add")
	public String managerClaimForm(@PageableDefault Pageable pageable, Model model) {
		
		List<ClaimCategoryDTO> claimCategory = claimService.findAllCategory();
		Page<ClaimSubCategoryDTO> claimSubCategory = claimService.findAllSubCategory(pageable);
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(claimSubCategory);
		int totalPages = claimSubCategory.getTotalPages();			    // 총 페이지 수 계산
		
		model.addAttribute("claimCategory", claimCategory);
		model.addAttribute("claimSubCategory", claimSubCategory);
		model.addAttribute("paging", paging);
		model.addAttribute("totalPages", totalPages);	
		
		return "/manager/claim/claim-form";
	}
	
	// 민원 저장 
	@PostMapping("/claim-submit")
	public String managerClaimSubmit(@ModelAttribute ClaimDTO claimDTO, @RequestParam("file") MultipartFile file, HttpSession session) {
		
		try {
			claimService.saveClaim(claimDTO, file, session);
			return "redirect:./claim-list";
		} catch (IOException e) {
			return "이미지 저장에 실패했습니다.";
		}
	}
	
	
	@GetMapping("/claim-sub-list/search")
    public ResponseEntity<List<ClaimSubCategoryDTO>> searchClaimSubCategories(
            @RequestParam String filter,
            @RequestParam String value) {
       
		List<ClaimSubCategoryDTO> results = claimService.searchSubCategories(filter, value);
       
		return ResponseEntity.ok(results);
    }

	// [ 답변 조회 ] ===========================================================================
    @GetMapping("/claim-re-detail/{claimNo}")
    public String ManagerclaimReDetail(@PathVariable("claimNo") Integer claimNo, Model model) {
    	
    	ClaimDTO claim = claimService.findByClaimNo(claimNo);		
    	ManagerDTO manager = managerService.findByManagerId(claim.getManagerId());
    	ClaimReplyDTO claimReply = claimReplyService.findClaimReply(claimNo);
    	
    	model.addAttribute("claim", claim);
    	model.addAttribute("manager", manager);
    	model.addAttribute("claimReply", claimReply);
    	
    	return "/manager/claim/claim-re-detail";
    }
	
}
