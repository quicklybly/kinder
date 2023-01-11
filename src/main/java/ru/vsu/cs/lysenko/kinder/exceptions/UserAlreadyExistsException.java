package ru.vsu.cs.lysenko.kinder.exceptions;

public class UserAlreadyExistsException extends AuthenticationException {
    public static final String message = "User already exists";
    @Override
    public String getMessage() {
        return message;
    }
}
