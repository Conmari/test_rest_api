//package scari.corp.test_rest_api.controller;
package scari.corp.test_rest_api.conrollerTest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import scari.corp.test_rest_api.controller.UserController;
import scari.corp.test_rest_api.model.User;
import scari.corp.test_rest_api.service.UserService;

public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetUserById_ReturnsUser () throws Exception {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Дмитрий");
        user.setLastName("Тест");

        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Дмитрий"))
                .andExpect(jsonPath("$.lastName").value("Тест"));
    }

    @Test
    public void testGetUserById_UserNotFound() throws Exception {
        when(userService.getUserById(1L)).thenReturn(null);

        mockMvc.perform(get("/users/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @AfterEach
    public void tearDown() {
        reset(userService);
    }
}
