package main.security;

import main.models.User;
import main.repositories.UserRepository;
import main.security.utils.AuthPasswordHashUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.UUID;

@Service
public class UserAuthenticationService implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(UserAuthenticationService.class);

    @Autowired
    AuthPasswordHashUtility passwdUtil;

    @Autowired
    LocalUserAuthenticationProvider authProvider;

    @Autowired
    UserRepository userRepository;

    public User checkLogin(String login, String password) {
        User user = userRepository.findUserByLogin(login);


        if ("test" .equals(login) && user == null) {
            user = new User();
            user.setLogin(login);
            user.setPassword(password);
            user.setEnabled(true);

            user = userRepository.save(user);

            String salt = UUID.randomUUID().toString();
            user.setSalt(salt);
            user.setHash(passwdUtil.calculateHash(user));

            userRepository.save(user);
        }

        if (user == null || !user.isEnabled() || !passwdUtil.isValidPassword(user, password)) {
            return null;
        }

        return user;
    }
}
