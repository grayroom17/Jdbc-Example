package main.java.jdbc.exception;

public class ConnectionManagerException extends RuntimeException {
    public ConnectionManagerException(Throwable throwable) {
        super(throwable);
    }
}
