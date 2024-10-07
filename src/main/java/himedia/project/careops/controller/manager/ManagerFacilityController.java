package himedia.project.careops.controller.manager;

/**
 * @author 진혜정
 * @editDate 2024-10-03
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import himedia.project.careops.common.Pagenation;
import himedia.project.careops.common.PagingButtonInfo;
import himedia.project.careops.dto.FacilityDTO;
import himedia.project.careops.dto.ManagerDepartmentDTO;
import himedia.project.careops.entity.Facility;
import himedia.project.careops.service.FacilityService;
import himedia.project.careops.service.ManagerDepartmentService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/manager")
public class ManagerFacilityController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final FacilityService facilityService;
	private final ManagerDepartmentService managerDepartmentService;
	
	public ManagerFacilityController(FacilityService facilityService, ManagerDepartmentService managerDepartmentService) {
		this.facilityService = facilityService;
		this.managerDepartmentService = managerDepartmentService;
	}
	
	// [목록 조회] ======================================================================================
	@GetMapping("/facility-list")
	public String facilityList(@PageableDefault Pageable pageable, Model model) {
		
		Page<FacilityDTO> facilityList = facilityService.findByFacilityList(pageable);
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(facilityList);
		
		//페이지네이션 관련 토탈 페이지 수 BE -> FE
		int totalPages = facilityList.getTotalPages();
		
		model.addAttribute("facilityList", facilityList);
		model.addAttribute("paging", paging);
		model.addAttribute("totalPages", totalPages);
		
		return "manager/facility/facility-list";
	}
	
	// [검색 조회] ======================================================================================
	@GetMapping("/facility-list/search")
	public String searchList(@RequestParam String filter, @RequestParam String value, Model model) {
		
		List<Facility> facilityList = facilityService.findFilterfacilityList(filter, value);
		
		model.addAttribute("facilityList", facilityList);
		model.addAttribute("filter", filter);
		model.addAttribute("value", value);
		
		return "manager/facility/facility-search-list";
	}

	// [수정(예약 신청)] ==========================================================================================
	@GetMapping("/getfindByFacilityList")
	@ResponseBody
	// 층수 선택하면 시설 이름, 시설 번호를 가져오는 메서드
	public List<Facility> getfindByFacilityList(@RequestParam String facilityFloor) {
	    return facilityService.findByFloorFacilityList(Integer.parseInt(facilityFloor));
	}
	
	@GetMapping("/facility-add")
	public String facilityEditPage(Model model) {
		
		// 담당 부서 목록 반환 
		List<ManagerDepartmentDTO> departments = managerDepartmentService.findAllDepartmentsList();
		model.addAttribute("departments", departments);
		
		return "manager/facility/facility-add";
	}
	
	@PostMapping("/facility-add")
	public String facilityEdit(FacilityDTO editFacility) {
		
		facilityService.editFacility(editFacility);
		
		return "redirect:./facility-list";
	}
	
	// [수정(예약 취소)] ==========================================================================================
	@GetMapping("/facility-dept-list")
	public String DeptByfacilityPage(HttpSession session, @PageableDefault Pageable pageable, Model model) {
		
		String department = (String) session.getAttribute("department");
		
		List<FacilityDTO> facilityList = facilityService.findByDeptNoFacilityList(department);
		model.addAttribute("facilityList", facilityList);
		
		return "manager/facility/facility-dept-list";
	}
	
	@PostMapping("/facility-dept-list")
	public String DeptByfacility(@RequestParam String facilityNo) {
		
		Facility editFacility = facilityService.findByFacilityNo(facilityNo); 
		facilityService.cancelFacility(editFacility);
		
		return "redirect:./facility-dept-list";
	}
}
