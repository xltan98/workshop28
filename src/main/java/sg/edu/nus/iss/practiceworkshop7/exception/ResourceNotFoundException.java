package sg.edu.nus.iss.practiceworkshop7.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(){
        super();
    }
    public ResourceNotFoundException(String message){
        super(message);
    }

    public ResourceNotFoundException(String message,Throwable cause){
        super(message,cause);
    }
      public ResourceNotFoundException(Throwable cause){
        super(cause);
    }
}
