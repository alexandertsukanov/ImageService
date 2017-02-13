package app.exceptions;

public class FileNotSavedException extends Exception {

    public FileNotSavedException() {
    }

    public FileNotSavedException(String message) {
        super(message);
    }

}
