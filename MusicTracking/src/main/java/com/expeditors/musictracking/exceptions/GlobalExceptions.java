package com.expeditors.musictracking.exceptions;

import com.expeditors.musictracking.dto.CustomResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.net.BindException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(value = {MissingServletRequestParameterException.class,
            ServletRequestBindingException.class, TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            MissingServletRequestPartException.class,
            BindException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse<?> handleBadRequestException(Exception ex, WebRequest request) {
        CustomResponse<?> rr = CustomResponse.ofError("Unexpected Exception: " + ex);

        return rr;
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public CustomResponse<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                             WebRequest request) {
        var errors = ex.getFieldErrors();
        List<String> errMsgs = errors.stream()
                .map(error -> "@Valid error:" + error.getField() + ": " + error.getDefaultMessage()
                        + ", supplied Value: " + error.getRejectedValue())
                .collect(toList());

        CustomResponse<?> rr = CustomResponse.ofError(errMsgs);

        return rr;
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<CustomResponse<?>> handleMethodArgument(MethodArgumentTypeMismatchException ex, WebRequest request) {
        var errMessage = "MethodArgumentTypeMismatch: name: " + ex.getName() + ", value: " + ex.getValue() + ", message: " +
                ex.getMessage() + ", parameter: " + ex.getParameter();

        CustomResponse<?> rr = CustomResponse.ofError(errMessage);

        return ResponseEntity.badRequest().body(rr);
    }


    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CustomResponse<?> lastPortOfCall(Exception ex, WebRequest request) {
        CustomResponse<?> rr = CustomResponse.ofError("Last Port ;Unexpected Exception: " + ex);

        return rr;
    }
}
