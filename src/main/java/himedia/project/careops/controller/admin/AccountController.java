package himedia.project.careops.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
/**
 * @author 최은지 
 * @editDate 2024-09-20 ~ 2024-09-25
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import himedia.project.careops.dto.ManagerDepartmentDTO;
import himedia.project.careops.service.ManagerService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/admin/account")
public class AccountController {
	
	@Autowired
	private ManagerService managerService;
	
	@GetMapping("/account-department")
	public String accountDepartment(Model model) {
	    // 전체 부서 조회
	    List<ManagerDepartmentDTO> managerDepartment= managerService.findAllDepartments();
	    log.info("Manager Departments: {}", managerDepartment);
	    model.addAttribute("managerDepartment", managerDepartment);
	    return "admin/account/account-department";
	}
	@GetMapping("/account-list")
	public String accountList() {
		return "admin/account/account-list";
	}
	
	@GetMapping("/account-add")
	public String accountAdd() {
		return "admin/account/account-add";
	}
	
	@GetMapping("/account-edit")
	public String accountEdit() {
		return "admin/account/account-edit";
	}
}
