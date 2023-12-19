package africa.xLogistics.exceptions;

public class ReceiverIdNotFoundError extends RuntimeException{
    public ReceiverIdNotFoundError(String message){
        super(message);
    }
}
