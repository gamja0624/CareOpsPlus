package himedia.project.careops.service;

/**
 * @author 노태윤 
 * @editDate 2024-09-26
 */

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import himedia.project.careops.dto.AdminDTO;
import himedia.project.careops.dto.AdminDepartmentDTO;
import himedia.project.careops.dto.ManagerDTO;
import himedia.project.careops.entity.Admin;
import himedia.project.careops.entity.AdminDepartment;
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

	// AdminDepartmentDTO를 AdminDepartment로 변환하는 메서드
	private AdminDepartment convertToEntity(AdminDepartmentDTO adminDepartmentDTO) {
		AdminDepartment adminDepartment = new AdminDepartment();
		
	// 필드명을 adminDeptNo, adminDeptName에 맞춰 수정
		adminDepartment.setAdminDeptNo(adminDepartmentDTO.getAdminDeptNo());
		adminDepartment.setAdminDeptName(adminDepartmentDTO.getAdminDeptName());
		return adminDepartment;
	}
	
    // Admin 정보를 ID로 조회하는 메서드
    public AdminDTO getAdminById(String adminId) {
        return adminRepository.findByAdminId(adminId)
            .map(this::convertToDTO)
            .orElseThrow(() -> new EntityNotFoundException("관리자를 찾을 수 없습니다: " + adminId));
    }

    // Manager 정보를 ID로 조회하는 메서드
    public ManagerDTO getManagerById(String managerId) {
        return managerRepository.findByManagerId(managerId)
            .map(this::convertToDTO)
            .orElseThrow(() -> new EntityNotFoundException("매니저를 찾을 수 없습니다: " + managerId));
    }

	// AdminDTO를 Admin 엔티티로 변환하는 메서드
	private Admin convertToEntity(AdminDTO adminDTO) {
		Admin admin = new Admin();
		admin.setAdminId(adminDTO.getAdminId());

		// AdminDepartmentDTO를 AdminDepartment로 변환한 후 설정
		AdminDepartment adminDepartment = convertToEntity(adminDTO.getAdminDeptNo());
		admin.setAdminDeptNo(adminDepartment);

		admin.setAdminPassword(adminDTO.getAdminPassword());
		admin.setAdminName(adminDTO.getAdminName());
		admin.setAdminPhoneNumber(adminDTO.getAdminPhoneNumber());
		return admin;
	}

	// ManagerDTO를 Manager 엔티티로 변환하는 메서드
	private Manager convertToEntity(ManagerDTO managerDTO) {
		Manager manager = new Manager();
		manager.setManagerId(managerDTO.getManagerId());
		manager.setManagerDeptNo(managerDTO.getManagerDeptNo()); // managerDeptNo 설정
		manager.setManagerDeptPart(managerDTO.getManagerDeptPart());
		manager.setManagerDeptName(managerDTO.getManagerDeptName());
		manager.setManagerPassword(managerDTO.getManagerPassword());
		manager.setManagerName(managerDTO.getManagerName());
		manager.setManagerPhoneNumber(managerDTO.getManagerPhoneNumber());
		return manager;
	}

	// Admin 정보 조회 메서드
	public Optional<AdminDTO> getMyInfoByAdminId(String adminId) {
		return adminRepository.findByAdminId(adminId).map(this::convertToDTO);
	}

	// Manager 정보 조회 메서드
	public Optional<ManagerDTO> getMyInfoByManagerId(String managerId) {
		return managerRepository.findByManagerId(managerId).map(this::convertToDTO);
	}

	// Admin 엔티티를 AdminDTO로 변환하는 메서드
	private AdminDTO convertToDTO(Admin admin) {
		AdminDTO dto = new AdminDTO();
		dto.setAdminId(admin.getAdminId());

		// AdminDepartmentDTO로 변환하여 설정
		AdminDepartmentDTO deptDTO = new AdminDepartmentDTO();
		deptDTO.setAdminDeptNo(admin.getAdminDeptNo().getAdminDeptNo());
		deptDTO.setAdminDeptName(admin.getAdminDeptNo().getAdminDeptName());
		dto.setAdminDeptNo(deptDTO); // AdminDepartmentDTO 설정

		dto.setAdminDeptName(admin.getAdminDeptNo().getAdminDeptName());
		dto.setAdminName(admin.getAdminName());
		dto.setAdminPhoneNumber(admin.getAdminPhoneNumber());
		return dto;
	}

	// Manager 엔티티를 ManagerDTO로 변환하는 메서드
	private ManagerDTO convertToDTO(Manager manager) {
		ManagerDTO dto = new ManagerDTO();
		dto.setManagerId(manager.getManagerId());
		dto.setManagerDeptNo(manager.getManagerDeptNo());
		dto.setManagerDeptName(manager.getManagerDeptName());
		dto.setManagerName(manager.getManagerName());
		dto.setManagerPhoneNumber(manager.getManagerPhoneNumber());
		return dto;
	}

