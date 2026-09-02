package dev.jorge.projects.gossipuerj.exception.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ExceptionDetails {

    USER_EMAIL_DOMAIN_NOT_ALLOWED_MESSAGE("O domínio do e-mail '%s' não é permitido", HttpStatus.BAD_REQUEST),
    USER_VERIFICATION_CODE_IS_NOT_VALID_MESSAGE("O código de verificação '%s' é inválido", HttpStatus.BAD_REQUEST),
    USER_VERIFICATION_CODE_EXPIRED_MESSAGE("O código de verificação '%s' expirou", HttpStatus.BAD_REQUEST),
    USER_NOT_VERIFIED_MESSAGE("O usuário '%s' não está verificado", HttpStatus.FORBIDDEN),
    USER_ALREADY_EXISTS_MESSAGE("O usuário '%s' já existe", HttpStatus.CONFLICT),
    USER_NOT_FOUND_MESSAGE("O usuário '%s' não foi encontrado", HttpStatus.NOT_FOUND);

    @Getter
    private final HttpStatus httpStatus;
    private final String message;

    ExceptionDetails(String message, HttpStatus httpStatus){
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String formatErrorMessage(String value){
        return String.format(this.message, value);
    }
}