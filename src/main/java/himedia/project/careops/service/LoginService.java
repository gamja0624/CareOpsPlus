package himedia.project.careops.service;

// @author 노태윤 
// @editDate 2024-09-26

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

    // 의존성 주입: 데이터베이스에서 관리자와 매니저 정보를 가져오는 Repository 클래스들과
    // 객체 간 매핑을 위한 ModelMapper를 주입받아 사용합니다.
    private final AdminRepository adminRepository;
    private final ManagerRepository managerRepository;
    private final ManagerDepartmentRepository managerDepartmentRepository;
    private final AdminDepartmentRepository adminDepartmentRepository;
    private final ModelMapper modelMapper;

    // 생성자에서 의존성 주입. 
    // Spring이 자동으로 주입하는 repository 및 mapper 객체들을 생성자에서 받아 초기화합니다.
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
    
    // 사용자 로그인 처리 메서드.
    // 사용자가 입력한 부서 번호, 아이디, 비밀번호를 받아서 관리자 또는 매니저로 로그인 시도
    // 각 역할에 맞는 로그인 처리를 시도하며 성공하면 사용자 정보를 반환, 실패하면 실패 메시지를 반환
    // @param deptNo 부서 번호 (관리자 또는 매니저가 소속된 부서)
    // @param userId 사용자 아이디 (관리자 또는 매니저)
    // @param userPassword 사용자 비밀번호
    // @return 로그인 성공 여부와 로그인한 사용자 정보를 담은 Map 객체
    public Map<String, Object> login(String deptNo, String userId, String userPassword) {
        Map<String, Object> result = new HashMap<>();

        // 관리자 로그인 시도. 성공 시 바로 결과 반환.
        if (tryAdminLogin(deptNo, userId, userPassword, result)) {
            return result;
        }

        // 매니저 로그인 시도. 성공 시 바로 결과 반환.
        if (tryManagerLogin(deptNo, userId, userPassword, result)) {
            return result;
        }

        // 로그인 실패 시 기본적으로 실패 메시지와 상태를 result에 담아서 반환.
        result.put("success", false);
        result.put("message", "로그인 실패: 아이디 또는 비밀번호가 올바르지 않습니다.");
        return result;
    }

    // 관리자 로그인 시도 메서드.
    // 부서 번호를 기반으로 AdminDepartment 엔티티를 찾고, 해당 부서의 관리자가 존재하는지 확인
    // 관리자가 존재하고 비밀번호가 일치하면 성공 처리를 하고, 그렇지 않으면 false를 반환
    // @param deptNo 관리자 부서 번호
    // @param userId 관리자 아이디
    // @param userPassword 관리자 비밀번호
    // @param result 로그인 결과를 저장할 Map 객체
    // @return 로그인 성공 여부 (true: 성공, false: 실패)
    private boolean tryAdminLogin(String deptNo, String userId, String userPassword, Map<String, Object> result) {
        // 부서 번호에 해당하는 관리자 부서 찾기
        Optional<AdminDepartment> adminDeptOpt = adminDepartmentRepository.findByAdminDeptNo(deptNo);
        
        if (adminDeptOpt.isPresent()) {
            // 관리자 정보 확인 (부서 번호, 아이디, 비밀번호가 모두 일치하는지 확인)
            Optional<Admin> adminOpt = adminRepository.findByAdminDeptNoAndAdminIdAndAdminPassword(deptNo, userId, userPassword);
            if (adminOpt.isPresent()) {
                // 관리자 정보가 존재하면 로그인 성공 처리
                Admin admin = adminOpt.get();   
                setLoginResult(result, "admin", admin.getAdminName(), adminDeptOpt.get().getAdminDeptName(), deptNo);
                return true;
            }
        }
        return false; // 로그인 실패 시 false 반환
    }

    // 매니저 로그인 시도 메서드.
    // 부서 번호가 유효한지 확인하고, 해당 부서의 매니저가 존재하는지 확인
    // 매니저가 존재하고 비밀번호가 일치하면 성공 처리를 하고, 그렇지 않으면 false를 반환
    // @param deptNo 매니저 부서 번호 (정수여야 함)
    // @param userId 매니저 아이디
    // @param userPassword 매니저 비밀번호
    // @param result 로그인 결과를 저장할 Map 객체
    // @return 로그인 성공 여부 (true: 성공, false: 실패)
    private boolean tryManagerLogin(String deptNo, String userId, String userPassword, Map<String, Object> result) {
        try {
            // 부서 번호가 숫자인지 확인하고, 정수로 변환 (부서 번호는 매니저에서 정수로 관리됨)
            int managerDeptNo = Integer.parseInt(deptNo);
            
            // 매니저 부서 정보 확인
            Optional<ManagerDepartment> departmentOpt = managerDepartmentRepository.findByManagerDeptNo(managerDeptNo);
            
            if (departmentOpt.isPresent()) {
                // 매니저 정보 확인 (부서 번호, 아이디, 비밀번호가 모두 일치하는지 확인)
                Optional<Manager> managerOpt = managerRepository.findByManagerDeptNoAndManagerIdAndManagerPassword(managerDeptNo, userId, userPassword);
                if (managerOpt.isPresent()) {
                    // 매니저 정보가 존재하면 로그인 성공 처리
                    Manager manager = managerOpt.get();
                    setLoginResult(result, "manager", manager.getManagerName(), departmentOpt.get().getManagerDeptName(), deptNo);
                    return true;
                }
            } else {
                // 부서 번호가 유효하지 않을 때의 오류 처리
                result.put("success", false);
                result.put("message", "부서 번호가 유효하지 않습니다.");
            }
        } catch (NumberFormatException e) {
            // 부서 번호가 숫자가 아닐 때의 오류 처리
            result.put("success", false);
            result.put("message", "부서 번호는 숫자여야 합니다.");
        }
        return false; // 로그인 실패 시 false 반환
    }

	// 로그인 결과 설정 메서드
    // 로그인 성공 시 사용자 유형(관리자 또는 매니저), 사용자 이름, 부서 이름을 결과에 저장
    // @param result 로그인 결과를 저장할 Map 객체
    // @param userType 사용자 유형 ("admin" 또는 "manager")
    // @param userName 사용자 이름
    // @param departmentName 부서 이름
    // @param deptNo 부서 번호
    private void setLoginResult(Map<String, Object> result, String userType, String userName, String departmentName, String deptNo) {
        result.put("success", true); // 로그인 성공 상태
        result.put("userType", userType); // 사용자 유형
        result.put("deptNo", deptNo); // 부서 번호
        result.put("departmentName", departmentName); // 부서 이름
        result.put("userName", userName); // 사용자 이름
        // redirectUrl 추가
        result.put("redirectUrl", "/" + userType + "/dash-board");
    }
}