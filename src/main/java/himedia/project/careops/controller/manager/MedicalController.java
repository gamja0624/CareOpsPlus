package himedia.project.careops.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 진혜정
 * @editDate 2024-09-19 ~ 
 */

@Controller
@RequestMapping("/manager/medical-list")
public class MedicalController {

	@GetMapping("")
	public String medicalList() {
		return "manager/medical/medical-list";
	}
	
	@GetMapping("/detail")
	public String medicalDetail() {
		return "manager/medical/medical-detail";
	}
	
}
