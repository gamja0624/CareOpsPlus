package himedia.project.careops.controller.manager;

/**
 * @author 최은지
 * @editDate 2024-09-24 ~
 */

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import himedia.project.careops.common.Pagenation;
import himedia.project.careops.common.PagingButtonInfo;
import himedia.project.careops.dto.ClaimCategoryDTO;
import himedia.project.careops.dto.ClaimDTO;
import himedia.project.careops.dto.ClaimReplyDTO;
import himedia.project.careops.dto.ClaimSubCategoryDTO;
import himedia.project.careops.dto.ManagerDTO;
import himedia.project.careops.entity.ClaimCategory;
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
	public String managerClaimList( @PageableDefault Pageable pageable, Model model, HttpSession session ) {
		
		String managerDeptName = (String) session.getAttribute("department");
		String deptNoStr = (String) session.getAttribute("deptNo");
		Integer managerDeptNo = Integer.valueOf(deptNoStr);
		
		log.info("우리 부서 이름 : {}" , managerDeptName);
		log.info("우리 부서 번호 : {}", managerDeptNo);
		
		Page<ClaimDTO> claim = claimService.ManagerDeptClaim(managerDeptNo, pageable);
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(claim);
		List<ClaimReplyDTO> claimReply =  claimReplyService.claimReplyList(); 
		
		model.addAttribute("claim", claim);
		model.addAttribute("paging", paging);
		model.addAttribute("claimReply", claimReply);
		
		return "/manager/claim/claim-list";
	}
	
	// 민원 상세 
	@GetMapping("/claim-detail/{claimNo}") 
	public String managerClaimDetail(@PathVariable("claimNo") Integer claimNo, Model model) {
		
		log.info("민원 상세 controller 실행");
		ClaimDTO claim = claimService.findByClaimNo(claimNo);		
		ManagerDTO manager = managerService.findByManagerId(claim.getManagerId());
		model.addAttribute("claim", claim);
		model.addAttribute("manager", manager);
		
		return "/manager/claim/claim-detail";
	}
	
	// [ 민원 수정 ] =========================================================================
	@GetMapping("/claim-edit/{claimNo}")
	public String managerClaimEdit(@PathVariable("claimNo") Integer claimNo, Model model, @PageableDefault Pageable pageable) {
		
		// 민원 정보 
		ClaimDTO claim = claimService.findByClaimNo(claimNo);
		ManagerDTO manager = managerService.findByManagerId(claim.getManagerId());
		
		// 민원 대분류
		List<ClaimCategoryDTO> claimCategory = claimService.findAllCategory();
		
		// 민원 소분류
		Page<ClaimSubCategoryDTO> claimSubCategory = claimService.findAllSubCategory(pageable);
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(claimSubCategory);
		
		// 민원 정보
		model.addAttribute("claim", claim);
		model.addAttribute("manager", manager);
		
		// 민원 대분류
		model.addAttribute("claimCategory", claimCategory);
		
		// 민원 소분류
		model.addAttribute("claimSubCategory", claimSubCategory);
		model.addAttribute("paging", paging);
		
		return "/manager/claim/claim-edit";
	}
	
	@PostMapping("/claim-edit-complete")
	public String managerClaimEditSave(@ModelAttribute ClaimDTO claimDTO) {
		log.info("민원 수정 컨트롤러 실행");
		claimService.updateClaim(claimDTO);
		return "redirect:./claim-list";
	}
	
	// [ 민원 신청 ] ==========================================================================
	// 민원 신청 폼 이동 ( 대분류, 소분류 카테고리 보내기 )
	@GetMapping("/claim-add")
	public String managerClaimAdd(@PageableDefault Pageable pageable, Model model) {
		
		// 민원 대분류
		List<ClaimCategoryDTO> claimCategory = claimService.findAllCategory();
		
		// 민원 소분류
		Page<ClaimSubCategoryDTO> claimSubCategory = claimService.findAllSubCategory(pageable);
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(claimSubCategory);
		
		log.info("민원 소분류 카테고리: {}", claimSubCategory);
		log.info("민원 대분류 카테고리: {}", claimCategory);
		
		model.addAttribute("claimCategory", claimCategory);
		model.addAttribute("claimSubCategory", claimSubCategory);
		model.addAttribute("paging", paging);
		
		return "/manager/claim/claim-form";
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
