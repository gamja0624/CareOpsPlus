package himedia.project.careops.service;

/**
 * @author 진혜정 
 * @editDate 2024-09-23
 */

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import himedia.project.careops.dto.ListMedicalDevicesDTO;
import himedia.project.careops.entity.ListMedicalDevices;
import himedia.project.careops.repository.MedicalRepository;

@Service
public class MedicalService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final MedicalRepository medicalRepository;
	private final ModelMapper modelMapper;
	
	public MedicalService(MedicalRepository medicalRepository, ModelMapper modelMapper) {
		this.medicalRepository = medicalRepository;
		this.modelMapper = modelMapper;
	}

	public Page<ListMedicalDevicesDTO> findByMedicalDevices(Pageable pageable) {
		
		pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("lmdMinorCateCode").descending());
		
		Page<ListMedicalDevices> medicalDevicesList = medicalRepository.findAll(pageable);
		
		log.info("이거 뭐임 medicalRepository.findAll(pageable) : {}", medicalRepository.findAll(pageable));
		
		return medicalDevicesList.map(medical -> modelMapper.map(medical, ListMedicalDevicesDTO.class));
	}
}
