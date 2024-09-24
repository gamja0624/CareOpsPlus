package himedia.project.careops.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping("/safety-checklist-edit")
	public String checkListEdit() {
		return "admin/safety/checklist-edit";
	}
	
//	@GetMapping("/checklist-edit/{sml_no}")
	@GetMapping("/checklist-edit/detail")
	public String checklistEditDetail(@PathVariable int sml_no, Model model) {
		
		// findBySmlNo()
		return "admin/safety/checklist-edit-detail";
	}
	
	@GetMapping("/safety-daily-registration")
	public String dailyResistraion() {
		return "admin/safety/daily-registration";
	}
	
	@PostMapping("/safety-daily-registration")
	public String resistrationComple() {
		return "redirect:/admin/safety/daily-registration";
	}
	
//	@GetMapping("/safety-daily-registration/{sml_no}")
	@GetMapping("/safety-daily-registration/detail")
	public String dailyResistraionDetail() {
		return "admin/safety/daily-registration-detail";
	}
	
	
}
