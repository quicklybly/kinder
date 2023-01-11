package ru.vsu.cs.lysenko.kinder.data_access.signUp;

import org.springframework.beans.factory.annotation.Autowired;
import ru.vsu.cs.lysenko.kinder.data.entities.User;
import ru.vsu.cs.lysenko.kinder.data.repos.UserRepository;
import ru.vsu.cs.lysenko.kinder.exceptions.UserAlreadyExistsException;

import java.util.Optional;

public class DataBaseSignUpper implements SignUpper {
    @Autowired
    private UserRepository userRepo;

    @Override
    public void signUp(User user) throws UserAlreadyExistsException {
        Optional<Boolean> isUserInDatabase = userRepo.checkIfUserInRepositoryByUsername(user.getUsername());
        if (isUserInDatabase.orElse(false)) {
            throw new UserAlreadyExistsException();
        }
        userRepo.save(user);
    }
}
