package himedia.project.careops.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/account")
public class AccountController {

	@GetMapping("/account-list")
	public String accountList() {
		return "/admin/account/account-list";
	}
	
	@GetMapping("/account-add")
	public String accountAdd() {
		return "/admin/account/account-add";
	}
}
