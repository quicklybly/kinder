package ru.vsu.cs.lysenko.kinder.data_access.signUp;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vsu.cs.lysenko.kinder.data.entities.User;
import ru.vsu.cs.lysenko.kinder.data.repos.UserRepository;
import ru.vsu.cs.lysenko.kinder.utils.ResponseUtils;

import javax.swing.text.StyledEditorKit;
import java.util.Optional;

public class DataBaseSignUpper implements SignUpper {
    @Autowired
    private UserRepository userRepo;

    @Override
    public JSONObject signUp(User user) {
        JSONObject response = ResponseUtils.prepareResponse();
        Optional<Boolean> isUserInDatabase = userRepo.checkIfUserInRepositoryByUsername(user.getUsername());
        if (isUserInDatabase.orElse(false)) {
            response.put("status", "error");
            response.put("message", "This user already exist");
            return response;
        }
        userRepo.save(user);
        return response;
    }
}
