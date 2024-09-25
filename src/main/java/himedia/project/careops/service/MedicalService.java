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
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicalService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final MedicalRepository medicalRepository;
	private final ModelMapper modelMapper;
	
	public MedicalService(MedicalRepository medicalRepository, ModelMapper modelMapper) {
		this.medicalRepository = medicalRepository;
		this.modelMapper = modelMapper;
	}
	
	// 의료기기 장비세분류코드 찾기
	public ListMedicalDevicesDTO findByMedicalLmdMinorCateCode(String lmdMinorCateCode) {
		
		ListMedicalDevices medicalDevice = medicalRepository.findById(lmdMinorCateCode).orElseThrow(IllegalArgumentException::new);
		
		return modelMapper.map(medicalDevice, ListMedicalDevicesDTO.class);
	}
	
	// 의료기기 목록 + 페이지네이션
	public Page<ListMedicalDevicesDTO> findByMedicalDevices(Pageable pageable) {
		
		pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("lmdMinorCateCode").ascending());
		
		Page<ListMedicalDevices> medicalDevicesList = medicalRepository.findAll(pageable);
		
		return medicalDevicesList.map(medical -> modelMapper.map(medical, ListMedicalDevicesDTO.class));
	}

	// 의료기기 등록
	@Transactional
	public void addMedicalDevice(ListMedicalDevicesDTO newMedicalDevice) {
		medicalRepository.save(modelMapper.map(newMedicalDevice, ListMedicalDevices.class));
	}
}
