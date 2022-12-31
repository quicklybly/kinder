package ru.vsu.cs.lysenko.kinder.data_access.signUp;

import org.json.JSONObject;
import ru.vsu.cs.lysenko.kinder.data.entities.User;

public interface SignUpper {
    JSONObject signUp(User user);

}
