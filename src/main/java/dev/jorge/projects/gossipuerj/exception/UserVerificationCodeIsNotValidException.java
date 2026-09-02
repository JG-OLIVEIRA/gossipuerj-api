package dev.jorge.projects.gossipuerj.exception;

import dev.jorge.projects.gossipuerj.enums.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserVerificationCodeIsNotValidException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.USER_VERIFICATION_CODE_IS_NOT_VALID_MESSAGE;

    public UserVerificationCodeIsNotValidException(String message) {
        super(ERROR.formatErrorMessage(message));
    }
}
