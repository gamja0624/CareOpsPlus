package himedia.project.careops.controller.admin;

/**
 * @author 이홍준
 * @editDate 2024-09-24 ~ 2024-10-17
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import himedia.project.careops.dto.SafetyManagementDTO;
import himedia.project.careops.service.SafetyService;
import jakarta.servlet.http.HttpSession;

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

	// [안전관리 현황 페이지]
	@GetMapping("/safety-list")
	public String safetyList(Model model) {
		
		// SafetyManagement 테이블 모든 컬럼의 데이터 반환
		List<SafetyManagementDTO> safetyListAll = safetyService.safetyListAll();
		// 중복제거된 점검 항목 컬럼의 데이터 반환
		List<String> smList = safetyService.findSmList();


		model.addAttribute("smList", smList);
		model.addAttribute("safetyListAll", safetyListAll);

		return "admin/safety/safety-list";
	}

	// [층 버튼클릭 시 요청 메소드]
	@GetMapping("/safety-list/{smList}/{smFacilityFloor}")
	public ResponseEntity<List<SafetyManagementDTO>> loadDataForFloor(@PathVariable String smList,
																		@PathVariable int smFacilityFloor, Model model) {
		// 점검 항목, 층 수에 대한 데이터 반환
		List<SafetyManagementDTO> statusList = safetyService.findStatusList(smList, smFacilityFloor);
		
		model.addAttribute("smlList", smList);
		model.addAttribute("safetyListAll", statusList);

		return ResponseEntity.ok(statusList);
	}
	
	// [안전관리 현황 수정 페이지]
	@GetMapping("/safety-list-status")
	public String modifySafetyStatus(Model model) {
		
		// SafetyManagement 테이블 모든 컬럼의 데이터 반환
		List<SafetyManagementDTO> safetyListAll = safetyService.safetyListAll();
		// 중복제거된 점검 항목 컬럼의 데이터 반환
		List<String> smList = safetyService.findSmList();

		model.addAttribute("smList", smList);
		model.addAttribute("safetyListAll", safetyListAll);
		
		return "admin/safety/safety-list-status";
	}
	
	// [현황 수정 시 실시간 데이터 저장 처리]
	@PostMapping("/updateSmStatus")
	@ResponseBody
	public Map<String, Object> updateSmStatus(@RequestBody SafetyManagementDTO updateData, HttpSession session) {
	    
	    Map<String, Object> response = new HashMap<>();
	    
	    try {
	        // 클라이언트에서 넘어온 관리자 정보가 올바른지 검증 (optional)
	        String sessionAdminId = (String) session.getAttribute("userId");
	        if (!sessionAdminId.equals(updateData.getSmAdminId())) {
	            throw new IllegalArgumentException("관리자 정보가 일치하지 않습니다.");
	        }

	        // 세션에서 이미 불필요한 정보는 가져오지 않고, updateData에 포함된 정보를 그대로 사용
	        safetyService.updateStatus(updateData);
	        
	        response.put("success", true);
	    } catch (IllegalArgumentException e) {
	        response.put("success", false);
	        response.put("error", "잘못된 요청: " + e.getMessage());
	    } catch (Exception e) {
	        response.put("success", false);
	        response.put("error", "시스템 오류: " + e.getMessage());
	    }
	    
	    return response;
	}
}

