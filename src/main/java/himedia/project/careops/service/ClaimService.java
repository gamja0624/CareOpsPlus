package himedia.project.careops.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import himedia.project.careops.dto.ClaimSubCategoryDTO;
import himedia.project.careops.dto.ListMedicalDevicesDTO;
import himedia.project.careops.entity.ClaimSubCategory;
import himedia.project.careops.repository.ClaimSubCategoryRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 최은지
 * @editDate 2024-09-27
 */

@Slf4j
@Service
public class ClaimService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final ClaimSubCategoryRepository calimSubCategoryRepository;
	private final ModelMapper modelMapper;
	
	public ClaimService(ClaimSubCategoryRepository calimSubCategoryRepository, ModelMapper modelMapper) {
		this.calimSubCategoryRepository = calimSubCategoryRepository;
		this.modelMapper = modelMapper;
	}
	
	// 민원 소분류 전체 조회 
	public Page<ClaimSubCategoryDTO> findAllSubCategory(Pageable page) {
		log.info("findAllSubCategory 실행");
		page = PageRequest.of(page.getPageNumber() <= 0 ? 0 : page.getPageNumber() - 1,
                page.getPageSize(),
                Sort.by("claimSubCategoryNo").ascending());
		
		Page<ClaimSubCategory> claimsubCategory = calimSubCategoryRepository.findAll(page);
		
		log.info("findAllSubCategory 종료");
		return claimsubCategory.map(subCategory -> modelMapper.map(subCategory, ClaimSubCategoryDTO.class));
	}
	
}