//    // Admin 정보를 업데이트하는 메서드
//    @Transactional
//    public void updateAdminInfo(AdminDTO adminDTO, String name, String phoneNumber) {
//        adminDTO.setAdminName(name);
//        adminDTO.setAdminPhoneNumber(phoneNumber);
//        
//        Admin adminEntity = convertToEntity(adminDTO);
//        adminRepository.save(adminEntity);
//    }

	// Admin 정보를 업데이트하는 메서드
    @Transactional
    public boolean updateAdminInfo(AdminDTO adminDTO) {
        Admin admin = adminRepository.findByAdminId(adminDTO.getAdminId())
            .orElseThrow(() -> new EntityNotFoundException("관리자를 찾을 수 없습니다: " + adminDTO.getAdminId()));
        
        admin.setAdminName(adminDTO.getAdminName());
        admin.setAdminPhoneNumber(adminDTO.getAdminPhoneNumber());
        
        Admin savedAdmin = adminRepository.saveAndFlush(admin);
        System.out.println("Updated Admin: " + savedAdmin); // 로그 추가
        return true;
    }

    @Transactional
    public boolean updateManagerInfo(ManagerDTO managerDTO) {
        Manager manager = managerRepository.findByManagerId(managerDTO.getManagerId())
            .orElseThrow(() -> new EntityNotFoundException("매니저를 찾을 수 없습니다: " + managerDTO.getManagerId()));
        
        manager.setManagerName(managerDTO.getManagerName());
        manager.setManagerPhoneNumber(managerDTO.getManagerPhoneNumber());
        
        Manager savedManager = managerRepository.saveAndFlush(manager);
        System.out.println("Updated Manager: " + savedManager); // 로그 추가
        return true;
    }
    
    // 비밀번호 조회 메서드 admin
    public String getAdminPassword(String adminId) {
        return adminRepository.findByAdminId(adminId)
                .map(Admin::getAdminPassword)
                .orElseThrow(() -> new EntityNotFoundException("관리자를 찾을 수 없습니다"));
    }

	// 비밀번호 조회 메서드 manager
	public String getManagerPassword(String managerId) {
		return managerRepository.findById(managerId).map(Manager::getManagerPassword)
				.orElseThrow(() -> new EntityNotFoundException("매니저를 찾을 수 없습니다"));
	}

	// 비밀번호 변경 메서드
	@Transactional
	public int changePassword(String userId, String newPassword, String userType) {
		if ("admin".equals(userType)) {
			return adminRepository.findByAdminId(userId).map(admin -> {
				admin.setAdminPassword(newPassword);
				adminRepository.save(admin);
				return 1;
			}).orElse(0);
		} else if ("manager".equals(userType)) {
			return managerRepository.findByManagerId(userId).map(manager -> {
				manager.setManagerPassword(newPassword);
				managerRepository.save(manager);
				return 1;
			}).orElse(0);
		}
		return 0;
	}

	@Transactional
	public boolean changePassword2(String userId, String newPassword, String userType) {
		System.out.println("changePassword2 메서드 호출됨");
		System.out.println("userId: " + userId);
		System.out.println("userType: " + userType);
		try {
			if ("admin".equals(userType)) {
				// 관리자 비밀번호 변경 로직
				return adminRepository.findByAdminId(userId).map(admin -> {
					admin.setAdminPassword(newPassword);
					adminRepository.save(admin);
					System.out.println("관리자 비밀번호 변경 성공");
					return true;
				}).orElse(false);
			} else if ("manager".equals(userType)) {
				// 매니저 비밀번호 변경 로직
				return managerRepository.findByManagerId(userId).map(manager -> {
					manager.setManagerPassword(newPassword);
					managerRepository.save(manager);
					System.out.println("매니저 비밀번호 변경 성공");
					return true;
				}).orElse(false);
			}
			System.out.println("유효하지 않은 사용자 유형");
			return false;
		} catch (Exception e) {
			System.err.println("비밀번호 변경 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public String updateUserInfo(String userType, String userId, String name, String phoneNumber) {
		if ("admin".equals(userType)) {
			// 관리자 정보 업데이트 로직
		} else if ("manager".equals(userType)) {
			// 매니저 정보 업데이트 로직
		}
		return "정보가 성공적으로 업데이트되었습니다.";
	}
}