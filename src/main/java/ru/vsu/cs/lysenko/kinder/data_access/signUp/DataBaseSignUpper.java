package ru.vsu.cs.lysenko.kinder.data_access.signUp;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.vsu.cs.lysenko.kinder.data.entities.User;
import ru.vsu.cs.lysenko.kinder.data.repos.UserRepository;
import ru.vsu.cs.lysenko.kinder.exceptions.UserAlreadyExistsException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataBaseSignUpper implements SignUpper {
    private final UserRepository userRepo;

    private final PasswordEncoder pwEncoder;

    @Override
    public void signUp(User user) throws UserAlreadyExistsException {
        Optional<Boolean> isUserInDatabase = userRepo.checkIfUserInRepositoryByUsername(user.getUsername());
        if (isUserInDatabase.orElse(false)) {
            throw new UserAlreadyExistsException();
        }
        user.setPassword(pwEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }
}
