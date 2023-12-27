package bohdan.abramovych.expandapis.infra.service.user;

import bohdan.abramovych.expandapis.core.exception.ApiException;
import bohdan.abramovych.expandapis.core.model.UserInfo;
import bohdan.abramovych.expandapis.core.model.UserRole;
import bohdan.abramovych.expandapis.core.persistence.UserRepository;
import bohdan.abramovych.expandapis.infra.controller.dto.AuthRequestDTO;
import bohdan.abramovych.expandapis.infra.controller.dto.JwtResponseDTO;
import bohdan.abramovych.expandapis.infra.service.jwt.JwtService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepo;
    PasswordEncoder encoder;
    JwtService jwtService;
    AuthenticationManager authenticationManager;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
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
