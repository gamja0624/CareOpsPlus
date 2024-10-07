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
import himedia.project.careops.dto.ListMedicalDevicesDTO;
import himedia.project.careops.dto.ManagerDepartmentDTO;
import himedia.project.careops.entity.Facility;
import himedia.project.careops.entity.Manager;
import himedia.project.careops.service.FacilityService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/manager")
public class ManagerFacilityController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final FacilityService facilityService;
	
	public ManagerFacilityController(FacilityService facilityService) {
		this.facilityService = facilityService;
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

	// [등록] ==========================================================================================
//	@GetMapping("/facility-add")
//	@ResponseBody
//	// 담당 부서 이름에 해당하는 담당자 목록을 가져오는 메서드
//	public List<Manager> getManagersByDeptName(@RequestParam String managerDeptName) {
//	    return facilityService.findByManagerList(managerDeptName);
//	}
	
	@GetMapping("/facility-add")
	public String facilityAddPage(Model model) {
		
		return "manager/facility/facility-add";
	}
	
//	@PostMapping("/facility-add")
//	public String facilityAdd(FacilityDTO newFacilityDevice) {
//		
//		facilityService.addFacilityRSVD(newFacilityRSVD);
//		
//		return "redirect:/facility/facility-list";
//	}
	
}
