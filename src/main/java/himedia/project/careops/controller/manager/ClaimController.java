package himedia.project.careops.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager/claim")
public class ClaimController {
	
	@GetMapping("claim-list")
	public String departmentsClaimList() {
		return "/manager/claim-list";
	}
	
}
