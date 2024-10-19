package com.softvision.usersmicroservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softvision.usersmicroservice.dto.UserDTO;
import com.softvision.usersmicroservice.entity.User;
import com.softvision.usersmicroservice.exceptions.UserNotFoundException;
import com.softvision.usersmicroservice.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll(); // Clear the database before each test
    }

    @Test
    public void testSaveUser() {
        UserDTO newUser = new UserDTO();
        newUser.setFirstName("Ada");
        newUser.setLastName("Zamfir");
        newUser.setEmail("adaz@example.com");
        newUser.setPassword("pass12345");

        UserDTO savedUser = userService.save(newUser);

        assertNotNull(savedUser);
        assertEquals("Ada", savedUser.getFirstName());
        assertEquals("Zamfir", savedUser.getLastName());
        assertEquals("adaz@example.com", savedUser.getEmail());
    }

    @Test
    public void testFindAllUsers() {
        UserDTO newUser1 = new UserDTO();
        newUser1.setFirstName("John");
        newUser1.setLastName("Doe");
        newUser1.setEmail("john@example.com");
        newUser1.setPassword("password123");

        UserDTO newUser2 = new UserDTO();
        newUser2.setFirstName("Jane");
        newUser2.setLastName("Doe");
        newUser2.setEmail("jane@example.com");
        newUser2.setPassword("password456");

        userService.save(newUser1);
        userService.save(newUser2);

        List<UserDTO> users = userService.findAll();
        assertEquals(2, users.size());
    }

    @Test
    public void testFindUserByEmailAndPassword() {
        UserDTO newUser = new UserDTO();
        newUser.setFirstName("Ada");
        newUser.setLastName("Zamfir");
        newUser.setEmail("adaz@example.com");
        newUser.setPassword("pass12345");

        userService.save(newUser);

        User foundUser = userService.findUserByEmailAndPassword("adaz@example.com", "pass12345");
        assertNotNull(foundUser);
        assertEquals("adaz@example.com", foundUser.getEmail());
    }

    @Test
    public void testUpdateUserByEmailAndPassword() {
        UserDTO newUser = new UserDTO();
        newUser.setFirstName("Ada");
        newUser.setLastName("Zamfir");
        newUser.setEmail("adaz@example.com");
        newUser.setPassword("pass12345");

        userService.save(newUser);

        UserDTO updateUser = new UserDTO();
        updateUser.setFirstName("Adele");
        updateUser.setLastName("Zamfir");
        updateUser.setEmail("adaz@example.com");
        updateUser.setPassword("pass12345"); 

        UserDTO updatedUser = userService.updateUserByEmailAndPassword("adaz@example.com", "pass12345", updateUser);

        assertNotNull(updatedUser);
        assertEquals("Adele", updatedUser.getFirstName());
    }

    @Test
    public void testDeleteUserById() {
        UserDTO newUser = new UserDTO();
        newUser.setFirstName("Ada");
        newUser.setLastName("Zamfir");
        newUser.setEmail("adaz@example.com");
        newUser.setPassword("pass12345");

        UserDTO savedUser = userService.save(newUser);
        Long userId = userService.findUserIdByEmailAndPassword("adaz@example.com", "pass12345"); // Get the user ID

        userService.deleteUserById(userId);

        Optional<User> foundUser = userRepository.findById(userId);
        assertFalse(foundUser.isPresent()); // User should no longer exist
    }
    @Test
    public void testDeleteUserNotFound() {
            // Attempting to delete a non-existing user
            Exception exception = assertThrows(UserNotFoundException.class, () -> {
                userService.deleteUserById(999L); // Assuming this ID does not exist
            });
        
            assertEquals("User not found", exception.getMessage());
        
    }

    @Test
    public void testFindAllUsersAgain() {
        UserDTO newUser1 = new UserDTO();
        newUser1.setFirstName("John");
        newUser1.setLastName("Doe");
        newUser1.setEmail("john@example.com");
        newUser1.setPassword("password123");

        UserDTO newUser2 = new UserDTO();
        newUser2.setFirstName("Jane");
        newUser2.setLastName("Doe");
        newUser2.setEmail("jane@example.com");
        newUser2.setPassword("password456");

        userService.save(newUser1);
        userService.save(newUser2);

        List<UserDTO> users = userService.findAll();
        assertEquals(2, users.size());
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
                .andExpect(status().isCreated());
    }


@Test
    public void testUpdateUserNotFound() throws Exception {
        UserDTO updateInfo = new UserDTO();
        updateInfo.setFirstName("Nonexistent");
        updateInfo.setLastName("User");
        updateInfo.setEmail("nonexistent@example.com");
        updateInfo.setPassword("nopassword");

        mockMvc.perform(put("/users/update")
                .param("email", "wrong@example.com")
                .param("password", "wrongpassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateInfo)))
                .andExpect(status().isNotFound());
    }

@Test
    public void testUpdateUserWithInvalidData() throws Exception {
        UserDTO updateInfo = new UserDTO();
        updateInfo.setFirstName("Jane");
        updateInfo.setLastName("Doe");
        updateInfo.setEmail(null); // Invalid email

        mockMvc.perform(put("/users/update")
                .param("email", "valid@example.com")
                .param("password", "validpassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateInfo)))
                .andExpect(status().isBadRequest());
    }

}
