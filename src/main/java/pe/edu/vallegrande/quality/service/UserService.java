
package pe.edu.vallegrande.quality.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.quality.constants.ApiConstants;
import pe.edu.vallegrande.quality.exception.UserNotFoundException;
import pe.edu.vallegrande.quality.model.User;
import pe.edu.vallegrande.quality.repository.UserRepository;
import pe.edu.vallegrande.quality.util.UserSortingUtil;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    
    public List<User> getAllUsers() {
        log.info("Obteniendo todos los usuarios");
        List<User> users = userRepository.findAll();
        return UserSortingUtil.sortByNameIgnoreCase(users);
    }
    
    public User createUser(User user) {
        log.info("Creando nuevo usuario: {}", user.getName());
        
        if (user.getAge() == null) {
            user.setAge(ApiConstants.DEFAULT_AGE);
        }
        
        User savedUser = userRepository.save(user);
        log.info("Usuario creado exitosamente con ID: {}", savedUser.getId());
        return savedUser;
    }
    
    public User getUserById(String id) {
        log.info("Buscando usuario con ID: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ApiConstants.USER_NOT_FOUND));
    }
    
    public Optional<User> findUserById(String id) {
        log.debug("Buscando usuario opcional con ID: {}", id);
        return userRepository.findById(id);
    }
    
    public boolean deleteUser(String id) {
        log.info("Eliminando usuario con ID: {}", id);
        
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(ApiConstants.USER_NOT_FOUND);
        }
        
        boolean deleted = userRepository.deleteById(id);
        if (deleted) {
            log.info("Usuario eliminado exitosamente con ID: {}", id);
        }
        return deleted;
    }
    
    public List<User> searchUsersByName(String name) {
        log.info("Buscando usuarios por nombre: {}", name);
        List<User> users = userRepository.findByNameContainingIgnoreCase(name);
        return UserSortingUtil.sortByNameIgnoreCase(users);
    }
    
    public long getUserCount() {
        return userRepository.count();
    }
}
