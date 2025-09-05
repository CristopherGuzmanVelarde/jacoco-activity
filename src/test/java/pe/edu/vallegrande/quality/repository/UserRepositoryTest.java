package pe.edu.vallegrande.quality.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.edu.vallegrande.quality.model.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private UserRepository userRepository;
    private User testUser;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        testUser = new User(null, "Juan Pérez", "juan@email.com", 25);
    }

    @Test
    void save_ShouldGenerateIdAndSaveUser() {
        // When
        User savedUser = userRepository.save(testUser);

        // Then
        assertNotNull(savedUser.getId());
        assertEquals(testUser.getName(), savedUser.getName());
        assertEquals(testUser.getEmail(), savedUser.getEmail());
        assertEquals(testUser.getAge(), savedUser.getAge());
    }

    @Test
    void save_ShouldThrowException_WhenUserIsNull() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> userRepository.save(null));
    }

    @Test
    void findById_ShouldReturnUser_WhenUserExists() {
        // Given
        User savedUser = userRepository.save(testUser);

        // When
        Optional<User> result = userRepository.findById(savedUser.getId());

        // Then
        assertTrue(result.isPresent());
        assertEquals(savedUser.getId(), result.get().getId());
        assertEquals(savedUser.getName(), result.get().getName());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenUserNotExists() {
        // When
        Optional<User> result = userRepository.findById("nonexistent");

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenIdIsNull() {
        // When
        Optional<User> result = userRepository.findById(null);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void findAll_ShouldReturnAllUsers() {
        // Given
        User user1 = userRepository.save(new User(null, "Ana", "ana@email.com", 20));
        User user2 = userRepository.save(new User(null, "Carlos", "carlos@email.com", 30));

        // When
        List<User> result = userRepository.findAll();

        // Then
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(u -> u.getName().equals("Ana")));
        assertTrue(result.stream().anyMatch(u -> u.getName().equals("Carlos")));
    }

    @Test
    void deleteById_ShouldReturnTrue_WhenUserExists() {
        // Given
        User savedUser = userRepository.save(testUser);

        // When
        boolean result = userRepository.deleteById(savedUser.getId());

        // Then
        assertTrue(result);
        assertFalse(userRepository.findById(savedUser.getId()).isPresent());
    }

    @Test
    void deleteById_ShouldReturnFalse_WhenUserNotExists() {
        // When
        boolean result = userRepository.deleteById("nonexistent");

        // Then
        assertFalse(result);
    }

    @Test
    void existsById_ShouldReturnTrue_WhenUserExists() {
        // Given
        User savedUser = userRepository.save(testUser);

        // When
        boolean result = userRepository.existsById(savedUser.getId());

        // Then
        assertTrue(result);
    }

    @Test
    void existsById_ShouldReturnFalse_WhenUserNotExists() {
        // When
        boolean result = userRepository.existsById("nonexistent");

        // Then
        assertFalse(result);
    }

    @Test
    void findByNameContainingIgnoreCase_ShouldReturnMatchingUsers() {
        // Given
        userRepository.save(new User(null, "Juan Pérez", "juan@email.com", 25));
        userRepository.save(new User(null, "Ana García", "ana@email.com", 20));
        userRepository.save(new User(null, "Carlos Ruiz", "carlos@email.com", 30));

        // When
        List<User> result = userRepository.findByNameContainingIgnoreCase("juan");

        // Then
        assertEquals(1, result.size());
        assertEquals("Juan Pérez", result.get(0).getName());
    }

    @Test
    void count_ShouldReturnCorrectCount() {
        // Given
        userRepository.save(new User(null, "User1", "user1@email.com", 25));
        userRepository.save(new User(null, "User2", "user2@email.com", 30));

        // When
        long count = userRepository.count();

        // Then
        assertEquals(2, count);
    }
}