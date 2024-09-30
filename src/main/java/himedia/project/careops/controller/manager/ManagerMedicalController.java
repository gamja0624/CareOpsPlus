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
import himedia.project.careops.dto.ListMedicalDevicesDTO;
import himedia.project.careops.dto.ManagerDepartmentDTO;
import himedia.project.careops.entity.Manager;
import himedia.project.careops.service.ManagerDepartmentService;
import himedia.project.careops.service.ManagerService;
import himedia.project.careops.service.MedicalService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/manager/medical-list")
public class ManagerMedicalController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final MedicalService medicalService;
	private final ManagerService managerService;
	private final ManagerDepartmentService managerDepartmentService;
	
	public ManagerMedicalController(MedicalService medicalService, ManagerService managerService, ManagerDepartmentService managerDepartmentService) {
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
	
	// [등록] ==========================================================================================
	@GetMapping("/getManagersByDeptName")
	@ResponseBody
	// 담당 부서 이름에 해당하는 담당자 목록을 가져오는 메서드
	public List<Manager> getManagersByDeptName(@RequestParam String managerDeptName) {
	    return managerService.findByManagerList(managerDeptName);
	}
	
	@GetMapping("/add")
	public String medicalAddPage(Model model) {
		
		// 담당 부서 목록 반환
		List<ManagerDepartmentDTO> departments = managerDepartmentService.findAllDepartmentsList();
		model.addAttribute("departments", departments);
		
		return "manager/medical/medical-add";
	}
	
	@PostMapping("/add")
	public String medicalAdd(ListMedicalDevicesDTO newMedicalDevice) {
		
		log.info("[1. 컨트롤러 실행]");
		medicalService.addMedicalDevice(newMedicalDevice);
		
		return "redirect:/manager/medical-list";
	}
	
}
