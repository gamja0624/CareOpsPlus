package himedia.project.careops.controller.manager;

import java.util.List;

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

import ch.qos.logback.core.net.LoginAuthenticator;
import himedia.project.careops.common.Pagenation;
import himedia.project.careops.common.PagingButtonInfo;
import himedia.project.careops.dto.ClaimSubCategoryDTO;
import himedia.project.careops.dto.ManagerDTO;
import himedia.project.careops.entity.Manager;
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
	private final ManagerService managerService;
	
	public ManagerClaimController(ClaimService claimService, ManagerService managerService) {
		this.claimService = claimService;
		this.managerService = managerService;
	}

	// [ 민원 조회 ] ==========================================================================
	// (부서 내) 민원 목록
	@GetMapping("/claim-list")
	public String managerClaimList(Model model, HttpSession session) {
		
		String managerDeptName = (String) session.getAttribute("department");
		log.info("우리 부서 이름 : {}" , managerDeptName);
		
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
