package is.hi.hbv501g.h6.hugboverkefni.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void addNewUser(User user) {
        Optional<User> userByEmail = userRepository.findUserByEmail(user.getEmail());
        Optional<User> userByUsername = userRepository.findUserByUserName(user.getUserName());
        if (userByEmail.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        if (userByUsername.isPresent()) {
            throw new IllegalStateException("username taken");
        }
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new IllegalStateException("user with id " + userId + " does not exist");
        }
        userRepository.deleteById(userId);
    }
}