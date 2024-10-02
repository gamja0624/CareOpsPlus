package himedia.project.careops.config;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 작성자: 노태윤
// 세션의 사용자 타입에 따라 접근을 제한하는 인터셉터 
public class RoleBasedAccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String userType = (String) session.getAttribute("userType");  
        String requestURI = request.getRequestURI();


        if (userType == null) {
            response.sendRedirect("/");
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