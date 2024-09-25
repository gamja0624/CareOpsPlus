package himedia.project.careops.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import himedia.project.careops.dto.ManagerDTO;
import himedia.project.careops.dto.ManagerDepartmentDTO;
import himedia.project.careops.entity.Manager;
import himedia.project.careops.entity.ManagerDepartment;
import himedia.project.careops.repository.ManagerDepartmentRepository;
import himedia.project.careops.repository.ManagerRepository;

/**
 * @author 최은지 
 * @editDate 2024-09-25
 */

/**
 *
 * @author 진혜정 
 * @editDate 2024-09-25
 */

@Service
public class ManagerService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final ManagerRepository managerRepository;
	private final ManagerDepartmentRepository managerDepartmentRepository;
	private final ModelMapper modelMapper;
	
	public ManagerService(ManagerDepartmentRepository managerDepartmentRepository, ManagerRepository managerRepository, ModelMapper modelMapper) {
		this.managerRepository = managerRepository; 
		this.managerDepartmentRepository = managerDepartmentRepository;
		this.modelMapper = modelMapper;
	}
	
	// 작성자 : 최은지
    // 담당자 전체 부서 조회 
    public List<ManagerDepartmentDTO> findAllDepartments() {
        List<ManagerDepartment> departments = managerDepartmentRepository.findAll();
        return departments.stream()
                         .map(department -> modelMapper.map(department, ManagerDepartmentDTO.class))
                         .collect(Collectors.toList());
    }
    
    // 작성자 : 최은지
    // 담당자 전체 조회
    public Page<ManagerDTO> allManager(Pageable page) {
    	
//    	page = PageRequest.of(page.getPageNumber() <= 0 ? 0 : page.getPageNumber() -1,
//    						  page.getPageSize(),
//    						  Sort.by("managerDeptNo").descending());
    	
    	Page<Manager> managerList = managerRepository.findAll(page);
    	
    	return managerList.map(manager -> modelMapper.map(manager, ManagerDTO.class));
    }
    
}

