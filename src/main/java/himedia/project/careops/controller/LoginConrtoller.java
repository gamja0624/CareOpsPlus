package himedia.project.careops.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import himedia.project.careops.dto.AdminDepartmentDTO;
import himedia.project.careops.service.AdminDepartmentService;
import himedia.project.careops.service.AdminService;
import himedia.project.careops.service.ManagerDepartmentService;
import himedia.project.careops.service.ManagerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/")
public class LoginConrtoller {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final AdminDepartmentService adminDepartmentService;
	private final AdminService adminService;
	private final ManagerDepartmentService managerDepartmentService;
	private final ManagerService mangerService;
	
	public LoginConrtoller(
			AdminDepartmentService adminDepartmentService, AdminService adminService,
			ManagerDepartmentService managerDepartmentService, ManagerService mangerService) {
		this.adminDepartmentService = adminDepartmentService;
		this.adminService = adminService;
		this.managerDepartmentService = managerDepartmentService;
		this.mangerService = mangerService;
	}
	
	@GetMapping("")
	public String login() {
		return "common/login";
	}
	
	@GetMapping("/{deptNo}")
	public String loginPage(@PathVariable String deptNo) {
		
		log.info("deptNo : {}", deptNo);
		AdminDepartmentDTO adminDept = adminDepartmentService.findByAdminDeptNo(deptNo);
		
		log.info("실행 중");
		
		if (adminDept != null) {
			log.info("adminDept : {}", adminDept);
		} else {
			log.info("없다 !");
		}
		
		return "common/login";
	}
}
