package himedia.project.careops.controller.admin;

/**
 * @author 최은지 
 * @editDate 2024-09-30
 */

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
import himedia.project.careops.dto.ClaimDTO;
import himedia.project.careops.dto.ManagerDTO;
import himedia.project.careops.service.ClaimService;
import himedia.project.careops.service.ManagerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminClaimController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final ClaimService claimService;
	private final ManagerService managerService;
	
	public AdminClaimController(ClaimService claimService, ManagerService managerService) {
		this.claimService = claimService;
		this.managerService = managerService;
	}

	// [ 민원 조회 ] ==========================================================================
	// 민원 전체 목록
	@GetMapping("/claim-list")
	public String ClaimList(@PageableDefault Pageable page, Model model) {
		
		Page<ClaimDTO> claim =  claimService.allClaim(page);
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(claim);
		
		log.info("claim: {}", claim.toString());
		
		model.addAttribute("claim", claim);
		model.addAttribute("paging", paging);
		
		return "/admin/claim/claim-list";
	}
	
	// 민원 상세
	@GetMapping("/claim-detail/{claimNo}") 
	public String ClaimDetail(@PathVariable("claimNo") Integer claimNo, Model model) {
		
		log.info("민원 상세 controller 실행");
		ClaimDTO claim = claimService.findByClaimNo(claimNo);		
		ManagerDTO manager = managerService.findByManagerId(claim.getManagerId());
		model.addAttribute("claim", claim);
		model.addAttribute("manager", manager);
		
		return "/admin/claim/claim-detail";
	}
	
	// 민원 승인
	@PostMapping("/claim-approve")
	public String ClaimApprove(@ModelAttribute ClaimDTO claimDTO) {
		log.info("민원 승인 컨트롤러 실행");
		claimService.ApprveClaim(claimDTO);
		return "redirect:./claim-list";
	}
}
