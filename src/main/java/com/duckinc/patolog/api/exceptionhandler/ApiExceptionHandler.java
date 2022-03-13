package com.duckinc.patolog.api.exceptionhandler;

import com.duckinc.patolog.domain.exceptions.DomainException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource msgSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, parseProblem(ex, status), headers, status, request);
    }
    private Problem parseProblem(String message, HttpStatus status){
        Problem problem = new Problem();

        problem.setStatus(status.value());
        problem.setTime(LocalDateTime.now());
        problem.setTitle(message);

        return problem;
    }

    private Problem parseProblem(MethodArgumentNotValidException ex, HttpStatus status){
        Problem problem = new Problem();

        problem.setStatus(status.value());
        problem.setTime(LocalDateTime.now());
        problem.setTitle(status.getReasonPhrase());
        problem.setErrorList(parseErrorFields(ex));

        return problem;
    }

    private List<Problem.ErrorField> parseErrorFields(MethodArgumentNotValidException ex){
        List<Problem.ErrorField> errors = new ArrayList<>();

        for(var error : ex.getBindingResult().getAllErrors()){
            String field = ((FieldError) error).getField();
            String reason = msgSource.getMessage(error, LocaleContextHolder.getLocale());

            errors.add(new Problem.ErrorField(field,reason));
        }

        return errors;
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Object> handleDomainException(DomainException ex, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return handleExceptionInternal(ex, parseProblem(ex.getMessage(), status), new HttpHeaders(), status, request);
    }
}
