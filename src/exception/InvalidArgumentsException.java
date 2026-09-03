package exception;

/*
  InvalidArgumentsException.java
  Team Member 3 , Custom Exception Handling
  Thrown whenever the command line arguments supplied by the user
  This is a checked exception 
 */

public class InvalidArgumentsException extends Exception {

    public InvalidArgumentsException(String message) {
        super(message);
    }
}
