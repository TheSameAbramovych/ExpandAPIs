package bohdan.abramovych.expandapis.service.user;

import bohdan.abramovych.expandapis.controller.dto.AuthRequestDTO;
import bohdan.abramovych.expandapis.controller.dto.JwtResponseDTO;
import bohdan.abramovych.expandapis.model.UserInfo;
import bohdan.abramovych.expandapis.model.UserRole;
import bohdan.abramovych.expandapis.repo.UserRepo;
import bohdan.abramovych.expandapis.service.exception.ApiException;
import bohdan.abramovych.expandapis.service.jwt.JwtService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepo userRepo;
    PasswordEncoder encoder;
    JwtService jwtService;
    AuthenticationManager authenticationManager;

    public UserInfo saveUser(AuthRequestDTO authRequestDTO) {
        checkNull(authRequestDTO);
        UserInfo user = new UserInfo();
        user.setRoles(UserRole.USER);
        user.setUsername(authRequestDTO.getUsername());
        user.setPassword(encoder.encode(authRequestDTO.getPassword()));
        userRepo.save(user);
        return user;
    }

    private static void checkNull(AuthRequestDTO authRequestDTO) {
        if (authRequestDTO == null) throw new ApiException("Invalid user request..!!");
    }

    public JwtResponseDTO getToken(AuthRequestDTO authRequestDTO) {
        checkNull(authRequestDTO);
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            return JwtResponseDTO.builder()
                    .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername())).build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }
}
