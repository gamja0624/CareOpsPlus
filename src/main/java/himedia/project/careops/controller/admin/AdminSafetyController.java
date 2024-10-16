package himedia.project.careops.controller.admin;

/**
 * @author 이홍준
 * @editDate 2024-10-15 
 */

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import himedia.project.careops.dto.SafetyManagementDTO;
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

	// 안전관리 목록 페이지
	@GetMapping("/safety-list")
	public String safetyList(Model model) {

		List<SafetyManagementDTO> safetyListAll = safetyService.safetyListAll();
		List<String> smList = safetyService.findSmList();

		log.info("smList값=>> {}", smList);

		model.addAttribute("smList", smList);
		// model.addAttribute("safetyListFirstPage", safetyListFirstPage);
		model.addAttribute("safetyListAll", safetyListAll);

		return "admin/safety/safety-list";
	}

	// 버튼클릭시 요청 메소드
	@GetMapping("/safety-list/{smList}/{smFacilityFloor}")
	public ResponseEntity<List<SafetyManagementDTO>> loadDataForFloor(@PathVariable String smList,
			@PathVariable int smFacilityFloor, Model model) {
		//log.info("뷰에서 넘어온 값 항목: {}, 층 수: {}", smList, smFacilityFloor );
		List<SafetyManagementDTO> statusList = safetyService.findStatusList(smList, smFacilityFloor);
		//log.info("검색 된 값 : {}", statusList);
		
		model.addAttribute("smlList", smList);
		model.addAttribute("safetyListAll", statusList);

		// log.info("층별 데이터>>>{}{}", smlList,allChecklist);

		return ResponseEntity.ok(statusList);
	}
	
	// 안전관리 현황 수정 페이지
	@GetMapping("/safety-list-status")
	public String modifySafetyStatus(Model model) {
		
		List<SafetyManagementDTO> safetyListAll = safetyService.safetyListAll();
		List<String> smList = safetyService.findSmList();

		log.info("smList값=>> {}", smList);

		model.addAttribute("smList", smList);
		// model.addAttribute("safetyListFirstPage", safetyListFirstPage);
		model.addAttribute("safetyListAll", safetyListAll);
		return "admin/safety/safety-list-status";
	}
}
