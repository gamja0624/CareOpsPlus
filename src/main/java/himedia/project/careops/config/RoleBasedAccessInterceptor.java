package himedia.project.careops.config;

/**
 * @author 노태윤
 * @editDate 2024-09-26
 * 세션의 사용자 타입에 따라 접근을 제한하는 인터셉터 
 * ex) admin -> manager url 접근 불가능
 * ex) manager -> admin url 접근 불가능
 */

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class RoleBasedAccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String userType = (String) session.getAttribute("user_type");
        String requestURI = request.getRequestURI();

        if (userType == null) {
            response.sendRedirect("/"); // 로그인 페이지로 리다이렉트
            return false;
        }

        if (userType.equals("admin") && requestURI.startsWith("/manager/")) {
            response.sendRedirect("/admin/dash-board");
            return false;
        }

        if (userType.equals("manager") && requestURI.startsWith("/admin/")) {
            response.sendRedirect("/manager/dash-board");
            return false;
        }

        return true;
    }
}