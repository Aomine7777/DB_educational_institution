package exceptions;

public class CustomerException  extends IllegalArgumentException{
    public CustomerException(String message){
        super(message);
    }
}
