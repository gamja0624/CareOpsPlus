package himedia.project.careops.controller;

/**
 * @author 노태윤 
 * @editDate 2024-09-26
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import himedia.project.careops.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("")
    public String showLoginPage() {
        return "common/login"; // 로그인 페이지 반환
    }
    
    @PostMapping("")
    public String login(@RequestParam("department_dept_no") String deptNo,
                       @RequestParam("user_id") String userId,
                       @RequestParam("user_password") String userPassword,
                       Model model, HttpSession session) {

        logger.info("로그인 시도: deptNo={}, userId={}", deptNo, userId);

        // 로그인 처리 메서드 호출
        String loginResult = loginService.login(deptNo, userId, userPassword);

        if (loginResult.equals("admin")) {
            logger.info("관리자 로그인 성공: {}", userId);
            session.setAttribute("user_type", "admin"); // 사용자 타입 설정
            session.setAttribute("user_id", userId); // 사용자 ID 설정
            return "redirect:/admin/dash-board"; // 관리자 대시보드로 리다이렉트
        } else if (loginResult.equals("manager")) {
            logger.info("매니저 로그인 성공: {}", userId);
            session.setAttribute("user_type", "manager"); // 사용자 타입 설정
            session.setAttribute("user_id", userId); // 사용자 ID 설정
            return "redirect:/manager/dash-board"; // 매니저 대시보드로 리다이렉트
        } else {
            logger.warn("로그인 실패: deptNo={}, userId={}", deptNo, userId);
            model.addAttribute("error", loginResult); // 에러 메시지 추가
            return "common/login"; // 로그인 페이지로 돌아감
        }
    }
}
