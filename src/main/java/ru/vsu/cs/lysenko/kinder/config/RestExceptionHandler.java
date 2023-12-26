package ru.vsu.cs.lysenko.kinder.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vsu.cs.lysenko.kinder.dto.ErrorDTO;
import ru.vsu.cs.lysenko.kinder.exceptions.AppException;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice()
public class RestExceptionHandler {
    @ExceptionHandler(value = {AppException.class})
    public ResponseEntity<ErrorDTO> handleAppException(AppException exception) {
        return ResponseEntity.status(exception.getStatus()).
                body(ErrorDTO.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<ErrorDTO> handleMissingParameterException(MissingServletRequestParameterException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).
                body(ErrorDTO.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ErrorDTO> handleValidationException(ConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorDTO.builder()
                        .message(
                                exception.getConstraintViolations().stream()
                                        .map(constraintViolation ->
                                                constraintViolation.getPropertyPath() +
                                                        " " + constraintViolation.getMessage()
                                        )
                                        .collect(Collectors.joining(", "))
                        )
                        .build()
                );
    }
}
