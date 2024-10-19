package com.softvision.usersmicroservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softvision.usersmicroservice.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        // Any setup logic can go here (if necessary)
    }

    @Test
    public void testAddNewUser() throws Exception {
        UserDTO newUser = new UserDTO();
        newUser.setFirstName("Ada");
        newUser.setLastName("Zamfir");
        newUser.setEmail("adaz@example.com");
        newUser.setPassword("pass12345");

        mockMvc.perform(post("/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

}
