package himedia.project.careops.service;

/**
 * @author 진혜정, 최은지
 * @editDate 2024-09-25
 */

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import himedia.project.careops.dto.ManagerDepartmentDTO;
import himedia.project.careops.entity.ManagerDepartment;
import himedia.project.careops.repository.ManagerDepartmentRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ManagerDepartmentService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final ManagerDepartmentRepository managerDepartmentRepository;
	private final ModelMapper modelMapper;
	
	public ManagerDepartmentService(ManagerDepartmentRepository managerDepartmentRepository, ModelMapper modelMapper) {
		this.managerDepartmentRepository = managerDepartmentRepository;
		this.modelMapper = modelMapper;
	}
	
	// 작성자 : 진혜정
    // 전체 부서 객체로 리스트로 반환
    public List<ManagerDepartmentDTO> findAllDepartmentsList() {
    	
        List<ManagerDepartment> departments = managerDepartmentRepository.findAll();
        return departments.stream()
                         .map(department -> modelMapper.map(department, ManagerDepartmentDTO.class))
                         .collect(Collectors.toList());
    }
    
    // 작성자 : 최은지
    // 부서 이름으로 부서 정보 찾기
    public ManagerDepartmentDTO findByDeptName (String managerDeptName) {
    	
    	return managerDepartmentRepository.findAll()
    					.stream()
    					.filter(deptName -> deptName.getManagerDeptName().equals(managerDeptName))
    					.map(department -> modelMapper.map(department, ManagerDepartmentDTO.class))
    					.findAny()  
    			        .orElse(null);
    }
}
