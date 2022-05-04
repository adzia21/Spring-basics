package com.ltp.contacts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.ltp.contacts.pojo.Contact;
import com.ltp.contacts.repository.ContactRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class ContactsApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Contact[] contacts = new Contact[]{
            new Contact("1", "Jon Snow", "6135342524"),
            new Contact("2", "Tyrion Lannister", "4145433332"),
            new Contact("3", "The Hound", "3452125631"),
    };

    @BeforeEach
    void setup() {
        for (int i = 0; i < contacts.length; i++) {
            contactRepository.saveContact(contacts[i]);
        }
    }

    @AfterEach
    void clear() {
        contactRepository.getContacts().clear();
    }


    @Test
    public void getContactByIdTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/contact/1");
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(contacts[0].getName()))
                .andExpect(jsonPath("$.phoneNumber").value(contacts[0].getPhoneNumber()));
    }

    @Test
    public void getAllContactsTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/contact/all");
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(contacts.length))
                .andExpect(jsonPath("$.[?(@.id == \"2\" && @.name == \"Tyrion Lannister\" && @.phoneNumber == \"4145433332\")]").exists());
    }

    @Test
    public void validContactCreation() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/contact")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Contact("Rayan", "456789156")));

        mockMvc.perform(requestBuilder).andExpect(status().isCreated());

    }

    @Test
    public void invalidContactCreation() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/contact")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Contact("    ", "    ")));

        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
    }

    @Test
    public void contactNotFoundTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/contact/4");
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
    }


}

