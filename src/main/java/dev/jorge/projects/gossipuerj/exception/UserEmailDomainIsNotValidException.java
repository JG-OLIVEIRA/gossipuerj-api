package dev.jorge.projects.gossipuerj.exception;

import dev.jorge.projects.gossipuerj.enums.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserEmailDomainIsNotValidException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.USER_EMAIL_DOMAIN_NOT_ALLOWED_MESSAGE;

    public UserEmailDomainIsNotValidException(String message) {
        super(ERROR.formatErrorMessage(message));
    }
}
