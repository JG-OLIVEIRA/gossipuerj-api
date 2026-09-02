package dev.jorge.projects.gossipuerj.exception;

import dev.jorge.projects.gossipuerj.enums.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserVerificationCodeExpiredException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.USER_VERIFICATION_CODE_EXPIRED_MESSAGE;

    public UserVerificationCodeExpiredException(String message) {
        super(ERROR.formatErrorMessage(message));
    }
}
