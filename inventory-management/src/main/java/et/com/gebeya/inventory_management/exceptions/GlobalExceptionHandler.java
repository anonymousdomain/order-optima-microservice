package et.com.gebeya.inventory_management.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {

    @ExceptionHandler(RequestStatusException.class)
    public ResponseEntity<String> RequestStatusException(RequestStatusException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND);
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false));

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<Object> handleInsufficientBalanceException(InsufficientBalanceException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(PhoneNumberAlreadyExistsException.class)
    public ResponseEntity<Object> handlePhoneNumberAlreadyExists(PhoneNumberAlreadyExistsException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NameAlreadyExistsException.class)
    public ResponseEntity<Object> handleNameAlreadyExistsException(NameAlreadyExistsException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("duplicated name ", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.CONFLICT); // HttpStatus.CONFLICT indicates a 409 error
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("BAD_REQUEST", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); // HttpStatus.BAD_REQUEST indicates a 400 error
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("data integrity error", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflictException(ConflictException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("due to conflict error", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleForbiddenException(ForbiddenException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("forbidden error exceptions", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<Object> handleServiceUnavailableException(ServiceUnavailableException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("the service didn't finished running still", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.SERVICE_UNAVAILABLE);
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Unauthorised exceptions", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<Object> buildResponseEntity(String message, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("cause ", message);
        return new ResponseEntity<>(body, status);
    }
    @ExceptionHandler(InvalidTokenSignatureException.class)
    public ResponseEntity<Object> handleInvalidTokenSignatureException(InvalidTokenSignatureException ex) {
        return buildResponseEntity("Invalid token signature. Please check your token and try again.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<Object> handleExpiredTokenException(ExpiredTokenException ex) {
        return buildResponseEntity("Token has expired. Please log in again to obtain a new token.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseEntity<Object> handleUnauthenticatedException(UnauthenticatedException ex) {
        return buildResponseEntity("Access denied. Please provide valid authentication credentials.", HttpStatus.UNAUTHORIZED);
    }

}