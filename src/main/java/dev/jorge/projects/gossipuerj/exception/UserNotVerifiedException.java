package dev.jorge.projects.gossipuerj.exception;

import dev.jorge.projects.gossipuerj.exception.enums.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserNotVerifiedException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.USER_NOT_VERIFIED_MESSAGE;

    public UserNotVerifiedException(String message) {
        super(ERROR.formatErrorMessage(message));
    }
}
