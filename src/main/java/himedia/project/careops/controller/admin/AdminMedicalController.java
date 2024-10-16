package himedia.project.careops.controller.admin;

/**
 * @author 진혜정
 * @editDate 2024-09-29 ~ 2024-10-14
 */

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping("/admin")
public class AdminMedicalController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final MedicalService medicalService;
	private final ManagerService managerService;
	private final ManagerDepartmentService managerDepartmentService;
	private final AdminService adminService;
	
	public AdminMedicalController(MedicalService medicalService, 
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
		
		//페이지네이션 관련 토탈 페이지 수 BE -> FE
		int totalPages = medicalDevicesList.getTotalPages();			    // 총 페이지 수 계산
		
		model.addAttribute("medicalDevicesList", medicalDevicesList);
		model.addAttribute("paging", paging);
		model.addAttribute("totalPages", totalPages);					// 총 페이지 수 View로 전달
		
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
	
	// [수정] ==========================================================================================
	@GetMapping("/medical-edit/getManagersByDeptName")
	@ResponseBody
	// 담당 부서 이름에 해당하는 담당자 목록을 가져오는 메서드
	public List<Manager> getManagersByDeptName(@RequestParam String managerDeptName) {
	    return managerService.findByManagerList(managerDeptName);
	}
	
	@GetMapping("/medical-edit/{lmdMinorCateCode}")
	public String medicalEditPage(@PathVariable String lmdMinorCateCode, Model model) {
		
		// 해당 의료기기 정보 반환
		ListMedicalDevicesDTO medicalDevice = medicalService.findByMedicalLmdMinorCateCode(lmdMinorCateCode);
		model.addAttribute("medicalDevice", medicalDevice);
		
		// 담당 부서 목록 토글로 반환
		List<ManagerDepartmentDTO> departments = managerDepartmentService.findAllDepartmentsList();
		model.addAttribute("departments", departments);
		
		// 담당자 반환
		List<Manager> managerList = managerService.findByManagerList(medicalDevice.getLmdManagerDeptPart());
		model.addAttribute("managerList", managerList);
		
		// 작업자 목록 토글로 반환
		List<AdminDTO> adminList = adminService.findAllAdminList();
		model.addAttribute("adminList", adminList);
		
		// 담당 부서의 매니저 정보 반환
		String managerDeptPart = medicalDevice.getLmdManagerDeptPart();
		List<Manager> managerinfo = managerService.findByManagerList(managerDeptPart);
		
		model.addAttribute("managerinfo", managerinfo);
		
		return "admin/medical/medical-edit";
	}

	@PostMapping("/medical-edit/{lmdMinorCateCode}")
	public String medicalEdit(
			@PathVariable String lmdMinorCateCode, ListMedicalDevicesDTO editMedical,
			@RequestParam String lmdStatus, @RequestParam String lmdDeviceCnt, 
			@RequestParam String lmdManagerDeptNo, @RequestParam String lmdManagerDeptPart, 
			@RequestParam String lmdManagerName, @RequestParam String lmdManagerId,
			@RequestParam String lmdAdminDeptNo, @RequestParam String lmdAdminId, @RequestParam String lmdAdminName, 
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date lmdLastCheckDate) {
		
//		log.info("lmdMinorCateCode   : {}", lmdStatus);
//		log.info("lmdDeviceCnt       : {}", lmdDeviceCnt);
//		log.info("lmdManagerDeptNo   : {}", lmdManagerDeptNo);
//		log.info("lmdManagerDeptPart : {}", lmdManagerDeptPart);
//		log.info("lmdManagerName     : {}", lmdManagerName);
//		log.info("lmdManagerId       : {}", lmdManagerId);
//		log.info("lmdAdminDeptNo     : {}", lmdAdminDeptNo);
//		log.info("lmdAdminId         : {}", lmdAdminId);
//		log.info("lmdAdminName       : {}", lmdAdminName);
//		log.info("lmdLastCheckDate   : {}", lmdLastCheckDate);
		
		// 해당 의료기기 정보 수정
		medicalService.editMedicalDevice(editMedical, lmdStatus, lmdDeviceCnt,  
				lmdManagerDeptNo, lmdManagerDeptPart, lmdManagerId, lmdManagerName, 
				lmdAdminDeptNo, lmdAdminId, lmdAdminName, lmdLastCheckDate);
		
		return "redirect:/admin/medical-detail/" + lmdMinorCateCode;
	}

}
