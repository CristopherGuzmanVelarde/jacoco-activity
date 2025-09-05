package pe.edu.vallegrande.quality.util;

import org.junit.jupiter.api.Test;
import pe.edu.vallegrande.quality.model.User;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserSortingUtilTest {

    @Test
    void sortByNameIgnoreCase_ShouldSortUsersCorrectly() {
        // Given
        List<User> users = Arrays.asList(
            new User("3", "carlos", "carlos@email.com", 30),
            new User("1", "Ana", "ana@email.com", 25),
            new User("2", "BEATRIZ", "beatriz@email.com", 28)
        );

        // When
        List<User> result = UserSortingUtil.sortByNameIgnoreCase(users);

        // Then
        assertEquals(3, result.size());
        assertEquals("Ana", result.get(0).getName());
        assertEquals("BEATRIZ", result.get(1).getName());
        assertEquals("carlos", result.get(2).getName());
    }

    @Test
    void sortByNameIgnoreCase_ShouldHandleNullNames() {
        // Given
        List<User> users = Arrays.asList(
            new User("2", "Carlos", "carlos@email.com", 30),
            new User("1", null, "null@email.com", 25),
            new User("3", "Ana", "ana@email.com", 28)
        );

        // When
        List<User> result = UserSortingUtil.sortByNameIgnoreCase(users);

        // Then
        assertEquals(3, result.size());
        // Los nombres null van al final
        assertEquals("Ana", result.get(0).getName());
        assertEquals("Carlos", result.get(1).getName());
        assertNull(result.get(2).getName());
    }

    @Test
    void sortByNameIgnoreCase_ShouldReturnEmptyList_WhenInputIsNull() {
        // When
        List<User> result = UserSortingUtil.sortByNameIgnoreCase(null);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void sortByAge_ShouldSortUsersByAge() {
        // Given
        List<User> users = Arrays.asList(
            new User("1", "Ana", "ana@email.com", 30),
            new User("2", "Carlos", "carlos@email.com", 20),
            new User("3", "Beatriz", "beatriz@email.com", 25)
        );

        // When
        List<User> result = UserSortingUtil.sortByAge(users);

        // Then
        assertEquals(3, result.size());
        assertEquals(20, result.get(0).getAge());
        assertEquals(25, result.get(1).getAge());
        assertEquals(30, result.get(2).getAge());
    }

    @Test
    void sortByEmail_ShouldSortUsersByEmail() {
        // Given
        List<User> users = Arrays.asList(
            new User("1", "Carlos", "carlos@email.com", 30),
            new User("2", "Ana", "ana@email.com", 25),
            new User("3", "Beatriz", "beatriz@email.com", 28)
        );

        // When
        List<User> result = UserSortingUtil.sortByEmail(users);

        // Then
        assertEquals(3, result.size());
        assertEquals("ana@email.com", result.get(0).getEmail());
        assertEquals("beatriz@email.com", result.get(1).getEmail());
        assertEquals("carlos@email.com", result.get(2).getEmail());
    }
}