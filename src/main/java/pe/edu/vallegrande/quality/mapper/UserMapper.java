package pe.edu.vallegrande.quality.mapper;

import org.springframework.stereotype.Component;
import pe.edu.vallegrande.quality.dto.UserRequestDto;
import pe.edu.vallegrande.quality.dto.UserResponseDto;
import pe.edu.vallegrande.quality.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    
    public User toEntity(UserRequestDto dto) {
        if (dto == null) {
            return null;
        }
        return new User(null, dto.getName(), dto.getEmail(), dto.getAge());
    }
    
    public UserResponseDto toResponseDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail(), user.getAge());
    }
    
    public List<UserResponseDto> toResponseDtoList(List<User> users) {
        if (users == null) {
            return List.of();
        }
        return users.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}