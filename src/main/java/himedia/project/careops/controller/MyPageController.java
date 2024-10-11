package himedia.project.careops.controller;

import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import himedia.project.careops.dto.AdminDTO;
import himedia.project.careops.dto.ManagerDTO;
import himedia.project.careops.service.MyPageService;
import jakarta.servlet.http.HttpSession;

@Controller
public class MyPageController {

    private final MyPageService myPageService;

    @Autowired
    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    @GetMapping("/{userType}/mypage")
    public String getMyInfo(@PathVariable("userType") String userType, Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");

        if ("admin".equals(userType)) {
            myPageService.getMyInfoByAdminId(userId).ifPresentOrElse(
                admin -> model.addAttribute("user", admin),
                () -> model.addAttribute("error", "관리자를 찾을 수 없습니다.")
            );
        } else if ("manager".equals(userType)) {
            myPageService.getMyInfoByManagerId(userId).ifPresentOrElse(
                manager -> model.addAttribute("user", manager),
                () -> model.addAttribute("error", "매니저를 찾을 수 없습니다.")
            );
        }

        model.addAttribute("userType", userType);
        
        String passwordChangeMessage = (String) session.getAttribute("passwordChangeMessage");
        if (passwordChangeMessage != null) {
            model.addAttribute("message", passwordChangeMessage);
            session.removeAttribute("passwordChangeMessage");
        }

        String passwordChangeError = (String) session.getAttribute("passwordChangeError");
        if (passwordChangeError != null) {
            model.addAttribute("error", passwordChangeError);
            session.removeAttribute("passwordChangeError");
        }

        return "common/mypage/mypage";
    }

    @GetMapping("/{userType}/mypage-edit")
    public String showEditForm(@PathVariable("userType") String userType, Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");

        if ("admin".equals(userType)) {
            Optional<AdminDTO> admin = myPageService.getMyInfoByAdminId(userId);
            admin.ifPresentOrElse(
                a -> {
                    model.addAttribute("user", a);
                    model.addAttribute("userType", "admin");
                },
                () -> model.addAttribute("error", "관리자를 찾을 수 없습니다.")
            );
        } else if ("manager".equals(userType)) {
            Optional<ManagerDTO> manager = myPageService.getMyInfoByManagerId(userId);
            manager.ifPresentOrElse(
                m -> {
                    model.addAttribute("user", m);
                    model.addAttribute("userType", "manager");
                },
                () -> model.addAttribute("error", "매니저를 찾을 수 없습니다.")
            );
        }

        return "common/mypage/mypage-edit";
    }

    @PostMapping("/{userType}/update-info")
    public String updateUserInfo(@PathVariable("userType") String userType,
                                 @RequestParam("id") String id,
                                 @RequestParam("name") String name,
                                 @RequestParam("phoneNumber") String phoneNumber,
                                 RedirectAttributes redirectAttributes) {
        try {
            boolean updated = false;
            if ("admin".equals(userType)) {
                AdminDTO adminDTO = new AdminDTO();
                adminDTO.setAdminId(id);
                adminDTO.setAdminName(name);
                adminDTO.setAdminPhoneNumber(phoneNumber);
                updated = myPageService.updateAdminInfo(adminDTO);
            } else if ("manager".equals(userType)) {
                ManagerDTO managerDTO = new ManagerDTO();
                managerDTO.setManagerId(id);
                managerDTO.setManagerName(name);
                managerDTO.setManagerPhoneNumber(phoneNumber);
                updated = myPageService.updateManagerInfo(managerDTO);
            }
            if (updated) {
                redirectAttributes.addFlashAttribute("message", "회원 정보가 성공적으로 수정되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("error", "회원 정보 수정에 실패했습니다.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "회원 정보 수정 중 오류가 발생했습니다.");
        }

        return "redirect:/" + userType + "/mypage";
    }
    
    // 비밀번호 변경 폼 표시
    @GetMapping("/{userType}/mypage-change-pw")
    public String showChangePasswordForm(@PathVariable("userType") String userType, Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            // 세션 정보가 없는 경우, 로그인 페이지로 리다이렉트
            return "redirect:/login"; // 적절한 로그인 페이지 경로로 변경
        }
        
        // 사용자의 비밀번호 조회
        String currentPassword;
        if ("admin".equals(userType)) {
            currentPassword = myPageService.getAdminPassword(userId);
        } else if ("manager".equals(userType)) {
            currentPassword = myPageService.getManagerPassword(userId);
        } else {
            // 유효하지 않은 userType인 경우
            throw new IllegalArgumentException("유효하지 않은 사용자 유형입니다: " + userType);
        }

        // 비밀번호 검증
        if (currentPassword == null || !myPageService.isValidPassword(currentPassword)) {
            // 비밀번호가 유효하지 않은 경우 처리 (예: 에러 메시지 추가, 리다이렉트 등)
            model.addAttribute("error", "현재 비밀번호가 유효하지 않습니다.");
            return "common/mypage/mypage-change-pw"; // 에러 메시지를 보여주기 위해 동일한 폼 반환
        }

        // 비밀번호를 모델에 추가
        model.addAttribute("userId", userId);
        model.addAttribute("userType", userType);
        model.addAttribute("currentPassword", currentPassword);

        return "common/mypage/mypage-change-pw"; // 변경할 비밀번호 폼의 뷰 이름
    }

    // 비밀번호 변경 요청 처리
    @PostMapping("/{userType}/mypage-change-pw")
    @ResponseBody
    public ResponseEntity<?> updatePassword(@PathVariable("userType") String userType, @RequestBody Map<String, String> passwordData, HttpSession session) {
        String currentPassword = passwordData.get("currentPassword");
        String newPassword = passwordData.get("newPassword");
        String confirmPassword = passwordData.get("confirmPassword");
        String userId = (String) session.getAttribute("userId");
        
        if (!("admin".equals(userType) || "manager".equals(userType))) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "잘못된 사용자 유형입니다."));
        }

        if (userId == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "유효하지 않은 세션 정보입니다."));
        }

        if (!myPageService.isCurrentPasswordCorrect(userId, currentPassword, userType)) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "현재 비밀번호가 올바르지 않습니다."));
        }

        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "새 비밀번호와 확인 비밀번호가 일치하지 않습니다."));
        }

        if (currentPassword.equals(newPassword)) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "새 비밀번호는 현재 비밀번호와 달라야 합니다. 다시 입력해주세요."));
        }

        if (!myPageService.isValidPassword(newPassword)) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "비밀번호는 10-16자 사이여야 하며, 대문자, 소문자 또는 숫자를 포함해야 합니다."));
        }

        try {
            boolean success = myPageService.changePassword(userId, currentPassword, newPassword, userType);
            if (success) {
                return ResponseEntity.ok(Map.of("success", true, "message", "비밀번호가 성공적으로 변경되었습니다."));
            } else {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "비밀번호 변경에 실패했습니다."));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("success", false, "message", "Server error occurred: " + e.getMessage()));
        }
    }
}
