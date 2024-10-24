package himedia.project.careops.controller.admin;

/**
 * @author 최은지 
 * @editDate 2024-09-20 ~ 2024-09-25
 */

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import himedia.project.careops.common.Pagenation;
import himedia.project.careops.common.PagingButtonInfo;
import himedia.project.careops.dto.ManagerDTO;
import himedia.project.careops.dto.ManagerDepartmentDTO;
import himedia.project.careops.entity.Manager;
import himedia.project.careops.service.ManagerDepartmentService;
import himedia.project.careops.service.ManagerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminAccountController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final ManagerService managerService;
	private final ManagerDepartmentService managerDepartmentService;
	
	public AdminAccountController(ManagerService managerService, ManagerDepartmentService managerDepartmentService) {
		this.managerService = managerService;
		this.managerDepartmentService = managerDepartmentService;
	}
	
	
	@GetMapping("/account-list")
	public String accountList(@PageableDefault Pageable page, Model model) {
		
		Page<ManagerDTO> manager = managerService.allManager(page);
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(manager);
		int totalPages = manager.getTotalPages();
		
		model.addAttribute("manager", manager);
		model.addAttribute("paging", paging);
		model.addAttribute("totalPages", totalPages);	
		
		return "admin/account/account-list";
	}
	// 담당자 검색
	@GetMapping("/account-list/search")
	public String accountSearchFitler(@RequestParam String filter, @RequestParam String value, Model model) {

		List<Manager> managerSearch = managerService.searchManagerByFilter(filter, value);
		
		model.addAttribute("managerSearch", managerSearch);
		
		return "admin/account/account-search-list";
	}
	
	
	// 담당자 등록 페이지
	@GetMapping("/account-add")
	public String accountAdd(Model model) {
		
		List<ManagerDepartmentDTO> managerDepartment= managerDepartmentService.findAllDepartmentsList();
		
		model.addAttribute("managerDepartment", managerDepartment);
		
		return "admin/account/account-add";
	}
	
	 
	// 아이디 중복 검사
	@GetMapping("/managerId-check/{managerId}")
	public String managerIdCheck(@PathVariable("managerId") String managerId,
            @RequestParam("managerDeptName") String managerDeptName,
            @RequestParam("managerName") String managerName,
            @RequestParam("managerPhoneNumber") String managerPhoneNumber,
            RedirectAttributes redirectAttributes) {
		
		boolean check = managerService.checkMangerId(managerId);
		
		if (check) {
	        redirectAttributes.addFlashAttribute("message", "이미 존재하는 아이디입니다.");
	    } else {
	        redirectAttributes.addFlashAttribute("message", "사용 가능한 아이디입니다.");
	    }
		
		// 입력값 저장 (폼 데이터 초기화 방지)
		redirectAttributes.addFlashAttribute("managerId", managerId);
		redirectAttributes.addFlashAttribute("managerDeptName", managerDeptName);
		redirectAttributes.addFlashAttribute("managerName", managerName);
		redirectAttributes.addFlashAttribute("managerPhoneNumber", managerPhoneNumber);
		
		return "redirect:/admin/account-add";
	}
	
	// 담당자 등록 - 저장  
	@PostMapping("/account-add-complete")
	public String accountSave(@ModelAttribute ManagerDTO managerDTO) {
		
		ManagerDepartmentDTO department = managerDepartmentService.findByDeptName(managerDTO.getManagerDeptName());
		managerService.saveManager(managerDTO, department);
		
		return "redirect:./account-list";
	}
	
	// 담당자 수정 페이지
	@GetMapping("/account-edit/{managerId}")
	public String accountEdit(@PathVariable("managerId") String managerId, Model model) {
		
		ManagerDTO manager = managerService.findByManagerId(managerId);
		
		model.addAttribute("manager", manager);
		
		return "admin/account/account-edit";
	}
	
	@PostMapping("/account-edit-complete")
    public String accuntEditComplete(@ModelAttribute ManagerDTO managerDTO) {
		
		managerService.updateManager(managerDTO);
       
		return "redirect:./account-list"; 
    }
}
