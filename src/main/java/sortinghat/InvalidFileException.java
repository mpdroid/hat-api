package sortinghat;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "not a valid text file")
class InvalidFileException extends RuntimeException {

    InvalidFileException(String message) {
        super(message);
    }

}
