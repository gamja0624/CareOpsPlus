package himedia.project.careops.controller.manager;

/**
 * @author 최은지
 * @editDate 2024-09-24
 */


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager/claim")
public class ClaimController {
	
	@GetMapping("claim-list")
	public String managerClaimList() {
		return "/manager/claim/claim-list";
	}
	
	@GetMapping("claim-application")
	public String managerClaimAdd() {
		return "/manager/claim/claim-form";
	}
	
	@GetMapping("claim-detail")
	public String managerClaimDetail() {
		return "/manager/claim/claim-detail";
	}
	
}
