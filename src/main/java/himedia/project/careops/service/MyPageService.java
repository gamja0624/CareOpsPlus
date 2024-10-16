package himedia.project.careops.service;

/*@author 노태윤
@editDate 2024-09-26*/

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import himedia.project.careops.dto.AdminDTO;
import himedia.project.careops.dto.ManagerDTO;
import himedia.project.careops.entity.Admin;
import himedia.project.careops.entity.Manager;
import himedia.project.careops.repository.AdminRepository;
import himedia.project.careops.repository.ManagerRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class MyPageService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ManagerRepository managerRepository;

    // Admin 정보를 ID로 조회하는 메서드
    // 주어진 adminId에 해당하는 Admin을 찾아 DTO로 변환하여 반환
    // 만약 해당 ID의 Admin이 없다면 EntityNotFoundException 발생
    public AdminDTO getAdminById(String adminId) {
        // AdminRepository에서 adminId로 Admin을 찾고, 존재하지 않으면 예외 처리
        return adminRepository.findByAdminId(adminId)
            .map(this::convertToDTO) // 엔티티를 DTO로 변환
            .orElseThrow(() -> new EntityNotFoundException("관리자를 찾을 수 없습니다: " + adminId));
    }

    // Manager 정보를 ID로 조회하는 메서드
    // 주어진 managerId에 해당하는 Manager를 찾아 DTO로 변환하여 반환
    // 만약 해당 ID의 Manager가 없다면 EntityNotFoundException 발생
    public ManagerDTO getManagerById(String managerId) {
        return managerRepository.findByManagerId(managerId)
            .map(this::convertToDTO)
            .orElseThrow(() -> new EntityNotFoundException("매니저를 찾을 수 없습니다: " + managerId));
    }
    
    // Admin 정보를 ID로 조회하고 Optional로 감싸서 반환하는 메서드
    // EntityNotFoundException이 발생하면 빈 Optional 반환
    public Optional<AdminDTO> getMyInfoByAdminId(String adminId) {
        try {
        	// 예외 처리하여 Optional 반환
            return Optional.of(getAdminById(adminId));
        } catch (EntityNotFoundException e) {
            return Optional.empty(); // 예외 발생 시 빈 Optional 반환
        }
    }
    
    // Manager 정보를 ID로 조회하고 Optional로 감싸서 반환하는 메서드
    // EntityNotFoundException이 발생하면 빈 Optional 반환
    public Optional<ManagerDTO> getMyInfoByManagerId(String managerId) {
        try {
            return Optional.of(getManagerById(managerId));
        } catch (EntityNotFoundException e) {
            return Optional.empty(); // 예외 발생 시 빈 Optional 반환
        }
    }

    // AdminDTO 객체를 Admin 엔티티로 변환하는 private 메서드
    // DTO의 정보를 새로운 Admin 엔티티에 복사
    private Admin convertToEntity(AdminDTO adminDTO) {
    	// Admin 엔티티 생성 및 DTO에서 값을 복사
        Admin admin = new Admin();
        admin.setAdminId(adminDTO.getAdminId());
        admin.setAdminPassword(adminDTO.getAdminPassword());
        admin.setAdminName(adminDTO.getAdminName());
        admin.setAdminPhoneNumber(adminDTO.getAdminPhoneNumber());
        return admin;
    }
    
    // ManagerDTO 객체를 Manager 엔티티로 변환하는 private 메서드
    // DTO의 정보를 새로운 Manager 엔티티에 복사
    private Manager convertToEntity(ManagerDTO managerDTO) {
    	// Manager 엔티티 생성 및 DTO에서 값을 복사
        Manager manager = new Manager();
        manager.setManagerId(managerDTO.getManagerId());
        manager.setManagerDeptNo(managerDTO.getManagerDeptNo());
        manager.setManagerDeptName(managerDTO.getManagerDeptName());
        manager.setManagerPassword(managerDTO.getManagerPassword());
        manager.setManagerName(managerDTO.getManagerName());
        manager.setManagerPhoneNumber(managerDTO.getManagerPhoneNumber());
        return manager;
    }
    
    // Admin 엔티티를 AdminDTO로 변환하는 private 메서드
    // 엔티티의 정보를 새로운 AdminDTO에 복사
    private AdminDTO convertToDTO(Admin admin) {
        // AdminDTO 생성 및 엔티티에서 값을 복사
        AdminDTO dto = new AdminDTO();
        dto.setAdminId(admin.getAdminId());
        dto.setAdminDeptNo(admin.getAdminDeptNo());
        dto.setAdminDeptName(admin.getAdminDeptName());
        dto.setAdminName(admin.getAdminName());
        dto.setAdminPhoneNumber(admin.getAdminPhoneNumber());
        return dto;
    }
    
    // Manager 엔티티를 ManagerDTO로 변환하는 private 메서드
    // 엔티티의 정보를 새로운 ManagerDTO에 복사
    private ManagerDTO convertToDTO(Manager manager) {
    	// ManagerDTO 생성 및 엔티티에서 값을 복사
        ManagerDTO dto = new ManagerDTO();
        dto.setManagerId(manager.getManagerId());
        dto.setManagerDeptNo(manager.getManagerDeptNo());
        dto.setManagerDeptName(manager.getManagerDeptName());
        dto.setManagerName(manager.getManagerName());
        dto.setManagerPhoneNumber(manager.getManagerPhoneNumber());
        return dto;
    }

    // Admin 정보를 업데이트하는 메서드
    // 주어진 AdminDTO의 정보로 기존 Admin 엔티티를 업데이트
    // 업데이트 성공 시 true 반환, 실패 시 EntityNotFoundException 발생
    @Transactional
    public boolean updateAdminInfo(AdminDTO adminDTO) {
        // adminId로 Admin 엔티티 조회
        Admin admin = adminRepository.findByAdminId(adminDTO.getAdminId())
            .orElseThrow(() -> new EntityNotFoundException("관리자를 찾을 수 없습니다: " + adminDTO.getAdminId()));
        
        // 이름과 전화번호 업데이트
        admin.setAdminName(adminDTO.getAdminName());
        admin.setAdminPhoneNumber(adminDTO.getAdminPhoneNumber());
        
        // 엔티티 저장
        adminRepository.save(admin);
        return true; // 업데이트 성공 시 true 반환
    }

    // Manager 정보를 업데이트하는 메서드
    // 주어진 ManagerDTO의 정보로 기존 Manager 엔티티를 업데이트
    // 업데이트 성공 시 true 반환, 실패 시 EntityNotFoundException 발생
    @Transactional
    public boolean updateManagerInfo(ManagerDTO managerDTO) {
    	// managerId로 Manager 엔티티 조회
        Manager manager = managerRepository.findByManagerId(managerDTO.getManagerId())
            .orElseThrow(() -> new EntityNotFoundException("매니저를 찾을 수 없습니다: " + managerDTO.getManagerId()));
        
        // 이름과 전화번호 업데이트
        manager.setManagerName(managerDTO.getManagerName());
        manager.setManagerPhoneNumber(managerDTO.getManagerPhoneNumber());
        
        // 엔티티 저장
        managerRepository.save(manager);
        return true;
    }
    
    // Admin의 비밀번호를 조회하는 메서드
    // 주어진 adminId에 해당하는 Admin의 비밀번호를 반환
    // 해당 ID의 Admin이 없으면 EntityNotFoundException 발생
    public String getAdminPassword(String adminId) {
    	// adminId로 Admin의 비밀번호를 조회
        return adminRepository.findByAdminId(adminId)
            .map(Admin::getAdminPassword)
            .orElseThrow(() -> new EntityNotFoundException("관리자를 찾을 수 없습니다: " + adminId));
    }
    
    // Manager의 비밀번호를 조회하는 메서드
    // 주어진 managerId에 해당하는 Manager의 비밀번호를 반환
    // 해당 ID의 Manager가 없으면 EntityNotFoundException 발생
    public String getManagerPassword(String managerId) {
    	// managerId로 Manager의 비밀번호를 조회
        return managerRepository.findByManagerId(managerId)
            .map(Manager::getManagerPassword)
            .orElseThrow(() -> new EntityNotFoundException("매니저를 찾을 수 없습니다: " + managerId));
    }
    
    // 비밀번호 검증 메서드
    public boolean isValidPassword(String password) {
        // 비밀번호는 최소 10자, 최대 16자, 소문자와 대문자 또는 숫자 포함해야 함
        return password.length() >= 10 && password.length() <= 16 &&
               password.matches(".*[a-z].*") &&  // 소문자 포함
               (password.matches(".*[A-Z].*") || password.matches(".*\\d.*")); // 대문자 또는 숫자 포함
    }
    
    
    // 현재 비밀번호가 정확한지 확인하는 메서드
    // userType에 따라 Admin 또는 Manager의 비밀번호를 검증
    public boolean isCurrentPasswordCorrect(String userId, String currentPassword, String userType) {
        if ("admin".equals(userType)) {
        	// Admin의 현재 비밀번호가 맞는지 확인
            Admin admin = adminRepository.findByAdminId(userId)
                    .orElseThrow(() -> new EntityNotFoundException("관리자를 찾을 수 없습니다: " + userId));
            return admin.getAdminPassword().equals(currentPassword);
        } else if ("manager".equals(userType)) {
        	// Manager의 현재 비밀번호가 맞는지 확인
            Manager manager = managerRepository.findByManagerId(userId)
                    .orElseThrow(() -> new EntityNotFoundException("매니저를 찾을 수 없습니다: " + userId));
            return manager.getManagerPassword().equals(currentPassword);
        }
        return false;
    }
    
    // 새로운 비밀번호를 설정하는 메서드
    // userType에 따라 Admin 또는 Manager의 비밀번호를 업데이트
    @Transactional
    public boolean changePassword(String userId, String currentPassword, String newPassword, String userType) {
        if ("admin".equals(userType)) {
        	// Admin 비밀번호 업데이트
            Admin admin = adminRepository.findByAdminId(userId)
                .orElseThrow(() -> new EntityNotFoundException("관리자를 찾을 수 없습니다: " + userId));
            
            if (!admin.getAdminPassword().equals(currentPassword)) {
                return false;
            }
            
            admin.setAdminPassword(newPassword);
            adminRepository.save(admin);
            return true;
        } else if ("manager".equals(userType)) {
        	// Manager 비밀번호 업데이트
            Manager manager = managerRepository.findByManagerId(userId)
                .orElseThrow(() -> new EntityNotFoundException("매니저를 찾을 수 없습니다: " + userId));
            
            if (!manager.getManagerPassword().equals(currentPassword)) {
                return false;
            }
            
            manager.setManagerPassword(newPassword);
            managerRepository.save(manager);
            return true;
        }
        return false;
    }
}