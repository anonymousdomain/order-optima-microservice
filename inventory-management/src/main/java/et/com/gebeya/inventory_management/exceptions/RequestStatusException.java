package et.com.gebeya.inventory_management.exceptions;

public class RequestStatusException extends RuntimeException {
    public RequestStatusException(String s) {
        super("Request is not in PENDING status either it is approved or already declined");
    }
}