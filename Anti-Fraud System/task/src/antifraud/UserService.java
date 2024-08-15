package antifraud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static antifraud.Role.ADMINISTRATOR;
import static antifraud.Role.MERCHANT;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(String name, String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("User already exists");
        }

        User newUser = new User();
        newUser.setName(name);
        //newUser.setUsername(username);
        username = username.replaceAll("\\s", "").trim();
        newUser.setUsername(username.trim());
        newUser.setPassword(passwordEncoder.encode(password));

        if (userRepository.count() == 0) {
            newUser.setRole(ADMINISTRATOR);
            newUser.setLocked(false);
        } else {
            newUser.setRole(MERCHANT);
            newUser.setLocked(true);
        }

        return userRepository.save(newUser);
    }

    public List<User> listUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public boolean deleteUser(String username) {
        User user = userRepository.findByUsernameIgnoreCase(username).orElse(null);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    public User changeUserRole(String username, Role role)
            throws UserNotFoundException, RoleAlreadyAssignedException {
        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getRole() == role) {
            throw new RoleAlreadyAssignedException("Role already assigned to the user");
        }

        user.setRole(role);
        return userRepository.save(user);
    }

    public String changeUserAccess(String username, String operation)
            throws UserNotFoundException, InvalidOperationException {
        User user = userRepository.findByUsernameIgnoreCase(username)
               .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (ADMINISTRATOR.equals(user.getRole())) {
            throw new InvalidOperationException("Cannot lock/unlock ADMINISTRATOR");
        }

        if ("LOCK".equalsIgnoreCase(operation)) {
            user.setLocked(true);
            userRepository.save(user);
            return "User " + username + " locked!";
        } else if ("UNLOCK".equalsIgnoreCase(operation)) {
            user.setLocked(false);
            userRepository.save(user);
            return "User " + username + " unlocked!";
        } else {
            throw new InvalidOperationException("Invalid operation");
        }
    }
}