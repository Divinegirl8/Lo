package africa.xLogistics.exceptions;

public class LowAmountError extends RuntimeException{
    public LowAmountError(String message){
        super(message);
    }
}
