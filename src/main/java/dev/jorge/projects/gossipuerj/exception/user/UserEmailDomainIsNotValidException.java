package dev.jorge.projects.gossipuerj.exception.user;

import dev.jorge.projects.gossipuerj.exception.enums.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserEmailDomainIsNotValidException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.USER_EMAIL_DOMAIN_NOT_ALLOWED_MESSAGE;

    public UserEmailDomainIsNotValidException(String message) {
        super(ERROR.formatErrorMessage(message));
    }
}
