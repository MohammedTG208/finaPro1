package spring.boot.fainalproject.API;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
