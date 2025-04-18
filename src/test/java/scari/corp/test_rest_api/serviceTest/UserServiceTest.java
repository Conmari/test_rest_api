package scari.corp.test_rest_api.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import scari.corp.test_rest_api.exception.ResourceNotFoundException;
import scari.corp.test_rest_api.model.User;
import scari.corp.test_rest_api.repository.UserRepository;
import scari.corp.test_rest_api.service.UserService;

@Tag("unit")
public class UserServiceTest {

    private Long userId;
    private User newUser ;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = 1L;
        newUser  = new User();
        newUser .setId(userId);
        newUser .setFirstName("Дмитрий");
    }

    @Test
    public void testCreateUser_Success() {

        when(userRepository.save(any(User.class))).thenReturn(newUser );

        User createdUser = userService.createUser (newUser );

        assertNotNull(createdUser);
        assertEquals("Дмитрий", createdUser.getFirstName());
    }

    @Test
    public void testUpdateUser_UserExists() {
        User updatedUser  = new User();
        updatedUser.setFirstName("Гарри");

        when(userRepository.findById(userId)).thenReturn(Optional.of(newUser));
        when(userRepository.save(newUser )).thenReturn(newUser);

        User result = userService.updateUser (userId, updatedUser);

        assertEquals("Гарри", result.getFirstName());
    }

    @Test
    public void testGetUserById_UserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(userId);
        });

        assertEquals("Пользователь не найден, id: " + userId, exception.getMessage());
    }
    
}


