package dev.jorge.projects.gossipuerj.handler;

import dev.jorge.projects.gossipuerj.dto.response.user.ExceptionResponse;
import dev.jorge.projects.gossipuerj.exception.comment.CommentNotFoundException;
import dev.jorge.projects.gossipuerj.exception.course.CourseNotFoundException;
import dev.jorge.projects.gossipuerj.exception.crush.CrushNotFoundException;
import dev.jorge.projects.gossipuerj.exception.match.MatchAlreadyRespondedException;
import dev.jorge.projects.gossipuerj.exception.match.MatchNotAllowedException;
import dev.jorge.projects.gossipuerj.exception.post.PostNotFoundException;
import dev.jorge.projects.gossipuerj.exception.user.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler({
            UserAlreadyExistsException.class,
            MatchAlreadyRespondedException.class
    })
    public final ResponseEntity<ExceptionResponse> handlerConflictException(UserAlreadyExistsException ex, WebRequest webRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(),
                webRequest.getDescription(false),
                new Date()
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            PostNotFoundException.class,
            CourseNotFoundException.class,
            CommentNotFoundException.class,
            CrushNotFoundException.class
    })
    public final ResponseEntity<ExceptionResponse> handlerUserNotFoundException(UserNotFoundException ex, WebRequest webRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(),
                webRequest.getDescription(false),
                new Date()
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<ExceptionResponse> handlerBadCredentialsException(BadCredentialsException ex, WebRequest webRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(),
                webRequest.getDescription(false),
                new Date()
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({
            UserNotVerifiedException.class,
            MatchNotAllowedException.class
    })
    public final ResponseEntity<ExceptionResponse> handlerUserNotVerifiedException(UserNotVerifiedException ex, WebRequest webRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(),
                webRequest.getDescription(false),
                new Date()
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
            UserVerificationCodeIsNotValidException.class,
            UserVerificationCodeExpiredException.class
    })
    public final ResponseEntity<ExceptionResponse> handlerUserVerificationCodeIsNotValidException(UserVerificationCodeIsNotValidException ex, WebRequest webRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(),
                webRequest.getDescription(false),
                new Date()
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserEmailDomainIsNotValidException.class)
    public final ResponseEntity<ExceptionResponse> handlerUserEmailDomainIsNotValidException(UserEmailDomainIsNotValidException ex, WebRequest webRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(),
                webRequest.getDescription(false),
                new Date()
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest webRequest) {

        List<Map<String, String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> {
                    Map<String, String> err = new HashMap<>();
                    err.put("field", error.getField());
                    err.put("message", error.getDefaultMessage());
                    return err;
                })
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Erro de validação");
        response.put("errors", errors);
        response.put("details", webRequest.getDescription(false));
        response.put("timestamp", new Date());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}