package com.ltp.globalsuperstore;

import com.ltp.globalsuperstore.controller.StoreController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
class GlobalSuperstoreApplicationTests {

    @Autowired
    private StoreController controller;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertNotNull(controller);
        assertNotNull(mockMvc);
    }

    @Test
    public void testShowItem() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/?123");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("form"))
                .andExpect(model().attributeExists("item"));
    }

    @Test
    public void TestSuccessfulSubmitItem() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/submitItem")
                .param("category", "Furniture")
                .param("name", "Lama")
                .param("price", "400")
                .param("discount", "40");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/inventory"));
    }

    @Test
    public void testUnsuccessfulSubmission() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/submitItem")
                .param("category", "  ")
                .param("name", "  ")
                .param("price", "-5")
                .param("discount", "-1");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("form"));
    }

    @Test
    public void testGetItem() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/inventory");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("inventory"))
                .andExpect(model().attributeExists("items"));

    }
}