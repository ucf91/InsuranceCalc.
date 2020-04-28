package com.elementsin.insuranceproduct.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.elementsin.insuranceproduct.model.PricesLog;
import com.elementsin.insuranceproduct.repository.PricesLogRepository;
import com.elementsin.insuranceproduct.service.PricesLogService;

@Service
public class PricesLogServiceImpl implements PricesLogService {
	private final PricesLogRepository pricesLogRepository;

	public PricesLogServiceImpl(PricesLogRepository pricesLogRepository){
		this.pricesLogRepository = pricesLogRepository;
	}

	@Override
	public List<PricesLog> findPrices(Pageable pageable) {
		Page<PricesLog> returnedPage = this.pricesLogRepository.findAll(pageable);
		return returnedPage.getContent().stream().collect(Collectors.toList());
	}
}
