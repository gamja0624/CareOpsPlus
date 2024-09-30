package himedia.project.careops.service;

/**
 * @author 진혜정, 최은지
 * @editDate 2024-09-25 ~ 
*/

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import himedia.project.careops.dto.ManagerDTO;
import himedia.project.careops.dto.ManagerDepartmentDTO;
import himedia.project.careops.entity.Manager;
import himedia.project.careops.entity.ManagerDepartment;
import himedia.project.careops.repository.ManagerDepartmentRepository;
import himedia.project.careops.repository.ManagerRepository;

@Service
public class ManagerService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final ManagerDepartmentRepository managerDepartmentRepository;
	private final ManagerRepository managerRepository;
	private final ModelMapper modelMapper;
	
	public ManagerService(
			ManagerDepartmentRepository managerDepartmentRepository, ManagerRepository managerRepository, 
			ModelMapper modelMapper) {
		this.managerRepository = managerRepository; 
		this.managerDepartmentRepository = managerDepartmentRepository;
		this.modelMapper = modelMapper;
	}
	
	// 작성자 : 진혜정
	// 전체 매니저 정보 리스트로 반환
	public List<ManagerDTO> findAllManagerList() {
		
		List<Manager> managerInfo = managerRepository.findAll();
		return managerInfo.stream()
				.map(manager -> modelMapper.map(manager, ManagerDTO.class))
                .collect(Collectors.toList());
	}
	
	// 작성자 : 진혜정
	// 매니저 아이디로 매니저 정보 객체로 반환
	public ManagerDTO findByManagerId(String managerId) {
		
		// log.info("[MangerService] 시작 !!!! - findByAdminNo 메서드 실행");
		Manager findManager = managerRepository.findById(managerId).orElseThrow(IllegalArgumentException::new);
		
		return modelMapper.map(findManager, ManagerDTO.class);
	}
	
	// 작성자 : 진혜정
	// 부서 이름으로 해당 매니저 리스트로 반환
	public List<Manager> findByManagerList(String lmdManagerDeptPart) {
		
		// log.info("[MangerService] 시작 !!!! - findByManagerList 메서드 실행");
		return managerRepository.findAll() 										// Manager 에 저장된 모든 객체 가져오기
				.stream() 
				.filter(m -> m.getManagerDeptName().equals(lmdManagerDeptPart))	// 부서명이 같은 manager 객체 가져오기
				.collect(Collectors.toList());                                  // list 형태로 반환
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

