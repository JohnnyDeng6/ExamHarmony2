package ca.cmpt276.examharmony.Model.user;

import ca.cmpt276.examharmony.Controllers.UserAlreadyExistException;
import ca.cmpt276.examharmony.Model.roles.Role;
import ca.cmpt276.examharmony.Model.roles.RoleRepository;
import ca.cmpt276.examharmony.Model.registration.UserRegistrationDto;
import ca.cmpt276.examharmony.utils.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;

import java.util.Set;
import java.util.UUID;

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
        } else if (findByUsername(registrationDto.getUsername()) != null) {
            throw new UserAlreadyExistException("There is an account with that username: " + registrationDto.getUsername());
        }

        User user = User.createUser(
                registrationDto.getUsername(),
                passwordEncoder.encode(PasswordGenerator.generatePassword(15)),
                registrationDto.getEmail(),
                registrationDto.getName()
        );

        Set<Role> roles = new HashSet<>();
        for (String roleName : registrationDto.getRoles()) {
            Role role = roleRepository.findByName(roleName);
            if (role != null) {
                roles.add(role);
            }
        }

        user.setPasswordResetToken(UUID.randomUUID());
        user.setPasswordResetTokenExpiry(LocalDateTime.now().plusDays(7)); //password reset for 7 days
        user.setRoles(roles);
        userRepository.save(user);
        registrationDto.setResetPasswordToken(user.getPasswordResetToken());
        registrationDto.setUUID(user.getUUID());
    }
    public User findByUUID(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public void updatePassword(UUID userId, String newPassword) {
        User user = findByUUID(userId);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }
    }

    public void invalidatePasswordResetToken(UUID userId) {
        User user = findByUUID(userId);
        if (user != null) {
            user.setPasswordResetToken(UUID.fromString("00000000-0000-0000-0000-000000000000"));
            user.setPasswordResetTokenExpiry(null);
            userRepository.save(user);
        }
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmailAddress(email) != null;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByPasswordResetToken(UUID passwordResetToken) {
        return userRepository.findByPasswordResetToken(passwordResetToken);
    }
}
