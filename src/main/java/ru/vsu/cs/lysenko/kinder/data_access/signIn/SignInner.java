package ru.vsu.cs.lysenko.kinder.data_access.signIn;

import org.json.JSONObject;
import ru.vsu.cs.lysenko.kinder.data.entities.User;

public interface SignInner {
    JSONObject signIn(User user);
}
