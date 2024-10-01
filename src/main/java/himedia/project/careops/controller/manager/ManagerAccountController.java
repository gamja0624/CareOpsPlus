package himedia.project.careops.controller.manager;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 최은지 
 * @editDate 2024-09-30
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import himedia.project.careops.dto.ManagerDTO;
import himedia.project.careops.entity.Manager;
import himedia.project.careops.service.ManagerService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("manager")
public class ManagerAccountController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final ManagerService managerService;
	
	public ManagerAccountController(ManagerService managerService) {
		this.managerService = managerService;
	}
	
	// 담당자 본인이 속한 부서의 담당자 목록 조회
	@GetMapping("/manager-list")
	public String managerList(Model model, HttpSession session) {
		log.info("우리 부서 담당자 조회 실행");
		return "manager/account/department-account-list";
	}

}
