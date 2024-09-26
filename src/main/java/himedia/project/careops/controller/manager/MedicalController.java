package himedia.project.careops.controller.manager;

/**
 * @author 진혜정
 * @editDate 2024-09-19 ~ 
 */

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
	
	public MedicalController(MedicalService medicalService, ManagerService managerService) {
		this.medicalService = medicalService;
		this.managerService = managerService;
	}

	// [목록 조회] ======================================================================================
	@GetMapping("")
	public String medicalList(@PageableDefault Pageable pageable, Model model) {
		
		Page<ListMedicalDevicesDTO> medicalDevicesList = medicalService.findByMedicalDevices(pageable);
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(medicalDevicesList);
		
		model.addAttribute("medicalDevicesList", medicalDevicesList);
		model.addAttribute("paging", paging);
		
//		log.info("목록 의료기기 상태 : {}", medicalDevicesList.toList().get(0).getLmdStatus());
		
//		log.info("컨트롤러 : 1번째 : {}", medicalDevicesList.get().toList().toString());
//		log.info("컨트롤러 : 2번째 : {}", paging);
		
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
		
		ListMedicalDevicesDTO medicalDevice = medicalService.findByMedicalLmdMinorCateCode(lmdMinorCateCode);
		model.addAttribute("medicalDevice", medicalDevice);
		
		log.info("1번) 매니저 부서 번호 받기");
		log.info("1번) medicalDevice : {}", medicalDevice.getLmdManagerDeptPart());
		
		log.info("2번) 매니저 부서 번호로 해당하는 담당자 리스트 가져오기");
		managerService.findByManagerName(medicalDevice.getLmdManagerDeptPart());
		
		
		return "manager/medical/medical-edit";
	}
	
	@PostMapping("/{lmdMinorCateCode}/edit")
	public String medicalEdit(
			@PathVariable String lmdMinorCateCode, ListMedicalDevicesDTO editMedical,
			@RequestParam String lmdStatus, @RequestParam String lmdDeviceCnt, @RequestParam String lmdManagerName) {
		
		log.info("수정 컨트롤러 실행");
		
		log.info("받아온 lmdStatus          : {}", lmdStatus);
		log.info("받아온 lmdDeviceCnt       : {}", lmdDeviceCnt);
		log.info("받아온 lmdManagerName     : {}", lmdManagerName);
		
		medicalService.editMedicalDevice(editMedical, lmdStatus, lmdDeviceCnt, lmdManagerName);
		
		return "redirect:/manager/medical-list";
	}
	
	// [등록] ==========================================================================================
	@GetMapping("/add")
	public String medicalAddPage() {
		
		return "manager/medical/medical-add";
	}
	
	@PostMapping("/add")
	public String medicalAdd(ListMedicalDevicesDTO newMedicalDevice) {
		
		//medicalService.addMedicalDevice(newMedicalDevice);
		
		return "redirect:/manager/medical-list";
	}
	
}
