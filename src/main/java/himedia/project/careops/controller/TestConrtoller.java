package himedia.project.careops.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import himedia.project.careops.dto.AdminDTO;
import himedia.project.careops.service.AdminDepartmentService;
import himedia.project.careops.service.AdminService;
import himedia.project.careops.service.ManagerDepartmentService;
import himedia.project.careops.service.ManagerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/")
public class TestConrtoller {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final AdminDepartmentService adminDepartmentService;
	private final AdminService adminService;
	private final ManagerDepartmentService managerDepartmentService;
	private final ManagerService mangerService;
	
	public TestConrtoller(
			AdminDepartmentService adminDepartmentService, AdminService adminService,
			ManagerDepartmentService managerDepartmentService, ManagerService mangerService) {
		this.adminDepartmentService = adminDepartmentService;
		this.adminService = adminService;
		this.managerDepartmentService = managerDepartmentService;
		this.mangerService = mangerService;
	}
	
	@GetMapping("/{admin}")
	public String loginPage(@PathVariable String admin) {
		
		log.info("받아온 파리미터 : {}", admin);
		AdminDTO findAdmin = adminService.findByAdminId(admin);
		
		log.info("끝났어여");
		log.info("findAdmin : {}", findAdmin);
		
		return "common/login";
	}
}
