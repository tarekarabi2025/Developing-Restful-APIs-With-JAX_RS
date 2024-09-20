package exception;

import java.io.Serial;

public class DataNotFoundException extends RuntimeException{


    @Serial
    private static final long serialVersionUID = 0;

    public DataNotFoundException(String message) {
        super(message);
    }
}
