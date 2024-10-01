package himedia.project.careops.controller.admin;

/**
 * @author 이홍준
 * @editDate 2024-09-20 ~ 
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import himedia.project.careops.common.Pagenation;
import himedia.project.careops.common.PagingButtonInfo;
import himedia.project.careops.dto.SafetyManagementChecklistDTO;
import himedia.project.careops.dto.SafetyManagementDTO;
import himedia.project.careops.dto.SafetyManagementListDTO;
import himedia.project.careops.service.SafetyService;

@Controller
@RequestMapping("/admin")
public class AdminSafetyController {

	// 로그
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// 서비스
	private final SafetyService safetyService;

	public AdminSafetyController(SafetyService safetyService) {
		this.safetyService = safetyService;
	}

	// 목록 페이지
	@GetMapping("/safety-list")
	public String smList(Model model) {

		log.info("목록 페이지 실행");

		/*
		 * List<SafetyManagementListDTO> safetyAllList = safetyService.findAllList();
		 * model.addAttribute("safetyAllList", safetyAllList);
		 * 
		 * log.info("네이티브쿼리 >>>> {}", safetyAllList);
		 */

		// sm table 호출
		List<SafetyManagementDTO> safetyResultList = safetyService.safetyResultList();
		log.info("목록 페이지 safetyResultList 실행");
		log.info("safetyResultList >>>{}", safetyResultList);
		// sml table 호출
		List<SafetyManagementListDTO> safetyListAll = safetyService.safetyListAll();
		log.info("목록 페이지 safetyListAll 실행");
		log.info("safetyListAll>>>{}", safetyListAll);

		model.addAttribute("safetyResultList", safetyResultList);
		model.addAttribute("safetyListAll", safetyListAll);

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
	/* public String checklistEditDetail(@PathVariable int sml_no, Model model) { */
	public String checklistEditDetail(@PageableDefault Pageable pageable, Model model) {

		Page<SafetyManagementChecklistDTO> allChecklist = safetyService.findAllChecklist(pageable);
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(allChecklist);

		model.addAttribute("allChecklist", allChecklist);
		model.addAttribute("paging", paging);
		log.info("allChecklist >>>>>> {}", allChecklist);
		
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
