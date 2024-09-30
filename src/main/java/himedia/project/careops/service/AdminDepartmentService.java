package himedia.project.careops.service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 진혜정 
 * @editDate 2024-09-25
 */

import org.modelmapper.ModelMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import himedia.project.careops.dto.AdminDepartmentDTO;
import himedia.project.careops.entity.AdminDepartment;
import himedia.project.careops.repository.AdminDepartmentRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminDepartmentService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final AdminDepartmentRepository adminDepartmentRepository;
	private final ModelMapper modelMapper;
	
	public AdminDepartmentService(AdminDepartmentRepository adminDepartmentRepository, ModelMapper modelMapper) {
		this.adminDepartmentRepository = adminDepartmentRepository;
		this.modelMapper = modelMapper;
	}
	
	// 작성자 : 진혜정
    // 전체 부서 조회
    public List<AdminDepartmentDTO> findAllDepartments() {
        List<AdminDepartment> departments = adminDepartmentRepository.findAll();
        return departments.stream()
                         .map(department -> modelMapper.map(department, AdminDepartmentDTO.class))
                         .collect(Collectors.toList());
    }
	
    // 작성자 : 진혜정
	// 관리자 부서 번호로 찾기
	public AdminDepartmentDTO findByAdminDeptNo(String adminDeptNo) {
		
		// log.info("[AdminDepartmentService] 시작 !!!! - findByAdminDeptNo");
		AdminDepartment adminDept = adminDepartmentRepository.findById(adminDeptNo).orElseThrow(IllegalArgumentException::new);
		
		return modelMapper.map(adminDept, AdminDepartmentDTO.class);
	}
	
}
