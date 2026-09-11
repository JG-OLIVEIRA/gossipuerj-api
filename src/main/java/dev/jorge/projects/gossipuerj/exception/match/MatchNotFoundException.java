package dev.jorge.projects.gossipuerj.exception.match;

import dev.jorge.projects.gossipuerj.exception.enums.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MatchNotFoundException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.MATCH_NOT_FOUND_EXCEPTION_MESSAGE;

    public MatchNotFoundException(String message) {
        super(ERROR.formatErrorMessage(message));
    }
}
