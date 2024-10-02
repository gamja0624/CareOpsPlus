package himedia.project.careops.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoleBasedAccessInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RoleBasedAccessInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String userType = (String) session.getAttribute("userType");  
        String requestURI = request.getRequestURI();

        log.info("Interceptor - User type: {}, Request URI: {}", userType, requestURI);

        if (userType == null) {
            log.warn("User type is null, redirecting to login page");
            response.sendRedirect("/");
            return false;
        }

        if (userType.equals("admin") && requestURI.startsWith("/manager/")) {
            log.warn("Admin attempting to access manager page, redirecting to admin dashboard");
            response.sendRedirect("/admin/dash-board");
            return false;
        }

        if (userType.equals("manager") && requestURI.startsWith("/admin/")) {
            log.warn("Manager attempting to access admin page, redirecting to manager dashboard");
            response.sendRedirect("/manager/dash-board");
            return false;
        }

        log.info("Access granted for user type: {} to URI: {}", userType, requestURI);
        return true;
    }
}