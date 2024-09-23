package himedia.project.careops.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import himedia.project.careops.repository.LoginRepository;

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
        return "common/login";  // login.html ������ ���
    }

    @PostMapping("/")
    public String login(@RequestParam("admin_dept_no") String adminDeptNo,
                        @RequestParam("admin_id") String adminId,
                        @RequestParam("admin_password") String adminPassword,
                        Model model, HttpSession session) {

        boolean isAuthenticated = authenticate(adminDeptNo, adminId, adminPassword);

        if (isAuthenticated) {
            session.setAttribute("admin_id", adminId);
            return "redirect:/admin/dash-board";
        } else {
            model.addAttribute("error", "�α��� ����: �߸��� �μ� ��ȣ, ���̵� �Ǵ� ��й�ȣ�Դϴ�.");
            return "common/login";
        }
    }

    private boolean authenticate(String adminDeptNo, String adminId, String adminPassword) {
        return loginRepository.findByAdminDeptNoAndAdminIdAndAdminPassword(adminDeptNo, adminId, adminPassword).isPresent();
    }

}
