package ca.cmpt276.examharmony.Model.registration;

import ca.cmpt276.examharmony.Controllers.UserAlreadyExistException;
import ca.cmpt276.examharmony.Model.Role;
import ca.cmpt276.examharmony.Model.RoleRepository;
import ca.cmpt276.examharmony.Model.User;
import ca.cmpt276.examharmony.Model.UserRepository;
import ca.cmpt276.examharmony.utils.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmailAddress(email) != null;
    }
    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

}
