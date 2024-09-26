package himedia.project.careops.controller.admin;

/**
 * @author 이홍준
 * @editDate 2024-09-20 ~ 
 */

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import himedia.project.careops.dto.SafetyManagementDTO;
import himedia.project.careops.dto.SafetyManagementListDTO;
import himedia.project.careops.service.SafetyService;


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
	
	// 목록 페이지
	@GetMapping("/safety-list")
	public String smList(Model model) {
		
		  log.info("목록 페이지 실행");
		  
			/*
			 * List<SafetyManagementListDTO> safetyAllList = safetyService.findList();
			 * model.addAttribute("safetyAllList", safetyAllList);
			 * 
			 * log.info("네이티브쿼리 >>>> {}", safetyAllList);
			 */
			
			  List<SafetyManagementDTO> safetyResultList =
			  safetyService.safetyResultList(); log.info("목록 페이지 safetyResultList 실행");
			  List<SafetyManagementListDTO> safetyListAll = safetyService.safetyListAll();
			  
			  model.addAttribute("safetyResultList", safetyResultList);
			  model.addAttribute("safetyListAll", safetyListAll);
			 
		  
			
			  log.info("safetyResultList >>>{}", safetyResultList);
			  log.info("safetyListAll>>>{}", safetyListAll);
			 
		  
		return "admin/safety/safety-list";
	}
	
	// 점검표 항목 수정 페이지
	@GetMapping("/safety-checklist-edit")
	public String checkListEdit() {
		return "admin/safety/checklist-edit";
	}
	
	// 점검표 항목 상세 수정 페이지
//	@GetMapping("/checklist-edit/{sml_no}")
	@GetMapping("/checklist-edit/detail")
	public String checklistEditDetail(@PathVariable int sml_no, Model model) {
		
		// findBySmlNo()
		return "admin/safety/checklist-edit-detail";
	}
	
	// 데일리 점검목록 페이지
	@GetMapping("/safety-daily-registration")
	public String dailyResistraion() {
		return "admin/safety/daily-registration";
	}
	
	// 데일리 점검 목록 상세 등록 페이지
//	@GetMapping("/safety-daily-registration/{sml_no}")
	@GetMapping("/safety-daily-registration/{sml_no}")
	public String dailyResistraionDetail() {
		return "admin/safety/daily-registration-detail";
	}
}
