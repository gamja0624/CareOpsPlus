package himedia.project.careops.service;

/**
 * @author 노태윤 
 * @editDate 2024-09-26
 */

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import himedia.project.careops.entity.Admin;
import himedia.project.careops.entity.Manager;
import himedia.project.careops.entity.ManagerDepartment;
import himedia.project.careops.entity.AdminDepartment;
import himedia.project.careops.repository.AdminRepository;
import himedia.project.careops.repository.ManagerDepartmentRepository;
import himedia.project.careops.repository.ManagerRepository;
import himedia.project.careops.repository.AdminDepartmentRepository; 
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    // 로그인 처리 메서드
    public String login(String deptNo, String userId, String userPassword) {
        // AdminDepartment 조회
        Optional<AdminDepartment> adminDeptOpt = adminDepartmentRepository.findByAdminDeptNo(deptNo);
        if (adminDeptOpt.isPresent()) {
            AdminDepartment adminDept = adminDeptOpt.get();
            Optional<Admin> admin = adminRepository.findByAdminDeptNoAndAdminIdAndAdminPassword(adminDept, userId, userPassword);
            if (admin.isPresent()) {
                // log.info("Admin 로그인 성공: {}", admin.get());
                return "admin"; // Admin 로그인 성공 시 "admin" 반환
            }
        }

        // Manager 로그인 처리
        try {
            int managerDeptNo = Integer.parseInt(deptNo); // 문자열을 정수로 변환
            Optional<ManagerDepartment> departmentOpt = managerDepartmentRepository.findByManagerDeptNo(managerDeptNo);
            
            if (departmentOpt.isPresent()) {
                ManagerDepartment department = departmentOpt.get();
                Optional<Manager> manager = managerRepository.findByManagerDeptNoAndManagerIdAndManagerPassword(department, userId, userPassword);
                if (manager.isPresent()) {
                    // log.info("Manager 로그인 성공: {}", manager.get());
                    return "manager"; // Manager 로그인 성공 시 "manager" 반환
                }
            } else {
                // log.warn("부서 번호가 존재하지 않습니다: {}", managerDeptNo);
                return "부서 번호가 유효하지 않습니다."; // 부서 번호가 유효하지 않을 경우 에러 메시지 반환
            }
        } catch (NumberFormatException e) {
            // log.error("부서 번호 변환 오류: {}", e.getMessage());
            return "부서 번호는 숫자여야 합니다."; // 부서 번호가 숫자가 아닐 경우 에러 메시지 반환
        }

        // log.warn("로그인 실패: 아이디 또는 비밀번호가 올바르지 않습니다.");
        return "로그인 실패: 아이디 또는 비밀번호가 올바르지 않습니다."; // 로그인 실패 시 에러 메시지 반환
    }
}
