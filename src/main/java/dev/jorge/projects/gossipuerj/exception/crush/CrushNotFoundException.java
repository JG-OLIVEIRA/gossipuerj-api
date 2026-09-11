package dev.jorge.projects.gossipuerj.exception.crush;

import dev.jorge.projects.gossipuerj.exception.enums.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CrushNotFoundException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.CRUSH_NOT_FOUND_EXCEPTION_MESSAGE;

    public CrushNotFoundException(String message) {
        super(ERROR.formatErrorMessage(message));
    }
}
