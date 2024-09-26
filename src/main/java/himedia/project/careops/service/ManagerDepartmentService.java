package himedia.project.careops.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

/**
 * @author 진혜정 
 * @editDate 2024-09-25
 */

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
	
    // 전체 부서 조회
    public List<ManagerDepartmentDTO> findAllDepartments() {
        List<ManagerDepartment> departments = managerDepartmentRepository.findAll();
        return departments.stream()
                         .map(department -> modelMapper.map(department, ManagerDepartmentDTO.class))
                         .collect(Collectors.toList());
    }
	
}
