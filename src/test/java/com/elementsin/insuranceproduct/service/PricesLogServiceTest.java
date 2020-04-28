package com.elementsin.insuranceproduct.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.elementsin.insuranceproduct.model.PricesLog;
import com.elementsin.insuranceproduct.repository.PricesLogRepository;
import com.elementsin.insuranceproduct.service.impl.PricesLogServiceImpl;
import com.elementsin.insuranceproduct.util.TestUtils;


@RunWith(MockitoJUnitRunner.class)
public class PricesLogServiceTest {

	private PricesLogService pricesLogService;

	@Mock
	private PricesLogRepository pricesLogRepository;

	@Before
	public void setUp() {
		pricesLogService = new PricesLogServiceImpl(pricesLogRepository);
	}

	@Test
	public void testFindPrices(){
		List<PricesLog> pricesLogList = new ArrayList<>();
		pricesLogList.add(TestUtils.genereateSamplePricesLogObject());

		PageRequest pageRequest = PageRequest.of(0,4);
		PageImpl<PricesLog> p = new PageImpl<>(pricesLogList);
		given(pricesLogRepository.findAll(pageRequest)).willReturn(p);

		List<PricesLog> returnedList = pricesLogService.findPrices(pageRequest);
		assertThat(returnedList).isNotNull();
	}




}
