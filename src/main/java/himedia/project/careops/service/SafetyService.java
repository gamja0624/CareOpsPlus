package himedia.project.careops.service;

/**
 * @author 이홍준
 * @editDate 2024-09-25 ~ 
 */


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import himedia.project.careops.dto.SafetyManagementDTO;
import himedia.project.careops.dto.SafetyManagementListDTO;
import himedia.project.careops.entity.SafetyManagement;
import himedia.project.careops.entity.SafetyManagementList;
import himedia.project.careops.repository.SafetyManagementChecklistRepository;
import himedia.project.careops.repository.SafetyManagementListRepository;
import himedia.project.careops.repository.SafetyManagementRepository;

@Service
public class SafetyService {

	private final SafetyManagementRepository safetyManagementRepository;
	private final SafetyManagementListRepository safetyManagementListRepository;
	private final SafetyManagementChecklistRepository safetyManagementChecklistRepository;
	private final ModelMapper modelMapper;
	
	public SafetyService(SafetyManagementRepository safetyManagementRepository,
			SafetyManagementListRepository safetyManagementListRepository,
			SafetyManagementChecklistRepository safetyManagementChecklistRepository, ModelMapper modelMapper) {
		super();
		this.safetyManagementRepository = safetyManagementRepository;
		this.safetyManagementListRepository = safetyManagementListRepository;
		this.safetyManagementChecklistRepository = safetyManagementChecklistRepository;
		this.modelMapper = modelMapper;
	}
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public List<SafetyManagementDTO> safetyResultList() {
		List<SafetyManagement> safetyResultList = safetyManagementRepository.findAll();
		return safetyResultList.stream().map(safetyManagement -> modelMapper.map(safetyManagement, SafetyManagementDTO.class)).collect(Collectors.toList());
	}

	public List<SafetyManagementListDTO> safetyListAll() {
		List<SafetyManagementList> safetyListAll = safetyManagementListRepository.findAll();
		return safetyListAll.stream().map(safetyManagementList -> modelMapper.map(safetyManagementList, SafetyManagementListDTO.class)).collect(Collectors.toList());
	}
	
	// 등록 수정 삭제 시 @Transactional 설정 필요
	
}
