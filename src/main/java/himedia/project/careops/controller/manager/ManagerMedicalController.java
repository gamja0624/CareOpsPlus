package himedia.project.careops.controller.manager;

/**
 * @author 진혜정
 * @editDate 2024-09-19 ~ 
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import himedia.project.careops.common.Pagenation;
import himedia.project.careops.common.PagingButtonInfo;
import himedia.project.careops.dto.AdminDTO;
import himedia.project.careops.dto.ListMedicalDevicesDTO;
import himedia.project.careops.dto.ManagerDepartmentDTO;
import himedia.project.careops.entity.ListMedicalDevices;
import himedia.project.careops.entity.Manager;
import himedia.project.careops.service.AdminService;
import himedia.project.careops.service.ManagerDepartmentService;
import himedia.project.careops.service.ManagerService;
import himedia.project.careops.service.MedicalService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/manager")
public class ManagerMedicalController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final MedicalService medicalService;
	private final ManagerService managerService;
	private final ManagerDepartmentService managerDepartmentService;
	private final AdminService adminService;
	
	public ManagerMedicalController(MedicalService medicalService, 
			ManagerService managerService, ManagerDepartmentService managerDepartmentService,
			AdminService adminService) {
		this.medicalService = medicalService;
		this.managerService = managerService;
		this.managerDepartmentService = managerDepartmentService;
		this.adminService = adminService;
	}

	// [목록 조회] ======================================================================================
	@GetMapping("/medical-list")
	public String medicalList(@PageableDefault Pageable pageable, Model model) {
		
		Page<ListMedicalDevicesDTO> medicalDevicesList = medicalService.findByMedicalDevices(pageable);
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(medicalDevicesList);
		
		// 총 페이지 수 계산
		int totalPages = medicalDevicesList.getTotalPages();			    
		
		model.addAttribute("medicalDevicesList", medicalDevicesList);
		model.addAttribute("paging", paging);
		model.addAttribute("totalPages", totalPages);					
		
		return "manager/medical/medical-list";
	}
	
	// [검색 조회] ======================================================================================
	@GetMapping("/medical-list/search")
	public String searchList(@RequestParam String filter, @RequestParam String value, Model model) {
		
		List<ListMedicalDevices> medicalDevicesList = medicalService.findFilterMedicalDevices(filter, value);
		
		model.addAttribute("medicalDevicesList", medicalDevicesList);
		model.addAttribute("filter", filter);
		model.addAttribute("value", value);
		
		return "manager/medical/medical-search-list";
	}
	
	// [상세 조회] ======================================================================================
	@GetMapping("/medical-detail/{lmdMinorCateCode}")
	public String medicalDetail(@PathVariable String lmdMinorCateCode, Model model) {
		
		// 해당 의료기기 정보 반환
		ListMedicalDevicesDTO medicalDevice = medicalService.findByMedicalLmdMinorCateCode(lmdMinorCateCode);
		model.addAttribute("medicalDevice", medicalDevice);
		
		// 작업자 목록
		List<AdminDTO> adminList = adminService.findAllAdminList();
		model.addAttribute("adminList", adminList);
		
		return "manager/medical/medical-detail";
	}
	
	// [등록] ==========================================================================================
	@GetMapping("/medical-add")
	public String medicalAddPage(Model model) {
		
		return "manager/medical/medical-add";
	}
	
	@PostMapping("/medical-add")
	public String medicalAdd(ListMedicalDevicesDTO newMedicalDevice) {
		
		medicalService.addMedicalDevice(newMedicalDevice);
		
		return "redirect:/manager/medical-list";
	}
}
