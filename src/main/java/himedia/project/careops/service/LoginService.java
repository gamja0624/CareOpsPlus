package himedia.project.careops.service;

// @author 노태윤 
// @editDate 2024-09-26~2024-10-07

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import himedia.project.careops.entity.Admin;
import himedia.project.careops.entity.AdminDepartment;
import himedia.project.careops.entity.Manager;
import himedia.project.careops.entity.ManagerDepartment;
import himedia.project.careops.repository.AdminDepartmentRepository;
import himedia.project.careops.repository.AdminRepository;
import himedia.project.careops.repository.ManagerDepartmentRepository;
import himedia.project.careops.repository.ManagerRepository;

@Service 
public class LoginService {

    private final AdminRepository adminRepository;
    private final ManagerRepository managerRepository;
    private final ManagerDepartmentRepository managerDepartmentRepository;
    private final AdminDepartmentRepository adminDepartmentRepository;
    private final ModelMapper modelMapper;

    public LoginService(AdminRepository adminRepository, ManagerRepository managerRepository,
                        ManagerDepartmentRepository managerDepartmentRepository, 
                        AdminDepartmentRepository adminDepartmentRepository, 
                        ModelMapper modelMapper) {
        this.adminRepository = adminRepository;
        this.managerRepository = managerRepository;
        this.managerDepartmentRepository = managerDepartmentRepository;
        this.adminDepartmentRepository = adminDepartmentRepository; 
        this.modelMapper = modelMapper;
    }
    
    public Map<String, Object> login(String deptNo, String userId, String userPassword) {
        Map<String, Object> result = new HashMap<>();

        // 관리자 부서 번호 형식 체크
        if (isAdminDepartmentFormat(deptNo)) {
            if (tryAdminLogin(deptNo, userId, userPassword, result)) {
                return result;
            }
        }

        // 매니저 부서 번호 형식 체크
        if (isManagerDepartmentFormat(deptNo)) {
            if (tryManagerLogin(deptNo, userId, userPassword, result)) {
                return result;
            }
        }

        // 로그인 실패 시 기본적으로 실패 메시지와 상태를 result에 담아서 반환.
        result.put("success", false);
        return result;
    }

    // 관리자 부서 번호 체크 로직
    private boolean isAdminDepartmentFormat(String deptNo) {
        // 관리자의 부서 번호가 문자열 형식인 경우 체크하는 로직
        return deptNo != null && !deptNo.matches("\\d+");  // 숫자가 아닌 경우 관리자로 처리
    }

    // 매니저 부서 번호 체크 로직
    private boolean isManagerDepartmentFormat(String deptNo) {
        // 매니저의 부서 번호가 숫자인지 체크
        try {
            Integer.parseInt(deptNo);  // 부서 번호가 숫자일 경우 매니저로 처리			
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean tryAdminLogin(String deptNo, String userId, String userPassword, Map<String, Object> result) {
        Optional<AdminDepartment> adminDeptOpt = adminDepartmentRepository.findByAdminDeptNo(deptNo);
        
        if (!adminDeptOpt.isPresent()) {
            result.put("success", false);
            result.put("message", "아이디 또는 비밀번호가 일치하지 않습니다."); // 부서 번호 오류 시 메시지
            return false;
        }

        Optional<Admin> adminOpt = adminRepository.findByAdminDeptNoAndAdminIdAndAdminPassword(deptNo, userId, userPassword);
        if (!adminOpt.isPresent()) {
            result.put("success", false);
            result.put("message", "아이디 또는 비밀번호가 일치하지 않습니다."); // 아이디 또는 비밀번호 오류 시 통일된 메시지
            return false;
        }

        Admin admin = adminOpt.get();   
        setLoginResult(result, "admin", admin.getAdminName(), adminDeptOpt.get().getAdminDeptName(), deptNo);
        return true;
    }

    private boolean tryManagerLogin(String deptNo, String userId, String userPassword, Map<String, Object> result) {
        try {
            int managerDeptNo = Integer.parseInt(deptNo);
            Optional<ManagerDepartment> departmentOpt = managerDepartmentRepository.findByManagerDeptNo(managerDeptNo);
            
            if (!departmentOpt.isPresent()) {
                result.put("success", false);
                result.put("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
                return false;
            }

            Optional<Manager> managerOpt = managerRepository.findByManagerDeptNoAndManagerIdAndManagerPassword(managerDeptNo, userId, userPassword);
            if (!managerOpt.isPresent()) {
                result.put("success", false);
                result.put("message", "아이디 또는 비밀번호가 일치하지 않습니다."); // 아이디 또는 비밀번호 오류 시 통일된 메시지
                return false;
            }

            Manager manager = managerOpt.get();
            
            setLoginResult(result, "manager", manager.getManagerName(), departmentOpt.get().getManagerDeptName(), deptNo);
            return true;
        } catch (NumberFormatException e) {
            result.put("success", false);
            result.put("message", "부서 번호는 숫자여야 합니다.");
            return false;
        }
    }

    private void setLoginResult(Map<String, Object> result, String userType, String userName, String departmentName, String deptNo) {
        result.put("success", true);
        result.put("userType", userType);
        result.put("deptNo", deptNo);
        result.put("departmentName", departmentName);
        result.put("userName", userName);
        result.put("redirectUrl", "/" + userType + "/dash-board");
    }
}
