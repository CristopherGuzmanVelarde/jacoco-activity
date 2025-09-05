
package pe.edu.vallegrande.quality.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.quality.model.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class UserRepository {
    
    private final Map<String, User> users = new ConcurrentHashMap<>();
    
    public List<User> findAll() {
        log.debug("Obteniendo todos los usuarios. Total: {}", users.size());
        return new ArrayList<>(users.values());
    }
    
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        
        if (user.getId() == null || user.getId().isEmpty()) {
            user.setId(UUID.randomUUID().toString());
        }
        
        users.put(user.getId(), user);
        log.debug("Usuario guardado con ID: {}", user.getId());
        return user;
    }
    
    public Optional<User> findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }
        
        User user = users.get(id);
        log.debug("Buscando usuario con ID: {}. Encontrado: {}", id, user != null);
        return Optional.ofNullable(user);
    }
    
    public boolean deleteById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        
        boolean removed = users.remove(id) != null;
        log.debug("Eliminando usuario con ID: {}. Eliminado: {}", id, removed);
        return removed;
    }
    
    public List<User> findByNameContainingIgnoreCase(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return users.values().stream()
                .filter(user -> user.getName() != null && 
                        user.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    public boolean existsById(String id) {
        return id != null && users.containsKey(id);
    }
    
    public long count() {
        return users.size();
    }
}
