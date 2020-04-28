package com.elementsin.insuranceproduct.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.elementsin.insuranceproduct.model.InsuranceModule;
import com.elementsin.insuranceproduct.model.value.Money;
import com.elementsin.insuranceproduct.service.InsuranceModuleService;
import com.elementsin.insuranceproduct.util.TestUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(InsuranceModuleController.class)
public class InsuranceModuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InsuranceModuleService insuranceModuleService;

    private final String CALCULATE_PRICE_API_URL = "/v1/modules/{moduleId}/price";
    private final String FIND_MODULES_API_URL = "/v1/modules";

    @Test
    public void calculatePrice_happyScenario() throws Exception {
        Money money = TestUtils.generateMoney_1200();
        given(insuranceModuleService.calculatePrice(anyString(),any(Money.class)))
                .willReturn(money);
        mockMvc.perform(MockMvcRequestBuilders.post(CALCULATE_PRICE_API_URL,"12345").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(money)))
                .andExpect(status().isOk()).andExpect(jsonPath("amount").value(money.getAmount()));
    }


    @Test
    public void calculatePrice_constraintViolation_nullAmount() throws Exception {
        Money money = TestUtils.generateMoney_1200();
        given(insuranceModuleService.calculatePrice(anyString(),any(Money.class)))
                .willReturn(money);
        mockMvc.perform(MockMvcRequestBuilders.post(CALCULATE_PRICE_API_URL,"12345").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(TestUtils.generateMoney_nullAmount())))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void calculatePrice_constraintViolation_negativeAmount() throws Exception {
        Money money = TestUtils.generateMoney_1200();
        given(insuranceModuleService.calculatePrice(anyString(),any(Money.class)))
                .willReturn(money);
        mockMvc.perform(MockMvcRequestBuilders.post(CALCULATE_PRICE_API_URL,"12345").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(TestUtils.generateMoney_negativeAmount())))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testFindModules() throws Exception {
        List<InsuranceModule> moduleList = new ArrayList<>();
        moduleList.add(TestUtils.generateSampleInsuranceModuleObject());
        given(insuranceModuleService.findModules()).willReturn(moduleList);

        mockMvc.perform(get(FIND_MODULES_API_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

}
