package himedia.project.careops.controller.manager;

/**
 * @author 최은지
 * @editDate 2024-09-24 
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import himedia.project.careops.common.Pagenation;
import himedia.project.careops.common.PagingButtonInfo;
import himedia.project.careops.dto.ClaimSubCategoryDTO;
import himedia.project.careops.service.ClaimService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/manager")
public class ManagerClaimController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final ClaimService claimService;
	
	public ManagerClaimController(ClaimService claimService) {
		this.claimService = claimService;
	}

	// [ 민원 조회 ] ==========================================================================
	// (부서 내) 민원 목록
	@GetMapping("/claim-list")
	public String managerClaimList(Model model) {
		
		return "/manager/claim/claim-list";
	}
	
	// 민원 상세 
	@GetMapping("/claim-detail")
	public String managerClaimDetail() {
		return "/manager/claim/claim-detail";
	}
	
	// [ 민원 신청 ] ==========================================================================
	// 민원 신청 폼 이동 ( 카테고리 보내기 )
	@GetMapping("/claim-application")
	public String managerClaimAdd(@PageableDefault Pageable pageable, Model model) {
		
		Page<ClaimSubCategoryDTO> claimSubCategory = claimService.findAllSubCategory(pageable);
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(claimSubCategory);
		
		log.info("민원 소분류 카테고리: {}", claimSubCategory);
		
		model.addAttribute("claimSubCategory", claimSubCategory);
		model.addAttribute("paging", paging);
		
		return "/manager/claim/claim-form";
	}
	
}
