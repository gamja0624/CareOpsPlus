package himedia.project.careops.controller.admin;

/**
 * @author 이홍준
 * @editDate 2024-09-20 ~ 
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import himedia.project.careops.dto.SafetyManagementChecklistDTO;
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
		/*
		 * List<SafetyManagementDTO> safetyResultList =
		 * safetyService.safetyResultList(); log.info("목록 페이지 safetyResultList 실행");
		 * log.info("safetyResultList >>>{}", safetyResultList); // sml table 호출
		 * List<SafetyManagementListDTO> safetyListAll = safetyService.safetyListAll();
		 * log.info("목록 페이지 safetyListAll 실행"); log.info("safetyListAll>>>{}",
		 * safetyListAll);
		 * 
		 * model.addAttribute("safetyResultList", safetyResultList);
		 * model.addAttribute("safetyListAll", safetyListAll);
		 */
		return "admin/safety/safety-list";
	}

	// 점검표 항목 수정 페이지
	@GetMapping("/safety-checklist-edit")
	public String checklistDetailEdit() {
		return "admin/safety/checklist-edit";
	}

	// 점검표 항목 상세 수정 페이지
	@GetMapping("/safety-checklist-edit/{smlList}")
	public String checklistEditDetail(@PathVariable String smlList, Model model) {

		List<SafetyManagementChecklistDTO> allChecklist = safetyService.findAllChecklist(smlList);
		
		model.addAttribute("smlList", smlList);
		model.addAttribute("allChecklist", allChecklist);
		
		//log.info("allChecklist >>>>>> {}", allChecklist);
		
		return "admin/safety/checklist-edit-detail";
	}
	
	// 버튼클릭시 요청 메소드
	@GetMapping("/safety-checklist-edit/{smlList}/{smcFloor}")
	public ResponseEntity<List<SafetyManagementChecklistDTO>> checklistEditFloor(@PathVariable String smlList, @PathVariable int smcFloor, Model model) {
		
		List<SafetyManagementChecklistDTO> allChecklist = safetyService.findCheckList(smlList, smcFloor);
		
		model.addAttribute("smlList", smlList);
		model.addAttribute("allChecklist", allChecklist);
		
		//log.info("층별 데이터>>>{}{}", smlList,allChecklist);
		
		return ResponseEntity.ok(allChecklist);
	}

	@PostMapping("/safety-checklist-edit/{smlList}")
    public String checklistEdit(@PathVariable String smlList, @RequestParam String smcFloor, 
            @RequestParam String smcNo, @RequestParam String smcList) {

        // log.info("대상 : {} ", smlList);
        // log.info("층 : {} ", smcFloor.charAt(0));

        String[] smcNos = smcNo.split(",");
        String[] smcLists = smcList.split(",");

        int numberOfEntries = Math.min(smcNos.length, smcLists.length);

        Map<String, String> checklistMap = new HashMap<>();

        for (int i = 0; i < numberOfEntries; i++) {
            String currentList = smcLists[i].trim(); // 공백 제거
            String currentNo = (i < smcNos.length) ? smcNos[i].trim() : ""; // 빈 문자열 처리
            checklistMap.put(currentNo, currentList);
        }

        safetyService.updateList(smlList, smcFloor.charAt(0), checklistMap);

        return "redirect:/admin/safety-checklist-edit";
    }
	
	// 데일리 점검목록 페이지
	@GetMapping("/safety-daily-registration")
	public String dailyResistraion() {
		return "admin/safety/daily-registration";
	}

	// 데일리 점검 목록 상세 등록 페이지
	@GetMapping("/safety-daily-registration/{smlList}")
	public String dailyResistraionDetail(@PathVariable String smlList, Model model) {
		
		return "admin/safety/daily-registration-detail";
	}
}
