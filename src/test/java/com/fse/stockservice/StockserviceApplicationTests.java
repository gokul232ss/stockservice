package com.fse.stockservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.stockservice.common.CommonConstant;
import com.fse.stockservice.model.request.StockRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.util.Calendar;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("junit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StockserviceApplicationTests {

    @Autowired
    private WebApplicationContext webAppContext;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper ob;

    @BeforeEach
    public void initialize() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webAppContext)
                .build();
    }

    @Test
    @Order(1)
    public void testA1ForConnect() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/stock/getping")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(equalTo("Success")));

    }

    @Test
    @Order(2)
    public void testA2CreateStock() throws Exception {
        StockRequest stock = new StockRequest();
        stock.setStockName("newStock");
        stock.setPrice(54.43);
        String requestJson = ob.writeValueAsString(stock);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/stock/add/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    @Order(3)
    public void testA3CreateStockBadRequest() throws Exception {
        StockRequest stock = new StockRequest();
        stock.setStockName("newStock");
        stock.setPrice(0);
        String requestJson = ob.writeValueAsString(stock);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/stock/add/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    @Order(4)
    public void testA4ForFindStock() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        String startDate = CommonConstant.formatterDB.format(new Date(calendar.getTimeInMillis()));
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String endDate = CommonConstant.formatterDB.format(new Date(calendar.getTimeInMillis()));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/stock/get/1/" + startDate + "/" + endDate)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @Order(5)
    public void testA5ForFindStockNotFound() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        String startDate = CommonConstant.formatterDB.format(new Date(calendar.getTimeInMillis()));
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String endDate = CommonConstant.formatterDB.format(new Date(calendar.getTimeInMillis()));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/stock/get/10/" + startDate + "/" + endDate)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

}
