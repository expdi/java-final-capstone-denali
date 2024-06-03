package com.expeditors.musictracking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ProblemDetail;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponse<T> {

    public enum Status {
        Success,
        Warning,
        Info,
        Error
    }

    private Status status = Status.Error;

    private List<String> errors = new ArrayList<>();

    private String message;

    @JsonInclude(Include.NON_NULL)
    private T entity;

    public static <E> CustomResponse<E> ofValue(E value) {
        return new CustomResponse<E>(Status.Success).entity(value);
    }

    public static <E> CustomResponse<E> ofError(ProblemDetail problemDetail) {
        var rr = new CustomResponse<>(Status.Error);
        rr.getErrors().add("pdType: " + problemDetail.getType());
        rr.getErrors().add("pdTitle: " + problemDetail.getTitle());
        rr.getErrors().add("pdStatus: " + String.valueOf(problemDetail.getStatus()));
        rr.getErrors().add("pdDetail: " + problemDetail.getDetail());
        rr.getErrors().add("pdInstance: " + problemDetail.getInstance());
        return (CustomResponse<E>) rr;
    }

    public static <E> CustomResponse<E> ofError(List<String> errors) {
        return new CustomResponse<E>(Status.Error).errors(errors);
    }

    public static <E> CustomResponse<E> ofInfo(String message) {
        return new CustomResponse<E>(Status.Info).message(message);
    }

    public static <E> CustomResponse<E> ofError(String ... errors) {
        return new CustomResponse<E>(Status.Error).errors(errors);
    }

    private CustomResponse(Status status) {
        this.status = status;
    }

    public CustomResponse<T> entity(T entity) {
        this.entity = entity;
        return this;
    }

    public CustomResponse<T> status(Status status) {
        this.status = status;
        return this;
    }

    public CustomResponse<T> message(String message) {
        this.message = message;
        return this;
    }

    public CustomResponse<T> errors(List<String> errors) {
        this.errors = errors;
        return this;
    }

    public CustomResponse<T> errors(String ... messages) {
        for(String message : messages) {
            this.errors.add(message);
        }
        return this;
    }
}
