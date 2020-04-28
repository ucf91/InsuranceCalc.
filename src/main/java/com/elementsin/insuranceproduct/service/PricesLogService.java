package com.elementsin.insuranceproduct.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.elementsin.insuranceproduct.model.PricesLog;

public interface PricesLogService {
	List<PricesLog> findPrices(Pageable pageable);
}
