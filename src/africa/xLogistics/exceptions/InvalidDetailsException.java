package africa.xLogistics.exceptions;

public class InvalidDetailsException extends UserNotFoundException {
    public InvalidDetailsException() {
        super("Login credentials is invalid");
    }
}
