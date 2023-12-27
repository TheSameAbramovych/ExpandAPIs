package bohdan.abramovych.expandapis.infra.service.exception.handler;

import bohdan.abramovych.expandapis.core.exception.ApiException;
import bohdan.abramovych.expandapis.core.exception.NotFoundException;
import io.jsonwebtoken.JwtException;
import org.hibernate.JDBCException;
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

    @ExceptionHandler(value = {JDBCException.class})
    protected ResponseEntity<Object> handleSignature(JDBCException ex, WebRequest request) {
        return handleExceptionInternal(ex, "Exception with SQL", new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}