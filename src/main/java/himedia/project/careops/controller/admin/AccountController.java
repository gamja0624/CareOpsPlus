package himedia.project.careops.controller.admin;

import java.lang.annotation.Target;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import himedia.project.careops.common.Pagenation;
import himedia.project.careops.common.PagingButtonInfo;
import himedia.project.careops.dto.ManagerDTO;
import himedia.project.careops.dto.ManagerDepartmentDTO;
import himedia.project.careops.service.ManagerDepartmentService;
import himedia.project.careops.service.ManagerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AccountController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final ManagerService managerService;
	private final ManagerDepartmentService managerDepartmentService;
	
	public AccountController(ManagerService managerService, ManagerDepartmentService managerDepartmentService) {
		this.managerService = managerService;
		this.managerDepartmentService = managerDepartmentService;
	}
	
	// 전체 부서 조회
	@GetMapping("/account-department")
	public String accountDepartment(Model model) {
	    
		List<ManagerDepartmentDTO> managerDepartment= managerDepartmentService.findAllDepartmentsList();
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
	@GetMapping("/account-edit/{managerId}")
	public String accountEdit(@PathVariable("managerId") String managerId, Model model) {
		log.info("manager Id: {}", managerId);
		ManagerDTO manager = managerService.findByManagerId(managerId);
		log.info("managerDTO: {}", manager);
		model.addAttribute("manager", manager);
		return "admin/account/account-edit";
	}
	
	@PostMapping("/account-edit-complete")
    public String accuntEditComplete(@ModelAttribute ManagerDTO managerDTO) {
        // 이름과 연락처 업데이트
		log.info("담당자 변경 컨트롤러 실행");
		managerService.updateManager(managerDTO);
        return "redirect:./account-list"; // 성공적으로 업데이트 후 account-list로 리다이렉트
    }
}
