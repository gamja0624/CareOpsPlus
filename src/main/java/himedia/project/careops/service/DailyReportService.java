package himedia.project.careops.service;

/**
 * @author 이홍준
 * @editDate 2024-09-26 ~ 2024-10-17
 */

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import himedia.project.careops.dto.DailyManagementReportDTO;
import himedia.project.careops.entity.DailyManagementReport;
import himedia.project.careops.repository.DailyManagementReportRepository;
import jakarta.transaction.Transactional;

@Service
public class DailyReportService {

	private final DailyManagementReportRepository dailyManagementReportRepository;
	private final ModelMapper modelMapper;
	private Logger log = LoggerFactory.getLogger(this.getClass());

	public DailyReportService(DailyManagementReportRepository dailyManagementReportRepository,
			ModelMapper modelMapper) {
		this.dailyManagementReportRepository = dailyManagementReportRepository;
		this.modelMapper = modelMapper;
	}

	// [목록페이지 전체조회]
	public Page<DailyManagementReportDTO> reportAllList(Pageable pageable) {

		pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
				pageable.getPageSize(), Sort.by("dmrNo").descending());

		Page<DailyManagementReport> allList = dailyManagementReportRepository.findAll(pageable);

		return allList.map(reportList -> modelMapper.map(reportList, DailyManagementReportDTO.class));
	}

	// [1개 데이터 조회]
	public DailyManagementReportDTO findByReportNo(int dmrNo) {

		DailyManagementReport resultReport = dailyManagementReportRepository.findById(dmrNo)
				.orElseThrow(IllegalArgumentException::new);

		return modelMapper.map(resultReport, DailyManagementReportDTO.class);
	}

	// [수정] 수정목록 : adminId, adminName, dmrReportDetail, DmrIssue
	@Transactional
	public void editReportDetail(int dmrNo, String adminId, String adminName, DailyManagementReportDTO editReport) {

		DailyManagementReport beforeReport = dailyManagementReportRepository.findById(dmrNo).get();

		beforeReport.setDmrReportDetail(editReport.getDmrReportDetail());
		beforeReport.setAdminId(adminId);
		beforeReport.setAdminName(adminName);
		beforeReport.setDmrIssue(editReport.getDmrIssue());
	}

	// [등록]
	@Transactional
	public void reportRegistation(String adminid, String adminName, String adminDeptNo, String adminDeptName,
			DailyManagementReportDTO newReport) {

		newReport.setAdminId(adminid);
		newReport.setAdminDeptNo(adminDeptNo);
		newReport.setAdminName(adminName);
		newReport.setAdminDeptName(adminDeptName);

		dailyManagementReportRepository.save(modelMapper.map(newReport, DailyManagementReport.class));
	}

	// [MyPage -> adminName별 조회 + 페이지네이션]
	public Page<DailyManagementReportDTO> searchMyReport(Pageable pageable, String adminName) {

		pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
				pageable.getPageSize(), Sort.by("dmrNo").descending());

		// Pageable을 사용하여 데이터를 가져옴
		Page<DailyManagementReport> allList = dailyManagementReportRepository.findByAdminName(adminName, pageable);

		return allList.map(list -> modelMapper.map(list, DailyManagementReportDTO.class));
	}

	// 보고서 목록 View 검색 
	public Page<DailyManagementReportDTO> reportSearch(String filter, String value, Pageable pageable) {

		pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
				pageable.getPageSize(), Sort.by("dmrNo").descending());
			
		if (value.isEmpty()) {
			Page<DailyManagementReport> allList = dailyManagementReportRepository.findAll(pageable);
			return allList.map(reportList -> modelMapper.map(reportList, DailyManagementReportDTO.class));
		}
		
		Page<DailyManagementReport> findList = null;
		
		try {
			switch (filter) {
				case "dmrNo": {
					List<DailyManagementReportDTO> collect = dailyManagementReportRepository.findAll().stream()
					.filter(list -> String.valueOf(list.getDmrNo()).contains(value))
					.map(list -> modelMapper.map(list, DailyManagementReportDTO.class)).collect(Collectors.toList());
					
					// 페이징 처리
	                int start = (int) pageable.getOffset();
	                int end = Math.min(start + pageable.getPageSize(), collect.size());
	                List<DailyManagementReportDTO> pagedList = collect.subList(start, end);
					
					return new PageImpl<>(pagedList, pageable, collect.size()) ;
				}
				case "adminDeptName": {
					findList = dailyManagementReportRepository.findByAdminDeptNameContaining(value, pageable);
					return findList.map(list -> modelMapper.map(list, DailyManagementReportDTO.class)) ;
				}
				case "adminName": {
					findList = dailyManagementReportRepository.findByAdminNameContaining(value, pageable);
					return findList.map(list -> modelMapper.map(list, DailyManagementReportDTO.class)) ;
				}
				case "dmrDate": {
					Date date = Date.valueOf(value);
					findList = dailyManagementReportRepository.findByDmrDate(date, pageable);
					return findList.map(list -> modelMapper.map(list, DailyManagementReportDTO.class)) ;
				}
				default : {
					return Page.empty();
				}
			}
		} catch (Exception e) {
			return Page.empty();
		}
	}

	// 접속한 adminName을 통해 특정 날짜 데이터 검색하는 메소드
	public Page<DailyManagementReportDTO> searchReporByDate(String adminName, String filter, String value,
			Pageable pageable) {
		
		if (value.isEmpty()) {
			Page<DailyManagementReport> nameList = dailyManagementReportRepository.findByAdminName(adminName, pageable);
			return nameList.map(reportList -> modelMapper.map(reportList, DailyManagementReportDTO.class));
		}
		Date date = Date.valueOf(value);
		
		pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
				pageable.getPageSize(), Sort.by("dmrNo").descending());
		
		List<DailyManagementReport> byAdminName = dailyManagementReportRepository.findByAdminName(adminName);
		
		List<DailyManagementReportDTO> collect = byAdminName.stream()
													.filter(list -> list.getDmrDate().equals(date))
													.map(list -> modelMapper.map(list, DailyManagementReportDTO.class))
													.collect(Collectors.toList());
		
		// 페이징 처리
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), collect.size());
        List<DailyManagementReportDTO> pagedList = collect.subList(start, end);
		
		return new PageImpl<>(pagedList, pageable, collect.size());
	}

	// 작성자 : 진혜정
	// [전체 보고서 List 로 반환]
	public List<DailyManagementReport> findBydailyReportList() {

		return dailyManagementReportRepository.findAll().stream().collect(Collectors.toList());
	}
}
