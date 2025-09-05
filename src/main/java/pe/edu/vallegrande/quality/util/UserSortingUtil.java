package pe.edu.vallegrande.quality.util;

import pe.edu.vallegrande.quality.model.User;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class UserSortingUtil {
    
    private UserSortingUtil() {
        // Utility class
    }
    
    public static List<User> sortByNameIgnoreCase(List<User> users) {
        if (users == null) {
            return List.of();
        }
        
        return users.stream()
                .sorted(Comparator.comparing(
                    User::getName,
                    Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
                ))
                .collect(Collectors.toList());
    }
    
    public static List<User> sortByAge(List<User> users) {
        if (users == null) {
            return List.of();
        }
        
        return users.stream()
                .sorted(Comparator.comparing(
                    User::getAge,
                    Comparator.nullsLast(Comparator.naturalOrder())
                ))
                .collect(Collectors.toList());
    }
    
    public static List<User> sortByEmail(List<User> users) {
        if (users == null) {
            return List.of();
        }
        
        return users.stream()
                .sorted(Comparator.comparing(
                    User::getEmail,
                    Comparator.nullsLast(Comparator.naturalOrder())
                ))
                .collect(Collectors.toList());
    }
}