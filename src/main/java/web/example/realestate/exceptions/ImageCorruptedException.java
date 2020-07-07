package web.example.realestate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ImageCorruptedException extends RuntimeException {

    public ImageCorruptedException() {
        super();
    }

    public ImageCorruptedException(String message) {
        super(message);
    }

    public ImageCorruptedException(String message, Throwable cause) {
        super(message, cause);
    }

}
