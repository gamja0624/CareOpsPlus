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

import himedia.project.careops.common.Pagenation;
import himedia.project.careops.common.PagingButtonInfo;
import himedia.project.careops.dto.ListMedicalDevicesDTO;
import himedia.project.careops.dto.ManagerDTO;
import himedia.project.careops.dto.ManagerDepartmentDTO;
import himedia.project.careops.entity.Manager;
import himedia.project.careops.service.ManagerDepartmentService;
import himedia.project.careops.service.ManagerService;
import himedia.project.careops.service.MedicalService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/manager/medical-list")
public class MedicalController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final MedicalService medicalService;
	private final ManagerService managerService;
	private final ManagerDepartmentService managerDepartmentService;
	
	public MedicalController(MedicalService medicalService, ManagerService managerService, ManagerDepartmentService managerDepartmentService) {
		this.medicalService = medicalService;
		this.managerService = managerService;
		this.managerDepartmentService = managerDepartmentService;
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
	@GetMapping("/{lmdMinorCateCode}/edit")
	public String medicalEditPage(@PathVariable String lmdMinorCateCode, Model model) {
		
		// 해당 의료기기 정보 반환
		ListMedicalDevicesDTO medicalDevice = medicalService.findByMedicalLmdMinorCateCode(lmdMinorCateCode);
		model.addAttribute("medicalDevice", medicalDevice);
		
		// 담당 부서의 매니저 정보 반환
		String managerDeptPart = medicalDevice.getLmdManagerDeptPart();
		List<Manager> managerinfo = managerService.findByManagerList(managerDeptPart);
		
		model.addAttribute("managerinfo", managerinfo);
		
		return "manager/medical/medical-edit";
	}

	@PostMapping("/{lmdMinorCateCode}/edit")
	public String medicalEdit(
			@PathVariable String lmdMinorCateCode, ListMedicalDevicesDTO editMedical,
			@RequestParam String lmdStatus, @RequestParam String lmdDeviceCnt, @RequestParam String lmdManagerName) {
		
		// 해당 의료기기 정보 반환
		ListMedicalDevicesDTO medicalDevice = medicalService.findByMedicalLmdMinorCateCode(lmdMinorCateCode);
		
		// 담당 부서의 매니저 정보 반환
		String managerDeptPart = medicalDevice.getLmdManagerDeptPart();
		List<Manager> managerList = managerService.findByManagerList(managerDeptPart);
		
		// lmdManagerId 변수에 저장
		Manager managerinfo = managerList.stream()
				.filter(m -> m.getManagerName().equals(lmdManagerName))
				.findFirst()
				.get();
		
		String lmdManagerId = managerinfo.getManagerId();
		
		// 해당 의료기기 정보 수정
		medicalService.editMedicalDevice(editMedical, lmdStatus, lmdDeviceCnt, lmdManagerName, lmdManagerId);
		
		return "redirect:/manager/medical-list";
	}
	
	// [등록] ==========================================================================================
	@GetMapping("/add")
	public String medicalAddPage(Model model) {
		
		log.info("[GET] 등록 페이지");
		
		// 담당 부서/ 담당자 반환
		List<ManagerDepartmentDTO> departments = managerDepartmentService.findAllDepartmentsList();
		List<ManagerDTO> managerList = managerService.findallManagerList();
		model.addAttribute("departments", departments);
		model.addAttribute("managerList", managerList);
		
		return "manager/medical/medical-add";
	}
	
	@PostMapping("/add")
	public String medicalAdd(ListMedicalDevicesDTO newMedicalDevice) {
		
		
		return "redirect:/manager/medical-list";
	}
	
}
