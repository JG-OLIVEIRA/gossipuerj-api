package dev.jorge.projects.gossipuerj.exception.comment;

import dev.jorge.projects.gossipuerj.exception.enums.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommentNotFoundException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.USER_NOT_FOUND_MESSAGE;

    public CommentNotFoundException(String message) {
        super(ERROR.formatErrorMessage(message));
    }
}
