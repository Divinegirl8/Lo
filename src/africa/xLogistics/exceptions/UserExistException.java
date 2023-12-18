package africa.xLogistics.exceptions;

public class UserExistException  extends UserNotFoundException {
    public UserExistException(String message) {
        super(message);
    }
}
