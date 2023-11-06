package backend.com.parcelsystem.Exception;

public class EntityExistingException extends RuntimeException {
    
    public EntityExistingException(String message) {
        super(message);
    }
}
