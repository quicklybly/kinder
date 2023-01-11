package ru.vsu.cs.lysenko.kinder.exceptions;

public class WrongUserNameException extends AuthenticationException {
    public static final String message = "Wrong user name";

    @Override
    public String getMessage() {
        return message;
    }
}
