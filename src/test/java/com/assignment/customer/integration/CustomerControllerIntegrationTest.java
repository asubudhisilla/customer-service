package com.assignment.customer.integration;


import com.assignment.customer.CustomerServiceApplication;
import com.assignment.customer.configuration.DataSourceConfig;
import com.assignment.customer.configuration.TestDataSourceConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = CustomerServiceApplication.class,
        webEnvironment = WebEnvironment.RANDOM_PORT)
@Import({DataSourceConfig.class, TestDataSourceConfig.class})
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CustomerControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreate() throws Exception {

        String customerInfo = "{\"firstName\":\"Sa\",\"lastName\":\"test\",\"age\":\"30\",\"address\":\"Pune\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/customer")
                .accept(MediaType.APPLICATION_JSON).content(customerInfo)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        int status = response.getStatus();
        Assert.assertEquals("http response status is wrong", 200, status);
    }

    @Test
    public void testGetAllCustomers() throws Exception {

        mockMvc.perform( MockMvcRequestBuilders
                        .get("/api/customer")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
