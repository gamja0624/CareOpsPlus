package himedia.project.careops.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 진혜정
 * @editDate 2024-09-29
 */

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
import himedia.project.careops.entity.Manager;
import himedia.project.careops.service.AdminDepartmentService;
import himedia.project.careops.service.AdminService;
import himedia.project.careops.service.ManagerDepartmentService;
import himedia.project.careops.service.ManagerService;
import himedia.project.careops.service.MedicalService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin/medical-list")
public class AdminMedicalController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final MedicalService medicalService;
	
	private final ManagerService managerService;
	private final ManagerDepartmentService managerDepartmentService;
	
	private final AdminService adminService;
	private final AdminDepartmentService adminDepartmentService;
	
	public AdminMedicalController(
			MedicalService medicalService, 
			ManagerService managerService, ManagerDepartmentService managerDepartmentService,
			AdminService adminService, AdminDepartmentService adminDepartmentService) {
		this.medicalService = medicalService;
		this.managerService = managerService;
		this.managerDepartmentService = managerDepartmentService;
		this.adminService = adminService;
		this.adminDepartmentService = adminDepartmentService;
	}

	// [목록 조회] ======================================================================================
	@GetMapping("")
	public String medicalList(@PageableDefault Pageable pageable, Model model) {
		
		Page<ListMedicalDevicesDTO> medicalDevicesList = medicalService.findByMedicalDevices(pageable);
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(medicalDevicesList);
		
		model.addAttribute("medicalDevicesList", medicalDevicesList);
		model.addAttribute("paging", paging);
		
		return "manager/medical/medical-list";
	}
	
	// [상세 조회] ======================================================================================
	@GetMapping("/{lmdMinorCateCode}/detail")
	public String medicalDetail(@PathVariable String lmdMinorCateCode, Model model) {
		
		ListMedicalDevicesDTO medicalDevice = medicalService.findByMedicalLmdMinorCateCode(lmdMinorCateCode);
		model.addAttribute("medicalDevice", medicalDevice);
		
		return "manager/medical/medical-detail";
	}
	
	// [수정] ==========================================================================================
	@GetMapping("/getManagersByDeptName")
	@ResponseBody
	// 담당 부서 이름에 해당하는 담당자 목록을 가져오는 메서드
	public List<Manager> getManagersByDeptName(@RequestParam String managerDeptName) {
	    return managerService.findByManagerList(managerDeptName);
	}
	
	@GetMapping("/{lmdMinorCateCode}/edit")
	public String medicalEditPage(@PathVariable String lmdMinorCateCode, Model model) {
		
		// 해당 의료기기 정보 반환
		ListMedicalDevicesDTO medicalDevice = medicalService.findByMedicalLmdMinorCateCode(lmdMinorCateCode);
		model.addAttribute("medicalDevice", medicalDevice);
		
		// 담당 부서 목록 토글로 반환
		List<ManagerDepartmentDTO> departments = managerDepartmentService.findAllDepartmentsList();
		model.addAttribute("departments", departments);
		
		// 담당자 반환
		List<Manager> managerList = managerService.findByManagerList(medicalDevice.getLmdAdminDeptNo());
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

	@PostMapping("/{lmdMinorCateCode}/edit")
	public String medicalEdit(
			@PathVariable String lmdMinorCateCode, ListMedicalDevicesDTO editMedical,
			@RequestParam String lmdStatus, @RequestParam String lmdDeviceCnt, 
			@RequestParam String lmdManagerDeptNo, @RequestParam String lmdManagerDeptPart, 
			@RequestParam String lmdManagerName, @RequestParam String lmdManagerId,
			@RequestParam String lmdAdminDeptNo, @RequestParam String lmdAdminId, @RequestParam String lmdAdminName, 
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date lmdLastCheckDate) {
		log.info("컨트롤러 실행 ====================================");
		
		log.info("lmdStatus : " + lmdStatus);
		log.info("lmdDeviceCnt : " + lmdDeviceCnt);
		log.info("lmdManagerDeptNo : " + lmdManagerDeptNo);
		log.info("lmdManagerDeptNo : " + lmdManagerDeptPart);
		log.info("lmdManagerName : " + lmdManagerName);
		log.info("lmdManagerId : " + lmdManagerId);
		log.info("lmdAdminDeptNo : " + lmdAdminDeptNo);
		log.info("lmdAdminId : " + lmdAdminId);
		log.info("lmdAdminName : " + lmdAdminName);
		log.info("lmdLastCheckDate : " + lmdLastCheckDate);
		
		// 해당 의료기기 정보 수정
		medicalService.editMedicalDevice(editMedical, lmdStatus, lmdDeviceCnt,  
				lmdManagerDeptNo, lmdManagerDeptPart, lmdManagerId, lmdManagerName, 
				lmdAdminDeptNo, lmdAdminId, lmdAdminName, lmdLastCheckDate);
		
		return "redirect:/admin/medical-list/" + lmdMinorCateCode + "/detail";
	}

}
