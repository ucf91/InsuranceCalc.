package com.elementsin.insuranceproduct.controller;

import static com.elementsin.insuranceproduct.util.TestUtils.genereateSamplePricesLogObject;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.elementsin.insuranceproduct.model.PricesLog;
import com.elementsin.insuranceproduct.service.PricesLogService;

@RunWith(SpringRunner.class)
@WebMvcTest(PricesLogController.class)
public class PricesLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PricesLogService pricesLogService;

    private final String FIND_PRICES_API_URL = "/v1/prices";

    @Test
    public void testFindCalculatedPrice() throws Exception {
        List<PricesLog> pricesLogList = new ArrayList<>();
        pricesLogList.add(genereateSamplePricesLogObject());

        given(pricesLogService.findPrices(any()))
                .willReturn(pricesLogList);

        mockMvc.perform(get(FIND_PRICES_API_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

}
