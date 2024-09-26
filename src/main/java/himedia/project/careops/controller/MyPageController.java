package himedia.project.careops.controller;

/**
 * @author 노태윤 
 * @editDate 2024-09-26
 */

import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import himedia.project.careops.dto.AdminDTO;
import himedia.project.careops.dto.ManagerDTO; 
import himedia.project.careops.service.MyPageService;
import jakarta.servlet.http.HttpSession;

@Controller
public class MyPageController {

    @Autowired
    private final MyPageService myPageService;

    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    @GetMapping("/mypage2")
    public String getMyInfo(Model model, HttpSession session) {
        String userType = (String) session.getAttribute("user_type");
        String userId = (String) session.getAttribute("user_id");

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
        
        // userType을 모델에 추가합니다.
        model.addAttribute("userType", userType);
        
        // 비밀번호 변경 메시지 처리
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

        return "common/mypage/mypage2";
    }
    
    @GetMapping("/mypage-edit2")
    public String showEditForm(Model model, HttpSession session) {
        String userType = (String) session.getAttribute("user_type");
        String userId = (String) session.getAttribute("user_id");

        if ("admin".equals(userType)) {
            Optional<AdminDTO> admin = myPageService.getMyInfoByAdminId(userId);
            if (admin.isPresent()) {
                model.addAttribute("user", admin.get());
                model.addAttribute("userType", "admin");
            } else {
                model.addAttribute("error", "관리자를 찾을 수 없습니다.");
            }
        } else if ("manager".equals(userType)) {
            Optional<ManagerDTO> manager = myPageService.getMyInfoByManagerId(userId);
            if (manager.isPresent()) {
                model.addAttribute("user", manager.get());
                model.addAttribute("userType", "manager");
            } else {
                model.addAttribute("error", "매니저를 찾을 수 없습니다.");
            }
        }

        return "common/mypage/mypage-edit2";
    }

    @PostMapping("/update-info")
    public String updateUserInfo(@RequestParam("id") String id,
                                 @RequestParam("name") String name,
                                 @RequestParam("phoneNumber") String phoneNumber,
                                 @RequestParam("userType") String userType,
                                 RedirectAttributes redirectAttributes) {
        try {
            boolean updated = false;
            if ("admin".equals(userType)) {
                AdminDTO adminDTO = new AdminDTO();
                adminDTO.setAdminId(id);
                adminDTO.setAdminName(name);
                adminDTO.setAdminPhoneNumber(phoneNumber);
                updated = myPageService.updateAdminInfo(adminDTO);
                System.out.println("Admin update result: " + updated + ", ID: " + id + ", Name: " + name + ", Phone: " + phoneNumber);
            } else if ("manager".equals(userType)) {
                ManagerDTO managerDTO = new ManagerDTO();
                managerDTO.setManagerId(id);
                managerDTO.setManagerName(name);
                managerDTO.setManagerPhoneNumber(phoneNumber);
                updated = myPageService.updateManagerInfo(managerDTO);
                System.out.println("Manager update result: " + updated + ", ID: " + id + ", Name: " + name + ", Phone: " + phoneNumber);
            }

            if (updated) {
                redirectAttributes.addFlashAttribute("message", "회원 정보가 성공적으로 수정되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("error", "회원 정보 수정에 실패했습니다.");
            }
        } catch (Exception e) {
            System.err.println("Error updating user info: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "회원 정보 수정 중 오류가 발생했습니다.");
        }

        return "redirect:/mypage2";
    }
    
    @PostMapping("/mypage-change-pw2")
    @ResponseBody
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> passwordData, HttpSession session) {
        System.out.println("updatePassword 메서드 호출됨");
        String newPassword = passwordData.get("newPassword");
        String confirmPassword = passwordData.get("confirmPassword");
        System.out.println("newPassword: " + newPassword);
        System.out.println("confirmPassword: " + confirmPassword);

        String userId = (String) session.getAttribute("user_id");
        String userType = (String) session.getAttribute("user_type");

        if (userId == null || userType == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "세션 정보가 유효하지 않습니다."));
        }

        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "새 비밀번호와 확인 비밀번호가 일치하지 않습니다."));
        }

        try {
            boolean success = myPageService.changePassword2(userId, newPassword, userType);
            if (success) {
                return ResponseEntity.ok(Map.of("success", true, "message", "비밀번호가 성공적으로 변경되었습니다."));
            } else {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "비밀번호 변경에 실패했습니다."));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("success", false, "message", "서버 오류: " + e.getMessage()));
        }
    }
    
    @GetMapping("/mypage-change-pw2")
    public String showChangePasswordForm(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("user_id");
        String userType = (String) session.getAttribute("user_type");

        if ("admin".equals(userType)) {
            // 관리자 비밀번호 가져오기
            String currentPassword = myPageService.getAdminPassword(userId);
            model.addAttribute("currentPassword", currentPassword);
        } else if ("manager".equals(userType)) {
            // 매니저 비밀번호 가져오기
            String currentPassword = myPageService.getManagerPassword(userId);
            model.addAttribute("currentPassword", currentPassword);
        }

        // userType을 모델에 추가
        model.addAttribute("userType", userType);

        return "common/mypage/mypage-change-pw2"; // 비밀번호 변경 페이지로 이동
    }
}