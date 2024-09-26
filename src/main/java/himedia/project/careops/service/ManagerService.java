package himedia.project.careops.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import himedia.project.careops.dto.ManagerDTO;
import himedia.project.careops.entity.Manager;
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
		this.managerDepartmentRepository = managerDepartmentRepository;
		this.managerRepository = managerRepository;
		this.modelMapper = modelMapper;
	}
	
	// 매니저 아이디로 매니저 정보 반환
	public ManagerDTO findByManagerId(String managerId) {
		
		// log.info("[MangerService] 시작 !!!! - findByAdminNo 메서드 실행");
		Manager findManager = managerRepository.findById(managerId).orElseThrow(IllegalArgumentException::new);
		
		return modelMapper.map(findManager, ManagerDTO.class);
	}

	public void findByManagerName(String lmdManagerDeptPart) {
		
		log.info("[MangerService] 시작 !!!! - findByManagerName 메서드 실행");
		List<Manager> findDeptMangerList = managerRepository.findAll();
	
		System.out.println("ㅋㅋ" + lmdManagerDeptPart);
		for(Manager m : findDeptMangerList) {
			
			if (m.getManagerDeptName().equals(lmdManagerDeptPart)) {
				System.out.println("찾았땅" + m.getManagerName());
			}
		}
		
//		findDeptMangerList.stream()
//					.map(findManger -> modelMapper.map(lmdManagerDeptPart, ManagerDTO.class))
//					.collect(Collectors.toList());
		
		
	}
}

