
package pe.edu.vallegrande.quality.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.quality.constants.ApiConstants;
import pe.edu.vallegrande.quality.dto.ApiResponse;
import pe.edu.vallegrande.quality.dto.UserRequestDto;
import pe.edu.vallegrande.quality.dto.UserResponseDto;
import pe.edu.vallegrande.quality.mapper.UserMapper;
import pe.edu.vallegrande.quality.model.User;
import pe.edu.vallegrande.quality.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(ApiConstants.USERS_PATH)
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    private final UserMapper userMapper;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getAllUsers() {
        log.info("Solicitud para obtener todos los usuarios");
        
        List<User> users = userService.getAllUsers();
        List<UserResponseDto> userDtos = userMapper.toResponseDtoList(users);
        
        return ResponseEntity.ok(
            ApiResponse.success(ApiConstants.USERS_RETRIEVED, userDtos)
        );
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(
            @Valid @RequestBody UserRequestDto userRequest) {
        log.info("Solicitud para crear usuario: {}", userRequest.getName());
        
        User user = userMapper.toEntity(userRequest);
        User createdUser = userService.createUser(user);
        UserResponseDto responseDto = userMapper.toResponseDto(createdUser);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(ApiConstants.USER_CREATED, responseDto));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserById(@PathVariable String id) {
        log.info("Solicitud para obtener usuario con ID: {}", id);
        
        User user = userService.getUserById(id);
        UserResponseDto responseDto = userMapper.toResponseDto(user);
        
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteUser(@PathVariable String id) {
        log.info("Solicitud para eliminar usuario con ID: {}", id);
        
        userService.deleteUser(id);
        
        return ResponseEntity.ok(ApiResponse.success(ApiConstants.USER_DELETED, null));
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> searchUsersByName(
            @RequestParam String name) {
        log.info("Solicitud para buscar usuarios por nombre: {}", name);
        
        List<User> users = userService.searchUsersByName(name);
        List<UserResponseDto> userDtos = userMapper.toResponseDtoList(users);
        
        return ResponseEntity.ok(
            ApiResponse.success("Búsqueda completada", userDtos)
        );
    }
    
    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> getUserCount() {
        log.info("Solicitud para obtener el conteo de usuarios");
        
        long count = userService.getUserCount();
        
        return ResponseEntity.ok(
            ApiResponse.success("Conteo obtenido", count)
        );
    }
}
