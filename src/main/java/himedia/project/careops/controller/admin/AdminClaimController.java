package himedia.project.careops.controller.admin;

import java.util.List;

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
import himedia.project.careops.dto.ClaimReplyDTO;
import himedia.project.careops.dto.ManagerDTO;
import himedia.project.careops.service.ClaimReplyService;
import himedia.project.careops.service.ClaimService;
import himedia.project.careops.service.ManagerService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminClaimController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final ClaimService claimService;
	private final ClaimReplyService claimReplyService;
	private final ManagerService managerService;
	
	public AdminClaimController(ClaimService claimService, ClaimReplyService claimReplyService, ManagerService managerService) {
		this.claimService = claimService;
		this.claimReplyService = claimReplyService;
		this.managerService = managerService;
	}

	// [ 민원 조회 ] ==========================================================================
	// 민원 전체 목록
	@GetMapping("/claim-list")
	public String claimList(@PageableDefault Pageable page, Model model) {
		
		Page<ClaimDTO> claim =  claimService.allClaim(page);
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(claim);
		
		List<ClaimReplyDTO> claimReply =  claimReplyService.claimReplyList(); 
		log.info("컨트롤러 - claimReply 답변 목록: {}", claimReply);
		model.addAttribute("claim", claim);
		model.addAttribute("paging", paging);
		model.addAttribute("claimReply", claimReply);
		
		
		return "/admin/claim/claim-list";
	}
	
	// 민원 상세
	@GetMapping("/claim-detail/{claimNo}") 
	public String claimDetail(@PathVariable("claimNo") Integer claimNo, Model model) {
		
		ClaimDTO claim = claimService.findByClaimNo(claimNo);		
		ManagerDTO manager = managerService.findByManagerId(claim.getManagerId());
		model.addAttribute("claim", claim);
		model.addAttribute("manager", manager);
		
		return "/admin/claim/claim-detail";
	}
	
	// [ 민원 변경 ] ==========================================================================
	// 민원 승인
	@PostMapping("/claim-approve")
	public String claimApprove(@ModelAttribute ClaimDTO claimDTO) {
		claimService.approveClaim(claimDTO);
		return "redirect:./claim-list";
	}
	
	// 민원 처리
	@PostMapping("/claim-complete/{claimNo}")
	public String claimComplete(@PathVariable("claimNo") Integer claimNo, @ModelAttribute ClaimDTO claimDTO) {
		claimService.completeClaim(claimDTO);
		return "redirect:/admin/claim-re/"+claimNo;
	}
	
	// [ 답변 저장 및 조회 ] ==========================================================================
	// 답변 작성
	@GetMapping("/claim-re/{claimNo}")
	public String claimReplyForm(@PathVariable("claimNo") Integer claimNo, Model model) {
		log.info("답변 작성에 필요한 claimNo : {}", claimNo);		
		ClaimDTO claim = claimService.findByClaimNo(claimNo);		
		ManagerDTO manager = managerService.findByManagerId(claim.getManagerId());
		
		model.addAttribute("claim", claim);
		model.addAttribute("manager", manager);
		return "/admin/claim/claim-re-form";
	}
	
	// 답변 저장
	@PostMapping("/claim-reply-save/{claimNo}")
	public String claimReplySave(@PathVariable("claimNo") Integer claimNo, @ModelAttribute ClaimReplyDTO claimReplyDTO, HttpSession session) {
		log.info("claimReplyDTO 저장되는 답변 정보 : {}", claimReplyDTO);
		claimReplyService.saveClaimReply(claimReplyDTO, claimNo, session);
		return "redirect:/admin/claim-list";
	}
	
	// 민원 답변 상세 페이지로 이동
    @GetMapping("/claim-re-detail/{claimNo}")
    public String claimReDetail(@PathVariable("claimNo") Integer claimNo, Model model) {
    	
    	ClaimDTO claim = claimService.findByClaimNo(claimNo);		
    	ManagerDTO manager = managerService.findByManagerId(claim.getManagerId());
    	ClaimReplyDTO claimReply = claimReplyService.findClaimReply(claimNo);
    	
    	model.addAttribute("claim", claim);
    	model.addAttribute("manager", manager);
    	model.addAttribute("claimReply", claimReply);
    	return "/admin/claim/claim-re-detail";
    }

}
