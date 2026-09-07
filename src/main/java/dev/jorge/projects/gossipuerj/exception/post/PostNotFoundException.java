package dev.jorge.projects.gossipuerj.exception.post;

import dev.jorge.projects.gossipuerj.exception.enums.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.POST_NOT_FOUND_EXCEPTION_MESSAGE;

    public PostNotFoundException(String message) {
        super(ERROR.formatErrorMessage(message));
    }
}
