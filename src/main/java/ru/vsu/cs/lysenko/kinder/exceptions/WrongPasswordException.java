package ru.vsu.cs.lysenko.kinder.exceptions;

public class WrongPasswordException extends AuthenticationException {
    public static final String message = "Wrong password";

    @Override
    public String getMessage() {
        return message;
    }
}
