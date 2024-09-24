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
import org.springframework.web.bind.annotation.RequestMapping;

import himedia.project.careops.common.Pagenation;
import himedia.project.careops.common.PagingButtonInfo;
import himedia.project.careops.dto.ListMedicalDevicesDTO;
import himedia.project.careops.service.MedicalService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/manager/medical-list")
public class MedicalController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final MedicalService medicalService;
	
	public MedicalController(MedicalService medicalService) {
		this.medicalService = medicalService;
	}

	@GetMapping("")
	public String medicalList(@PageableDefault Pageable pageable, Model model) {
		
		Page<ListMedicalDevicesDTO> medicalDevicesList = medicalService.findByMedicalDevices(pageable);
		log.info("medicalDevicesList : {}", medicalDevicesList);
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(medicalDevicesList);
		
		model.addAttribute("medicalDevicesList", medicalDevicesList);
		model.addAttribute("paging", paging);
		
		return "manager/medical/medical-list";
	}
	
	@GetMapping("/detail")
	public String medicalDetail() {
		return "manager/medical/medical-detail";
	}
	
	@GetMapping("/edit")
	public String medicalEdit() {
		return "manager/medical/medical-edit";
	}
	
	@GetMapping("/add")
	public String medicalAdd() {
		return "manager/medical/medical-add";
	}
	
}
