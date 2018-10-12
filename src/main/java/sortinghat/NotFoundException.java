package sortinghat;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Record not found")
class NotFoundException extends RuntimeException {

    NotFoundException(String message) {
        super(message);
    }

}
