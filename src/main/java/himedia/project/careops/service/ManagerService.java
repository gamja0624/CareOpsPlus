package himedia.project.careops.service;

/**
 * @author 진혜정, 최은지
 * @editDate 2024-09-25 ~ 
*/

import java.util.List;
import java.util.Optional;
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
import himedia.project.careops.entity.Claim;
import himedia.project.careops.entity.Manager;
import himedia.project.careops.entity.ManagerDepartment;
import himedia.project.careops.repository.ManagerDepartmentRepository;
import himedia.project.careops.repository.ManagerRepository;

@Service
public class ManagerService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final ManagerRepository managerRepository;
	private final ModelMapper modelMapper;
	
	public ManagerService(ManagerRepository managerRepository, 
			ModelMapper modelMapper) {
		this.managerRepository = managerRepository; 
		this.modelMapper = modelMapper;
	}
	
	// 작성자 : 진혜정
	// 매니저 아이디로 매니저 정보 객체로 반환
	public ManagerDTO findByManagerId(String managerId) {
		
		Manager findManager = managerRepository.findById(managerId).orElseThrow(IllegalArgumentException::new);

		return modelMapper.map(findManager, ManagerDTO.class);
	}
	
	// 작성자 : 진혜정
	// 부서 이름으로 해당 매니저 리스트로 반환
	public List<Manager> findByManagerList(String lmdManagerDeptPart) {
		
		return managerRepository.findAll() 										// Manager 에 저장된 모든 객체 가져오기
				.stream() 
				.filter(m -> m.getManagerDeptName().equals(lmdManagerDeptPart))	// 부서명이 같은 manager 객체 가져오기
				.collect(Collectors.toList());                                  // list 형태로 반환
	}
    
    // 작성자 : 최은지
    // 담당자 전체 조회
    public Page<ManagerDTO> allManager(Pageable page) {
    	
    	page = PageRequest.of(page.getPageNumber() <= 0 ? 0 : page.getPageNumber() -1,
    						  page.getPageSize(),
    						  Sort.by("managerDeptNo").ascending());
    	
    	Page<Manager> managerList = managerRepository.findAll(page);
    	
    	return managerList.map(manager -> modelMapper.map(manager, ManagerDTO.class));
    }
    
    // 담당자 검색
	public List<Manager> searchManagerByFilter(String filter, String value) {
		List<Manager> ManagerSearchList = managerRepository.findAll();
		return ManagerSearchList.stream()
							.filter(srh -> {
								if (filter.equals("MangerDeptName")) {
									return srh.getManagerDeptName().contains(value);
								} else if (filter.equals("ManagerName")) {
									return srh.getManagerName().contains(value);
								} 
								return List.of().isEmpty();
							}).collect(Collectors.toList());	
	}
    
    
    // 작성자 : 최은지
    // 담당자 변경 ( 이름 , 전화번호 변경 )
    public void updateManager(ManagerDTO managerDTO) {
        Manager manager = managerRepository.findByManagerId(managerDTO.getManagerId())
                .orElseThrow(() -> new RuntimeException("담당자를 변경할 수 없습니다."));
        		// orElse는 삭제할까 고민중
        manager.setManagerName(managerDTO.getManagerName());
        manager.setManagerPhoneNumber(managerDTO.getManagerPhoneNumber());

        managerRepository.save(manager); // 변경 사항 저장

    }
    
    // 작성자 : 최은지
    // 담당자 중복 확인
    public boolean checkMangerId(String managerId) {
    	Optional<Manager> checkId = managerRepository.findByManagerId(managerId);
    
    	checkId.isEmpty();
    	
    	if(checkId.isPresent()) {
    		return true;
    	}
    	return false;
    }
    
    // 작성자 : 최은지
    // 담당자 저장 
    public void saveManager(ManagerDTO managerDTO,  ManagerDepartmentDTO departmentDTO ) {
    	
    	Manager manager = new Manager();
    	
    	manager.setManagerDeptNo(departmentDTO.getManagerDeptNo());
    	manager.setManagerDeptPart(departmentDTO.getManagerDeptPart());
    	
    	manager.setManagerDeptName(managerDTO.getManagerDeptName());   	
    	manager.setManagerName(managerDTO.getManagerName());    	
    	manager.setManagerPhoneNumber(managerDTO.getManagerPhoneNumber());   	
    	manager.setManagerId(managerDTO.getManagerId());
    	manager.setManagerPassword(managerDTO.getManagerPassword());    	
    	
    	managerRepository.save(manager);
	}
}
