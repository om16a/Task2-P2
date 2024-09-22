package org.example.jwt.services;

import org.example.jwt.DTO.UserDTO;
import org.example.jwt.model.User;
import org.example.jwt.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignupService {
    private final UserRepository userRepository;

    public SignupService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signupUser(UserDTO userDTO) {
        User user = convertToUser(userDTO);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5)));
        userRepository.save(user);
    }
    private User convertToUser(UserDTO userDTO) {
        User user = new User(userDTO.getUsername(),
                userDTO.getEmail(), userDTO.getPassword());
        return user;
    }
}
