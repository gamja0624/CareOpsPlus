package himedia.project.careops.controller;


/*@author 노태윤
@editDate 2024-09-23~2024-09-24*/

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import himedia.project.careops.repository.LoginRepository;
import himedia.project.careops.dto.AdminDTO;
import himedia.project.careops.entity.Admin;
import himedia.project.careops.entity.Manager;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class LoginController {

    private final LoginRepository loginRepository;

    public LoginController(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @GetMapping("/")
    public String showLoginPage(Model model) {
        model.addAttribute("error", "");
        return "common/login"; // login.html 페이지로 이동
    }

    @PostMapping("/")
    public String login(@RequestParam("department_dept_no") String deptNo,
                        @RequestParam("user_id") String userId,
                        @RequestParam("user_password") String userPassword,
                        Model model, HttpSession session) {

        // Admin 테이블에서 조회
        Optional<Admin> admin = loginRepository.findByAdminDeptNoAndAdminIdAndAdminPassword(deptNo, userId, userPassword);
        if (admin.isPresent()) {
            session.setAttribute("admin_id", admin.get().getAdminId());
            return "redirect:/admin/dash-board";
        }

        // Manager 테이블에서 조회
        int managerDeptNo;
        try {
            managerDeptNo = Integer.parseInt(deptNo); // 문자열을 정수로 변환
        } catch (NumberFormatException e) {
            model.addAttribute("error", "부서 번호는 숫자여야 합니다.");
            return "common/login";
        }

        Optional<Manager> manager = loginRepository.findByManagerDeptNoAndManagerIdAndManagerPassword(managerDeptNo, userId, userPassword);
        if (manager.isPresent()) {
            session.setAttribute("manager_id", manager.get().getManagerId());
            return "redirect:/manager/dash-board";
        }

        model.addAttribute("error", "로그인 실패: 아이디 또는 비밀번호가 올바르지 않습니다.");
        return "common/login";
    }
}
