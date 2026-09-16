package dev.jorge.projects.gossipuerj.exception.user;

import dev.jorge.projects.gossipuerj.exception.enums.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyVerifiedException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.USER_ALREADY_VERIFIED_MESSAGE;

    public UserAlreadyVerifiedException(String message) {
        super(ERROR.formatErrorMessage(message));
    }
}
