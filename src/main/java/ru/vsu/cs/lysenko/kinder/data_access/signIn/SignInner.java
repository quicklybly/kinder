package ru.vsu.cs.lysenko.kinder.data_access.signIn;

import ru.vsu.cs.lysenko.kinder.data.entities.Session;
import ru.vsu.cs.lysenko.kinder.data.entities.User;
import ru.vsu.cs.lysenko.kinder.exceptions.AuthenticationException;

public interface SignInner {
    Session signIn(User user) throws AuthenticationException;

}
