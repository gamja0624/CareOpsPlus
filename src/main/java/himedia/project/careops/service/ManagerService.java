package himedia.project.careops.service;

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
	// 매니저 아이디로 매니저 정보 반환
	public ManagerDTO findByManagerId(String managerId) {
		
		// log.info("[MangerService] 시작 !!!! - findByAdminNo 메서드 실행");
		Manager findManager = managerRepository.findById(managerId).orElseThrow(IllegalArgumentException::new);
		
		return modelMapper.map(findManager, ManagerDTO.class);
	}

	// 작성자 : 진혜정
	// 부서 이름으로 매니저 리스트 찾기
	public void findByManagerName(String lmdManagerDeptPart) {
		
		log.info("[MangerService] 시작 !!!! - findByManagerName 메서드 실행");
		List<Manager> findDeptMangerList = managerRepository.findAll();
	
		System.out.println("ㅋㅋ" + lmdManagerDeptPart);
		for(Manager m : findDeptMangerList) {
			
			if (m.getManagerDeptName().equals(lmdManagerDeptPart)) {
				System.out.println("찾았땅" + m.getManagerName());
			}
		}
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

