package himedia.project.careops.repository;
/*
 * @author 노태윤
 * @editDate 2024-09-23~2024-09-24
 * 
 * */
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import himedia.project.careops.entity.Admin;
import himedia.project.careops.entity.Manager;
import himedia.project.careops.dto.AdminDTO;
import himedia.project.careops.dto.ManagerDTO; 
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class MyPageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<AdminDTO> getMyInfoByAdminId(String admin_id) {
        try {
            Admin admin = entityManager.createQuery("SELECT a FROM Admin a WHERE a.admin_id = :adminId", Admin.class)
                                             .setParameter("adminId", admin_id)
                                             .getSingleResult();
            return Optional.of(convertToDTO(admin));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<ManagerDTO> getMyInfoByManagerId(String manager_id) {
        try {
            Manager manager = entityManager.createQuery("SELECT m FROM Manager m WHERE m.manager_id = :managerId", Manager.class)
                                                 .setParameter("managerId", manager_id)
                                                 .getSingleResult();
            return Optional.of(convertToDTO(manager));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private AdminDTO convertToDTO(Admin admin) {
        AdminDTO dto = new AdminDTO();
        dto.setAdminId(admin.getAdminId());
        dto.setAdminDeptNo(admin.getAdminDeptNo());
        dto.setAdminName(admin.getAdminName());
        dto.setAdminPhoneNumber(admin.getAdminPhoneNumber());
        return dto;
    }

    private ManagerDTO convertToDTO(Manager manager) {
        ManagerDTO dto = new ManagerDTO();
        dto.setManagerId(manager.getManagerId());
        dto.setManagerDeptNo(manager.getManagerDeptNo());
        dto.setManagerName(manager.getManagerName());
        dto.setManagerPhoneNumber(manager.getManagerPhoneNumber());
        return dto;
    }

    @Transactional
    public int updateMyInfo(String admin_id, AdminDTO updateAdminDTO) {
        String jpql = "UPDATE AdminEntity a SET a.admin_dept_no = :admin_dept_no, a.admin_name = :admin_name, a.admin_phone_number = :admin_phoneNumber WHERE a.admin_id = :admin_id";
        return entityManager.createQuery(jpql)
                            .setParameter("admin_dept_no", updateAdminDTO.getAdminDeptNo())
                            .setParameter("admin_name", updateAdminDTO.getAdminName())
                            .setParameter("admin_phoneNumber", updateAdminDTO.getAdminPhoneNumber())
                            .setParameter("admin_id", admin_id)
                            .executeUpdate();
    }

    @Transactional
    public int updateMyInfo(String manager_id, ManagerDTO updateManagerDTO) {
        String jpql = "UPDATE ManagerEntity m SET m.manager_dept_no = :manager_dept_no, m.manager_name = :manager_name, m.manager_phone_number = :manager_phone_number WHERE m.manager_id = :manager_id";
        return entityManager.createQuery(jpql)
                            .setParameter("manager_dept_no", updateManagerDTO.getManagerDeptNo())
                            .setParameter("manager_name", updateManagerDTO.getManagerName())
                            .setParameter("manager_phone_number", updateManagerDTO.getManagerPhoneNumber())
                            .setParameter("manager_id", manager_id)
                            .executeUpdate();
    }
    
    // 비밀번호 변경 메소드
    @Transactional
    public int changePassword(String userId, String newPassword, String userType) {
        String jpql;
        if ("admin".equals(userType)) {
            jpql = "UPDATE AdminEntity a SET a.admin_password = :newPassword WHERE a.admin_id = :userId";
        } else if ("manager".equals(userType)) {
            jpql = "UPDATE ManagerEntity m SET m.manager_password = :newPassword WHERE m.manager_id = :userId";
        } else {
            return 0; // 유효하지 않은 사용자 유형
        }

        return entityManager.createQuery(jpql)
                            .setParameter("newPassword", newPassword)
                            .setParameter("userId", userId)
                            .executeUpdate();
    }

}
