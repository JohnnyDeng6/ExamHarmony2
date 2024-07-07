package ca.cmpt276.examharmony.Model.user;

import ca.cmpt276.examharmony.Controllers.UserAlreadyExistException;
import ca.cmpt276.examharmony.Model.Role;
import ca.cmpt276.examharmony.Model.RoleRepository;
import ca.cmpt276.examharmony.Model.registration.UserRegistrationDto;
import ca.cmpt276.examharmony.utils.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import java.util.Properties;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public void registerNewUser(UserRegistrationDto registrationDto) {
        if (emailExists(registrationDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + registrationDto.getEmail());
        } else if (usernameExists(registrationDto.getName())) {
            throw new UserAlreadyExistException("There is an account with that username: " + registrationDto.getName());
        }

        User user = User.createUser(
                registrationDto.getName(),
                passwordEncoder.encode(PasswordGenerator.generatePassword(15)),
                registrationDto.getEmail()
        );

        Set<Role> roles = new HashSet<>();
        for (String roleName : registrationDto.getRoles()) {
            Role role = roleRepository.findByName(roleName);
            if (role != null) {
                roles.add(role);
            }
        }
        user.setRoles(roles);
        userRepository.save(user);
        registrationDto.setID(user.getID());
    }
    public User findById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public void updatePassword(int userId, String newPassword) {
        User user = findById(userId);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword)); // Remember to hash the password
            userRepository.save(user);
        }
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmailAddress(email) != null;
    }
    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

}
