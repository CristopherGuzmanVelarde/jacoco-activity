package pe.edu.vallegrande.quality.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pe.edu.vallegrande.quality.dto.UserRequestDto;
import pe.edu.vallegrande.quality.exception.UserNotFoundException;
import pe.edu.vallegrande.quality.mapper.UserMapper;
import pe.edu.vallegrande.quality.model.User;
import pe.edu.vallegrande.quality.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;
    private UserRequestDto userRequestDto;

    @BeforeEach
    void setUp() {
        testUser = new User("1", "Juan Pérez", "juan@email.com", 25);
        userRequestDto = new UserRequestDto();
        userRequestDto.setName("Juan Pérez");
        userRequestDto.setEmail("juan@email.com");
        userRequestDto.setAge(25);
    }

    @Test
    void getAllUsers_ShouldReturnUsers() throws Exception {
        // Given
        List<User> users = Arrays.asList(testUser);
        when(userService.getAllUsers()).thenReturn(users);
        when(userMapper.toResponseDtoList(users)).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Usuarios obtenidos exitosamente"));
    }

    @Test
    void createUser_ShouldCreateUserSuccessfully() throws Exception {
        // Given
        when(userMapper.toEntity(any(UserRequestDto.class))).thenReturn(testUser);
        when(userService.createUser(any(User.class))).thenReturn(testUser);
        when(userMapper.toResponseDto(any(User.class))).thenReturn(null);

        // When & Then
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Usuario creado exitosamente"));
    }

    @Test
    void createUser_ShouldReturnBadRequest_WhenValidationFails() throws Exception {
        // Given
        UserRequestDto invalidUser = new UserRequestDto();
        invalidUser.setName(""); // Invalid name
        invalidUser.setEmail("invalid-email"); // Invalid email

        // When & Then
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Errores de validación"));
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() throws Exception {
        // Given
        when(userService.getUserById("1")).thenReturn(testUser);
        when(userMapper.toResponseDto(testUser)).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void getUserById_ShouldReturnNotFound_WhenUserNotExists() throws Exception {
        // Given
        when(userService.getUserById("999")).thenThrow(new UserNotFoundException("Usuario no encontrado"));

        // When & Then
        mockMvc.perform(get("/api/v1/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Usuario no encontrado"));
    }

    @Test
    void deleteUser_ShouldDeleteSuccessfully() throws Exception {
        // Given
        when(userService.deleteUser("1")).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Usuario eliminado exitosamente"));
    }

    @Test
    void searchUsersByName_ShouldReturnMatchingUsers() throws Exception {
        // Given
        List<User> users = Arrays.asList(testUser);
        when(userService.searchUsersByName("Juan")).thenReturn(users);
        when(userMapper.toResponseDtoList(users)).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/api/v1/users/search")
                .param("name", "Juan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Búsqueda completada"));
    }

    @Test
    void getUserCount_ShouldReturnCount() throws Exception {
        // Given
        when(userService.getUserCount()).thenReturn(5L);

        // When & Then
        mockMvc.perform(get("/api/v1/users/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Conteo obtenido"))
                .andExpect(jsonPath("$.data").value(5));
    }
}