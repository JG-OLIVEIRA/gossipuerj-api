package dev.jorge.projects.gossipuerj.exception.match;

import dev.jorge.projects.gossipuerj.exception.enums.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class MatchNotAllowedException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.MATCH_NOT_ALLOWED_EXCEPTION_MESSAGE;

    public MatchNotAllowedException(String message) {
        super(ERROR.formatErrorMessage(message));
    }
}
