package ru.vsu.cs.lysenko.kinder.data_access.signUp;

import ru.vsu.cs.lysenko.kinder.data.entities.User;
import ru.vsu.cs.lysenko.kinder.exceptions.UserAlreadyExistsException;

public interface SignUpper {
     void signUp(User user) throws UserAlreadyExistsException;

}
