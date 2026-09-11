package dev.jorge.projects.gossipuerj.exception.match;

import dev.jorge.projects.gossipuerj.exception.enums.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MatchAlreadyRespondedException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.MATCH_ALREADY_RESPONDED_EXCEPTION_MESSAGE;

    public MatchAlreadyRespondedException(String message) {
        super(ERROR.formatErrorMessage(message));
    }
}
