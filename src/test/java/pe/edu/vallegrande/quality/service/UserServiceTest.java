package pe.edu.vallegrande.quality.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.vallegrande.quality.exception.UserNotFoundException;
import pe.edu.vallegrande.quality.model.User;
import pe.edu.vallegrande.quality.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("1", "Juan Pérez", "juan@email.com", 25);
    }

    @Test
    void getAllUsers_ShouldReturnSortedUsers() {
        // Given
        List<User> users = Arrays.asList(
            new User("2", "Carlos", "carlos@email.com", 30),
            new User("1", "Ana", "ana@email.com", 25)
        );
        when(userRepository.findAll()).thenReturn(users);

        // When
        List<User> result = userService.getAllUsers();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Ana", result.get(0).getName());
        assertEquals("Carlos", result.get(1).getName());
        verify(userRepository).findAll();
    }

    @Test
    void createUser_ShouldCreateUserSuccessfully() {
        // Given
        User userToCreate = new User(null, "Juan", "juan@email.com", null);
        User savedUser = new User("1", "Juan", "juan@email.com", 0);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // When
        User result = userService.createUser(userToCreate);

        // Then
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Juan", result.getName());
        assertEquals(0, result.getAge());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        // Given
        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));

        // When
        User result = userService.getUserById("1");

        // Then
        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getName(), result.getName());
        verify(userRepository).findById("1");
    }

    @Test
    void getUserById_ShouldThrowException_WhenUserNotExists() {
        // Given
        when(userRepository.findById("999")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.getUserById("999"));
        verify(userRepository).findById("999");
    }

    @Test
    void deleteUser_ShouldDeleteSuccessfully_WhenUserExists() {
        // Given
        when(userRepository.existsById("1")).thenReturn(true);
        when(userRepository.deleteById("1")).thenReturn(true);

        // When
        boolean result = userService.deleteUser("1");

        // Then
        assertTrue(result);
        verify(userRepository).existsById("1");
        verify(userRepository).deleteById("1");
    }

    @Test
    void deleteUser_ShouldThrowException_WhenUserNotExists() {
        // Given
        when(userRepository.existsById("999")).thenReturn(false);

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser("999"));
        verify(userRepository).existsById("999");
        verify(userRepository, never()).deleteById(anyString());
    }

    @Test
    void searchUsersByName_ShouldReturnMatchingUsers() {
        // Given
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findByNameContainingIgnoreCase("Juan")).thenReturn(users);

        // When
        List<User> result = userService.searchUsersByName("Juan");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Juan Pérez", result.get(0).getName());
        verify(userRepository).findByNameContainingIgnoreCase("Juan");
    }

    @Test
    void getUserCount_ShouldReturnCorrectCount() {
        // Given
        when(userRepository.count()).thenReturn(5L);

        // When
        long result = userService.getUserCount();

        // Then
        assertEquals(5L, result);
        verify(userRepository).count();
    }
}