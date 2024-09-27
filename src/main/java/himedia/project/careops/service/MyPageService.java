package himedia.project.careops.service;

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

    // 작성자 : 노태윤
    // Admin 정보를 ID로 조회하는 메서드
    // 주어진 adminId에 해당하는 Admin을 찾아 DTO로 변환하여 반환
    // 만약 해당 ID의 Admin이 없다면 EntityNotFoundException 발생
    public AdminDTO getAdminById(String adminId) {
        return adminRepository.findByAdminId(adminId)
            .map(this::convertToDTO)
            .orElseThrow(() -> new EntityNotFoundException("관리자를 찾을 수 없습니다: " + adminId));
    }

    // 작성자 : 노태윤
    // Manager 정보를 ID로 조회하는 메서드
    // 주어진 managerId에 해당하는 Manager를 찾아 DTO로 변환하여 반환
    // 만약 해당 ID의 Manager가 없다면 EntityNotFoundException 발생
    public ManagerDTO getManagerById(String managerId) {
        return managerRepository.findByManagerId(managerId)
            .map(this::convertToDTO)
            .orElseThrow(() -> new EntityNotFoundException("매니저를 찾을 수 없습니다: " + managerId));
    }
    // 작성자 : 노태윤
    // Admin 정보를 ID로 조회하고 Optional로 감싸서 반환하는 메서드
    // EntityNotFoundException이 발생하면 빈 Optional 반환
    public Optional<AdminDTO> getMyInfoByAdminId(String adminId) {
        try {
            return Optional.of(getAdminById(adminId));
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }
    // 작성자 : 노태윤
    // Manager 정보를 ID로 조회하고 Optional로 감싸서 반환하는 메서드
    // EntityNotFoundException이 발생하면 빈 Optional 반환
    public Optional<ManagerDTO> getMyInfoByManagerId(String managerId) {
        try {
            return Optional.of(getManagerById(managerId));
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }

    // 작성자 : 노태윤
    // AdminDTO 객체를 Admin 엔티티로 변환하는 private 메서드
    // DTO의 정보를 새로운 Admin 엔티티에 복사
    private Admin convertToEntity(AdminDTO adminDTO) {
        Admin admin = new Admin();
        admin.setAdminId(adminDTO.getAdminId());
        admin.setAdminPassword(adminDTO.getAdminPassword());
        admin.setAdminName(adminDTO.getAdminName());
        admin.setAdminPhoneNumber(adminDTO.getAdminPhoneNumber());
        return admin;
    }
    // 작성자 : 노태윤
    // ManagerDTO 객체를 Manager 엔티티로 변환하는 private 메서드
    // DTO의 정보를 새로운 Manager 엔티티에 복사
    private Manager convertToEntity(ManagerDTO managerDTO) {
        Manager manager = new Manager();
        manager.setManagerId(managerDTO.getManagerId());
        manager.setManagerDeptNo(managerDTO.getManagerDeptNo());
        manager.setManagerDeptName(managerDTO.getManagerDeptName());
        manager.setManagerPassword(managerDTO.getManagerPassword());
        manager.setManagerName(managerDTO.getManagerName());
        manager.setManagerPhoneNumber(managerDTO.getManagerPhoneNumber());
        return manager;
    }
    // 작성자 : 노태윤
    // Admin 엔티티를 AdminDTO로 변환하는 private 메서드
    // 엔티티의 정보를 새로운 AdminDTO에 복사
    private AdminDTO convertToDTO(Admin admin) {
        AdminDTO dto = new AdminDTO();
        dto.setAdminId(admin.getAdminId());
        dto.setAdminDeptNo(admin.getAdminDeptNo());
        dto.setAdminDeptName(admin.getAdminDeptName());
        dto.setAdminName(admin.getAdminName());
        dto.setAdminPhoneNumber(admin.getAdminPhoneNumber());
        return dto;
    }
    // 작성자 : 노태윤
    // Manager 엔티티를 ManagerDTO로 변환하는 private 메서드
    // 엔티티의 정보를 새로운 ManagerDTO에 복사
    private ManagerDTO convertToDTO(Manager manager) {
        ManagerDTO dto = new ManagerDTO();
        dto.setManagerId(manager.getManagerId());
        dto.setManagerDeptNo(manager.getManagerDeptNo());
        dto.setManagerDeptName(manager.getManagerDeptName());
        dto.setManagerName(manager.getManagerName());
        dto.setManagerPhoneNumber(manager.getManagerPhoneNumber());
        return dto;
    }

    // 작성자 : 노태윤
    // Admin 정보를 업데이트하는 메서드
    // 주어진 AdminDTO의 정보로 기존 Admin 엔티티를 업데이트
    // 업데이트 성공 시 true 반환, 실패 시 EntityNotFoundException 발생
    @Transactional
    public boolean updateAdminInfo(AdminDTO adminDTO) {
        Admin admin = adminRepository.findByAdminId(adminDTO.getAdminId())
            .orElseThrow(() -> new EntityNotFoundException("관리자를 찾을 수 없습니다: " + adminDTO.getAdminId()));
        
        admin.setAdminName(adminDTO.getAdminName());
        admin.setAdminPhoneNumber(adminDTO.getAdminPhoneNumber());
        
        adminRepository.save(admin);
        return true;
    }

    // 작성자 : 노태윤
    // Manager 정보를 업데이트하는 메서드
    // 주어진 ManagerDTO의 정보로 기존 Manager 엔티티를 업데이트
    // 업데이트 성공 시 true 반환, 실패 시 EntityNotFoundException 발생
    @Transactional
    public boolean updateManagerInfo(ManagerDTO managerDTO) {
        Manager manager = managerRepository.findByManagerId(managerDTO.getManagerId())
            .orElseThrow(() -> new EntityNotFoundException("매니저를 찾을 수 없습니다: " + managerDTO.getManagerId()));
        
        manager.setManagerName(managerDTO.getManagerName());
        manager.setManagerPhoneNumber(managerDTO.getManagerPhoneNumber());
        
        managerRepository.save(manager);
        return true;
    }
    
    // 작성자 : 노태윤	
    // Admin의 비밀번호를 조회하는 메서드
    // 주어진 adminId에 해당하는 Admin의 비밀번호를 반환
    // 해당 ID의 Admin이 없으면 EntityNotFoundException 발생
    public String getAdminPassword(String adminId) {
        return adminRepository.findByAdminId(adminId)
            .map(Admin::getAdminPassword)
            .orElseThrow(() -> new EntityNotFoundException("관리자를 찾을 수 없습니다: " + adminId));
    }
    
    // 작성자 : 노태윤
    // Manager의 비밀번호를 조회하는 메서드
    // 주어진 managerId에 해당하는 Manager의 비밀번호를 반환
    // 해당 ID의 Manager가 없으면 EntityNotFoundException 발생
    public String getManagerPassword(String managerId) {
        return managerRepository.findByManagerId(managerId)
            .map(Manager::getManagerPassword)
            .orElseThrow(() -> new EntityNotFoundException("매니저를 찾을 수 없습니다: " + managerId));
    }
    
    // 작성자 : 노태윤
    // 사용자의 비밀번호를 변경하는 메서드
    // userType에 따라 Admin 또는 Manager의 비밀번호를 변경
    // 변경 성공 시 true, 실패 시 false 반환
    @Transactional
    public boolean changePassword2(String userId, String newPassword, String userType) {
        if ("admin".equals(userType)) {
            return adminRepository.findByAdminId(userId).map(admin -> {
                admin.setAdminPassword(newPassword);
                adminRepository.save(admin);
                return true;
            }).orElse(false);
        } else if ("manager".equals(userType)) {
            return managerRepository.findByManagerId(userId).map(manager -> {
                manager.setManagerPassword(newPassword);
                managerRepository.save(manager);
                return true;
            }).orElse(false);
        }
        return false;
    }
}