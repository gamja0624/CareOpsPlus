package himedia.project.careops.service;

/**
 * @author 진혜정 
 * @editDate 2024-09-25
 */

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import himedia.project.careops.dto.AdminDTO;
import himedia.project.careops.dto.AdminDepartmentDTO;
import himedia.project.careops.entity.Admin;
import himedia.project.careops.entity.AdminDepartment;
import himedia.project.careops.repository.AdminDepartmentRepository;
import himedia.project.careops.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final AdminDepartmentRepository adminDepartmentRepository;
	private final AdminRepository adminRepository;
	private final ModelMapper modelMapper;
	
	public AdminService(
			AdminDepartmentRepository adminDepartmentRepository, AdminRepository adminRepository, 
			ModelMapper modelMapper) {
		this.adminDepartmentRepository = adminDepartmentRepository;
		this.adminRepository = adminRepository;
		this.modelMapper = modelMapper;
	}
	
    // 전체 부서 조회
    public List<AdminDepartmentDTO> findAllDepartments() {
        List<AdminDepartment> departments = adminDepartmentRepository.findAll();
        return departments.stream()
                         .map(department -> modelMapper.map(department, AdminDepartmentDTO.class))
                         .collect(Collectors.toList());
    }
	
	// 관리자 아이디로 관리자 정보 반환
	public AdminDTO findByAdminId(String adminId) {
		
		// log.info("[AdminService] 시작 !!!! - findByAdminNo 메서드 실행");
		Admin findAdmin = adminRepository.findById(adminId).orElseThrow(IllegalArgumentException::new);
		
		return modelMapper.map(findAdmin, AdminDTO.class);
	}
	
	
}
