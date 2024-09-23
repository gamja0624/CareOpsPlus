package himedia.project.careops.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class LoginController {

    // 사용자 인증을 위한 샘플 데이터
    private static final String ADMIN_ID = "admin";
    private static final String ADMIN_PASSWORD = "adminpass";
    private static final String MANAGER_ID = "manager";
    private static final String MANAGER_PASSWORD = "managerpass";

    // 로그인 페이지로 GET 요청 처리
    @GetMapping("/")
    public String showLoginPage(Model model) {
        model.addAttribute("error", "");
        return "common/login";  // login.html 페이지 반환
    }

    // 로그인 처리 POST 요청
    // 파란줄 :
    // 경로를 명시적으로 지정하는 방식으로 수정하는 것을 권장한다.
    //  Spring 이 메서드 이름을 사용하여 경로를 암시적으로 매핑하는 대신, 명시적으로 경로를 설정하는 것이 더 명확하다
    @PostMapping("/")
    public String login(@RequestParam("admin_id") String admin_id,
                        @RequestParam("admin_password") String admin_password, 
                        Model model, HttpSession session) {

        // 로그인 인증 여부 확인
        boolean isAuthenticated = authenticate(admin_id, admin_password);

        if (isAuthenticated) {
            // 세션에 사용자 ID를 저장
            session.setAttribute("admin_id", admin_id);

            if (admin_id.equals(ADMIN_ID)) {
                return "redirect:/admin/dash-board";  // 관리자 대시보드로 리다이렉트
            } else if (admin_id.equals(MANAGER_ID)) {
                return "redirect:/manager/managerdashboard";  // 매니저 대시보드로 리다이렉트
            } else {
                return "redirect:/";  // 홈(login.html)으로 리다이렉트
            }
        } else {
            // 로그인 실패 시 에러 메시지를 설정하여 다시 로그인 페이지로
            model.addAttribute("error", "로그인 실패: 잘못된 아이디, 비밀번호입니다.");
            return "common/login";  // 로그인 실패 시 다시 로그인 페이지로
        }
    }

    // 사용자 인증 로직
    private boolean authenticate(String admin_id, String admin_password) {
        if (admin_id.equals(ADMIN_ID) && admin_password.equals(ADMIN_PASSWORD)) {
            return true;
        } else if (admin_id.equals(MANAGER_ID) && admin_password.equals(MANAGER_PASSWORD)) {
            return true;
        } else {
            return false;
        }
    }
}
