package model.exception;

import java.io.IOException;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String s, IOException e) {
        super(s,e);
    }
}
