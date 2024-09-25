package himedia.project.careops.controller.admin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import himedia.project.careops.dto.SafetyManagementDTO;
import himedia.project.careops.dto.SafetyManagementListDTO;
import himedia.project.careops.service.SafetyService;

/**
 * @author 이홍준
 * @editDate 2024-09-20 ~ 
 */

@Controller
@RequestMapping("/admin")
public class SafetyController{
	
	// 로그
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	// 서비스
	private final SafetyService safetyService;
	
	public SafetyController(SafetyService safetyService) {
		this.safetyService = safetyService;
	}

	@GetMapping("/safety-list")
	public String smList(Model model) {
		/*
		 * log.info("목록 페이지 실행");
		 * 
		 * List<SafetyManagementDTO> safetyResultList =
		 * safetyService.safetyResultList(); log.info("목록 페이지 safetyResultList 실행");
		 * List<SafetyManagementListDTO> safetyListAll = safetyService.safetyListAll();
		 * 
		 * model.addAttribute("safetyResultList", safetyResultList);
		 * model.addAttribute("safetyListAll", safetyListAll);
		 * 
		 * log.info("safetyResultList >>>{}", safetyResultList);
		 * log.info("safetyListAll>>>{}", safetyListAll);
		 */
		
		return "admin/safety/safety-list";
	}
	
	@GetMapping("/safety-checklist-edit")
	public String checkListEdit() {
		return "admin/safety/checklist-edit";
	}
	
//	@GetMapping("/checklist-edit/{sml_no}")
	@GetMapping("/checklist-edit/detail")
	public String checklistEditDetail(@PathVariable int sml_no, Model model) {
		
		// findBySmlNo()
		return "admin/safety/checklist-edit-detail";
	}
	
	@GetMapping("/safety-daily-registration")
	public String dailyResistraion() {
		return "admin/safety/daily-registration";
	}
	
	@PostMapping("/safety-daily-registration")
	public String resistrationComple() {
		return "redirect:/admin/safety/daily-registration";
	}
	
//	@GetMapping("/safety-daily-registration/{sml_no}")
	@GetMapping("/safety-daily-registration/detail")
	public String dailyResistraionDetail() {
		return "admin/safety/daily-registration-detail";
	}
}
