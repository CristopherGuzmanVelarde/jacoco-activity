package pe.edu.vallegrande.quality.constants;

public final class ApiConstants {
    
    // API Paths
    public static final String API_BASE_PATH = "/api/v1";
    public static final String USERS_PATH = API_BASE_PATH + "/users";
    
    // Messages
    public static final String USER_NOT_FOUND = "Usuario no encontrado";
    public static final String USER_CREATED = "Usuario creado exitosamente";
    public static final String USER_DELETED = "Usuario eliminado exitosamente";
    public static final String USERS_RETRIEVED = "Usuarios obtenidos exitosamente";
    
    // Default values
    public static final int DEFAULT_AGE = 0;
    public static final int MIN_AGE = 0;
    public static final int MAX_AGE = 120;
    
    private ApiConstants() {
        // Utility class
    }
}