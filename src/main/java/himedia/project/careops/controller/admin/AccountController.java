package himedia.project.careops.controller.admin;

/**
 * @author 최은지 
 * @editDate 2024-09-20 ~ 2024-09-25
 */
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import himedia.project.careops.common.Pagenation;
import himedia.project.careops.common.PagingButtonInfo;
import himedia.project.careops.dto.ManagerDTO;
import himedia.project.careops.dto.ManagerDepartmentDTO;
import himedia.project.careops.service.ManagerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin/account")
public class AccountController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final ManagerService managerService;
	
	public AccountController(ManagerService managerService) {
		this.managerService = managerService;
	}
	
	// 전체 부서 조회
	@GetMapping("/account-department")
	public String accountDepartment(Model model) {
	    
		List<ManagerDepartmentDTO> managerDepartment= managerService.findAllDepartments();
	    log.info("Manager Departments: {}", managerDepartment);
	   
	    model.addAttribute("managerDepartment", managerDepartment);
	    return "admin/account/account-department";
	}
	
	@GetMapping("/account-list")
	public String accountList(@PageableDefault Pageable page, Model model) {
		
		Page<ManagerDTO> manager = managerService.allManager(page);
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(manager);
		
		log.info("Manager: {}", manager);
		model.addAttribute("manager", manager);
		model.addAttribute("paging", paging);
		
		return "admin/account/account-list";
	}
	
	// 담당자 등록 페이지
	@GetMapping("/account-add")
	public String accountAdd() {
		return "admin/account/account-add";
	}
	
	// 담당자 수정 페이지
	@GetMapping("/account-edit")
	public String accountEdit() {
		return "admin/account/account-edit";
	}
}
