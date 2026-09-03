package exception;

/*
InvalidCsvFormatException.java
Member 3 , Custom Exception Handling
 */
public class InvalidCsvFormatException extends Exception {

    public InvalidCsvFormatException(String message) {
        super(message);
    }

    public InvalidCsvFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
