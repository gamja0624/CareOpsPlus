package himedia.project.careops.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/safety")
public class SafetyManagementController{
	
	@GetMapping("/safety-list")
	public String smList() {
		return "admin/safety/safety-list";
	}
	
	@GetMapping("checklist-edit")
	public String checkListEdit() {
		return "admin/safety/checklist-edit";
	}
	
}
