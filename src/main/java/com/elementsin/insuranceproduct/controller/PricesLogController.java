package com.elementsin.insuranceproduct.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elementsin.insuranceproduct.dto.PricesLogDto;
import com.elementsin.insuranceproduct.mapper.EntityMapper;
import com.elementsin.insuranceproduct.model.PricesLog;
import com.elementsin.insuranceproduct.service.PricesLogService;

@RestController
public class PricesLogController {

	private final PricesLogService pricesLogService;
	private final EntityMapper<PricesLog, PricesLogDto> pricesLogMapper = pricesLog -> {
		PricesLogDto pricesLogDto = PricesLogDto.builder()
				.id(pricesLog.getId())
				.calculatedPrice(pricesLog.getCalculatedPrice())
				.moduleName(pricesLog.getModuleName())
				.selectedCoverage(pricesLog.getSelectedCoverage())
				.timestamp(pricesLog.getTimestamp())
				.build();
		return pricesLogDto;
	};

	public PricesLogController(PricesLogService pricesLogService){
		this.pricesLogService = pricesLogService;
	}
	@GetMapping(value = "v1/prices")
	public List<PricesLogDto> findPrices(@PageableDefault(page=0, size=10) Pageable pageable){
		return pricesLogMapper.mapList(this.pricesLogService.findPrices(pageable));
	}

}
