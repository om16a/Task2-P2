package org.example.jwt.services;
import org.example.jwt.DTO.UserDTO;
import org.example.jwt.model.User;
import org.example.jwt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService {
    final UserRepository userRepository;
    final JwtService jwtService;

    public UserInfoService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public UserDTO getUserInfo(String accessToken) {
        String userEmail = jwtService.extractSubjectAccessToken(accessToken);
        Optional<User> optionalUser = userRepository.findByUserEmail(userEmail);
        if(optionalUser.isEmpty())
            throw new RuntimeException("User can't be found");
        return convertToUserDTO(optionalUser.get());
    }
    public UserDTO convertToUserDTO(User user) {
        UserDTO userInfo = new UserDTO(user.getUsername(), user.getUserEmail(),
                user.getPassword());
        return userInfo;
    }
    public String extractBearerToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
