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
import himedia.project.careops.entity.Admin;
import himedia.project.careops.repository.AdminDepartmentRepository;
import himedia.project.careops.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final AdminRepository adminRepository;
	private final ModelMapper modelMapper;
	
	public AdminService(AdminRepository adminRepository, ModelMapper modelMapper) {
		this.adminRepository = adminRepository;
		this.modelMapper = modelMapper;
	}
	
	// 작성자 : 진혜정
    // 전체 관리자 조회
    public List<AdminDTO> findAllAdminList() {
        List<Admin> adminList = adminRepository.findAll();
        return adminList.stream()
                         .map(admin -> modelMapper.map(admin, AdminDTO.class))
                         .collect(Collectors.toList());
    }
	
    // 작성자 : 진혜정
	// 관리자 아이디로 관리자 정보 반환
	public AdminDTO findByAdminId(String adminId) {
		
		Admin findAdmin = adminRepository.findById(adminId).orElseThrow(IllegalArgumentException::new);
		
		return modelMapper.map(findAdmin, AdminDTO.class);
	}
	
	// 작성자 : 진혜정
	// 부서 이름으로 해당 작업자 리스트로 반환
	public List<Admin> findByAdminList(String lmdManagerDeptPart) {
		
		return adminRepository.findAll() 										// admin 에 저장된 모든 객체 가져오기
				.stream() 
				.filter(m -> m.getAdminDeptName().equals(lmdManagerDeptPart))	// 부서명이 같은 admin 객체 가져오기
				.collect(Collectors.toList());                                  // list 형태로 반환
	}
}
