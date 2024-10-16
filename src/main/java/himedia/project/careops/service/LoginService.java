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

    private final AdminRepository adminRepository;  // 관리자 정보를 처리하는 리포지토리
    private final ManagerRepository managerRepository;  // 매니저 정보를 처리하는 리포지토리
    private final ManagerDepartmentRepository managerDepartmentRepository;  // 매니저 부서 정보를 처리하는 리포지토리
    private final AdminDepartmentRepository adminDepartmentRepository;  // 관리자 부서 정보를 처리하는 리포지토리
    private final ModelMapper modelMapper;  // 엔티티와 DTO 간의 변환을 돕는 ModelMapper 객체

    // LoginService 생성자, 의존성을 주입받아 초기화
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
    
    // 로그인 처리 로직
    // 사용자 ID(userId), 비밀번호(userPassword) 및 부서 번호(deptNo)를 기준으로 관리자인지 매니저인지 구분하고,
    // 각 로그인 로직을 수행하여 결과를 반환. 결과는 Map 객체로 반환되며, 성공 시 'success' 값을 true로 설정.
    public Map<String, Object> login(String deptNo, String userId, String userPassword) {
        Map<String, Object> result = new HashMap<>();  // 로그인 결과를 담을 Map 객체

        // 부서 번호가 관리자 형식인지 체크 (문자열 형식일 경우 관리자)
        if (isAdminDepartmentFormat(deptNo)) {
            if (tryAdminLogin(deptNo, userId, userPassword, result)) {
                return result;  // 관리자 로그인 성공 시 결과 반환
            }
        }

        // 부서 번호가 매니저 형식인지 체크 (숫자 형식일 경우 매니저)
        if (isManagerDepartmentFormat(deptNo)) {
            if (tryManagerLogin(deptNo, userId, userPassword, result)) {
                return result;  // 매니저 로그인 성공 시 결과 반환
            }
        }

        // 로그인 실패 시 실패 상태와 메시지를 반환
        result.put("success", false);
        result.put("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
        return result;  // 최종적으로 실패 상태 반환
    }

    // 관리자 부서 번호 형식을 체크하는 메서드
    // 문자열 형식(숫자가 아닌 경우)이면 관리자 부서 번호로 간주
    private boolean isAdminDepartmentFormat(String deptNo) {
        return deptNo != null && !deptNo.matches("\\d+");  // 정규식을 사용해 숫자 여부 확인
    }

    // 매니저 부서 번호 형식을 체크하는 메서드
    // 숫자 형식이면 매니저 부서 번호로 간주
    private boolean isManagerDepartmentFormat(String deptNo) {
        try {
            Integer.parseInt(deptNo);  // 숫자 형식으로 변환 시도
            return true;  // 성공 시 매니저 부서 번호로 처리
        } catch (NumberFormatException e) {
            return false;  // 변환 실패 시 false 반환
        }
    }

    // 관리자로 로그인 시도하는 메서드
    // 부서 번호, 사용자 ID, 비밀번호를 기준으로 관리자 로그인 처리
    // 성공 시 true, 실패 시 false 반환
    private boolean tryAdminLogin(String deptNo, String userId, String userPassword, Map<String, Object> result) {
        Optional<AdminDepartment> adminDeptOpt = adminDepartmentRepository.findByAdminDeptNo(deptNo);
        
        // 부서 번호에 해당하는 관리자가 존재하지 않을 경우 실패 처리
        if (!adminDeptOpt.isPresent()) {
            result.put("success", false);
            result.put("message", "아이디 또는 비밀번호가 일치하지 않습니다.");  // 부서 번호가 일치하지 않을 경우 오류 메시지
            return false;
        }

        // 해당 부서 번호와 ID, 비밀번호가 일치하는 관리자가 있는지 확인
        Optional<Admin> adminOpt = adminRepository.findByAdminDeptNoAndAdminIdAndAdminPassword(deptNo, userId, userPassword);
        if (!adminOpt.isPresent()) {
            result.put("success", false);
            result.put("message", "아이디 또는 비밀번호가 일치하지 않습니다.");  // ID 또는 비밀번호가 틀린 경우 오류 메시지
            return false;
        }

        // 로그인 성공 시 결과를 세팅하여 반환
        Admin admin = adminOpt.get();   
        setLoginResult(result, "admin", admin.getAdminName(), adminDeptOpt.get().getAdminDeptName(), deptNo);
        return true;
    }

    // 매니저로 로그인 시도하는 메서드
    // 부서 번호, 사용자 ID, 비밀번호를 기준으로 매니저 로그인 처리
    // 성공 시 true, 실패 시 false 반환
    private boolean tryManagerLogin(String deptNo, String userId, String userPassword, Map<String, Object> result) {
        try {
            int managerDeptNo = Integer.parseInt(deptNo);  // 부서 번호를 숫자로 변환
            Optional<ManagerDepartment> departmentOpt = managerDepartmentRepository.findByManagerDeptNo(managerDeptNo);
            
            // 부서 번호에 해당하는 매니저가 존재하지 않을 경우 실패 처리
            if (!departmentOpt.isPresent()) {
                result.put("success", false);
                result.put("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
                return false;
            }

            // 해당 부서 번호와 ID, 비밀번호가 일치하는 매니저가 있는지 확인
            Optional<Manager> managerOpt = managerRepository.findByManagerDeptNoAndManagerIdAndManagerPassword(managerDeptNo, userId, userPassword);
            if (!managerOpt.isPresent()) {
                result.put("success", false);
                result.put("message", "아이디 또는 비밀번호가 일치하지 않습니다.");  // ID 또는 비밀번호가 틀린 경우 오류 메시지
                return false;
            }

            // 로그인 성공 시 결과를 세팅하여 반환
            Manager manager = managerOpt.get();
            setLoginResult(result, "manager", manager.getManagerName(), departmentOpt.get().getManagerDeptName(), deptNo);
            return true;
        } catch (NumberFormatException e) {
            // 부서 번호가 숫자가 아닐 경우 오류 메시지를 반환
            result.put("success", false);
            result.put("message", "부서 번호는 숫자여야 합니다.");
            return false;
        }
    }

    // 로그인 성공 시, 결과 Map에 사용자 정보 및 리다이렉션 URL을 설정하는 메서드
    private void setLoginResult(Map<String, Object> result, String userType, String userName, String departmentName, String deptNo) {
        result.put("success", true);  // 성공 상태 설정
        result.put("userType", userType);  // 사용자 유형 (admin 또는 manager)
        result.put("deptNo", deptNo);  // 부서 번호
        result.put("departmentName", departmentName);  // 부서 이름
        result.put("userName", userName);  // 사용자 이름
        result.put("redirectUrl", "/" + userType + "/dash-board");  // 성공 시 리다이렉션될 URL 설정 (관리자 또는 매니저 대시보드)
    }
}
