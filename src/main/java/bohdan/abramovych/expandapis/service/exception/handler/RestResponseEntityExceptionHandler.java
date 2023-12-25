package bohdan.abramovych.expandapis.service.exception.handler;

import bohdan.abramovych.expandapis.service.exception.ApiException;
import bohdan.abramovych.expandapis.service.exception.NotFoundException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(NotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {ApiException.class})
    protected ResponseEntity<Object> handleBadRequest(ApiException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {JwtException.class})
    protected ResponseEntity<Object> handleJwt(JwtException ex, WebRequest request) {
        return handleExceptionInternal(ex, "Error in JWT", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    @ExceptionHandler(value = {SignatureException.class})
    protected ResponseEntity<Object> handleSignature(SignatureException ex, WebRequest request) {
        return handleExceptionInternal(ex, "Error in JWT", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> handleDefault(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "Unknown error", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}