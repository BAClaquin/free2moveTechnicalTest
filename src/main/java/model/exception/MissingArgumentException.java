package model.exception;

public class MissingArgumentException extends RuntimeException  {
    public MissingArgumentException(String s) {
        super(s);
    }
}
