package ru.vsu.cs.lysenko.kinder.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vsu.cs.lysenko.kinder.dto.ErrorDTO;
import ru.vsu.cs.lysenko.kinder.exceptions.AppException;

@RestControllerAdvice()
public class RestExceptionHandler {
    @ExceptionHandler(value = {AppException.class})
    public ResponseEntity<ErrorDTO> handleException(AppException exception) {
        return ResponseEntity.status(exception.getStatus()).
                body(ErrorDTO.builder().message(exception.getMessage()).build());
    }
}
