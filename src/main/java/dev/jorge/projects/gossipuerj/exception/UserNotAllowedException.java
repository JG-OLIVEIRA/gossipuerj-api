package dev.jorge.projects.gossipuerj.exception;

import dev.jorge.projects.gossipuerj.enums.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserNotAllowedException extends  RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.USER_NOT_ALLOWED_MESSAGE;

    public UserNotAllowedException(String message) {
        super(ERROR.formatErrorMessage(message));
    }
}
