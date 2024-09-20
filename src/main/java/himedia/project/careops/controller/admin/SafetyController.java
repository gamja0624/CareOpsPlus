package himedia.project.careops.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 이홍준
 * @editDate 2024-09-20 ~ 
 */

@Controller
@RequestMapping("/admin")
public class SafetyController{
	
	@GetMapping("/safety-list")
	public String smList() {
		return "admin/safety/safety-list";
	}
	
	@GetMapping("/checklist-edit")
	public String checkListEdit() {
		return "admin/safety/checklist-edit";
	}
	
//	@GetMapping("/checklist-edit/{ 세부항목번호 }")
	@GetMapping("/checklist-edit/detail")
	public String checklistEditDetail() {
		return "admin/safety/checklist-edit-detail";
	}
}
