package africa.xLogistics.exceptions;

public class SenderIdNotFoundError extends RuntimeException{
    public SenderIdNotFoundError(String message){
        super(message);
    }
}
