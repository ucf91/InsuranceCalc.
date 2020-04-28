package com.elementsin.insuranceproduct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.elementsin.insuranceproduct.dto.InsuranceModuleDto;
import com.elementsin.insuranceproduct.dto.PricesLogDto;
import com.elementsin.insuranceproduct.model.value.Money;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class IntegrationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	private final String CALCULATE_PRICE_API_URL = "/v1/modules/{moduleId}/price";
	private final String FIND_MODULES_API_URL = "/v1/modules";
	private final String FIND_PRICES_API_URL = "/v1/prices";

	private List<InsuranceModuleDto> retrievedModulesList;
	@Before
	public void setup(){
		ResponseEntity<InsuranceModuleDto[]> response  = restTemplate.getForEntity(FIND_MODULES_API_URL, InsuranceModuleDto[].class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		this.retrievedModulesList = Arrays.asList(response.getBody());
	}

	@Test
	public void testFindModules(){
		assertThat(this.retrievedModulesList).isNotNull();
		assertThat(this.retrievedModulesList.size()).isGreaterThan(0);
	}

	@Test
	public void testCalculatePrice_and_findPrices_happyScenario() throws InterruptedException {
		Money selectedCoverage = Money.of(600);
		ResponseEntity<Money> response = restTemplate.postForEntity(CALCULATE_PRICE_API_URL,selectedCoverage,Money.class,this.retrievedModulesList.get(0).getId());

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getAmount().compareTo(selectedCoverage.getAmount().multiply(BigDecimal.valueOf(this.retrievedModulesList.get(0).getRisk())))).isEqualTo(0);
		// wait until the fire and forget method complete insertion
		Thread.sleep(3000);
		// assert that the calculated price has been inserted successfully
		ResponseEntity<PricesLogDto[]> findPricesResponse  = restTemplate.getForEntity(FIND_PRICES_API_URL, PricesLogDto[].class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(findPricesResponse.getBody()[0].getCalculatedPrice().getAmount().compareTo(response.getBody().getAmount())).isEqualTo(0);

	}

}
