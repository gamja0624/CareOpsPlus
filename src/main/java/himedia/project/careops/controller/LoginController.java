package himedia.project.careops.controller;

import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import himedia.project.careops.entity.Admin;
import himedia.project.careops.entity.Manager;
import himedia.project.careops.repository.AdminLoginRepository;
import himedia.project.careops.repository.ManagerLoginRepository;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final AdminLoginRepository adminLoginRepository;
    private final ManagerLoginRepository managerLoginRepository;

    public LoginController(AdminLoginRepository adminLoginRepository, ManagerLoginRepository managerLoginRepository) {
        this.adminLoginRepository = adminLoginRepository;
        this.managerLoginRepository = managerLoginRepository;
    }

    @PostMapping("/")
    public String login(@RequestParam("department_dept_no") String deptNo,
                        @RequestParam("user_id") String userId,
                        @RequestParam("user_password") String userPassword,
                        Model model, HttpSession session) {

        logger.info("로그인 시도: deptNo={}, userId={}", deptNo, userId);

        // Admin 테이블에서 조회
        Optional<Admin> admin = adminLoginRepository.findByAdminDeptNoAndAdminIdAndAdminPassword(deptNo, userId, userPassword);
        if (admin.isPresent()) {
            logger.info("관리자 로그인 성공: {}", userId);
            session.setAttribute("admin_id", admin.get().getAdminId());
            session.setAttribute("admin_name", admin.get().getAdminName());
            return "redirect:/admin/dash-board";
        }

        // Manager 테이블에서 조회
        try {
            int managerDeptNo = Integer.parseInt(deptNo);
            Optional<Manager> manager = managerLoginRepository.findByManagerDeptNoAndManagerIdAndManagerPassword(managerDeptNo, userId, userPassword);
            if (manager.isPresent()) {
                logger.info("매니저 로그인 성공: {}", userId);
                session.setAttribute("manager_id", manager.get().getManagerId());
                session.setAttribute("manager_name", manager.get().getManagerName());
                return "redirect:/manager/dash-board";
            }
        } catch (NumberFormatException e) {
            logger.warn("부서 번호 변환 실패: {}", deptNo);
        }

        logger.warn("로그인 실패: deptNo={}, userId={}", deptNo, userId);
        model.addAttribute("error", "로그인 실패: 아이디 또는 비밀번호가 올바르지 않습니다.");
        return "common/login";
    }
}
