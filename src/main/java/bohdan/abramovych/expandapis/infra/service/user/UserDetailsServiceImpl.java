package bohdan.abramovych.expandapis.infra.service.user;

import bohdan.abramovych.expandapis.core.exception.NotFoundException;
import bohdan.abramovych.expandapis.core.model.CustomUserDetails;
import bohdan.abramovych.expandapis.core.model.UserInfo;
import bohdan.abramovych.expandapis.core.persistence.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("Entering in loadUserByUsername Method...");
        UserInfo user = userRepo.findByUsername(username);
        if (user == null) {
            log.error("Username not found: " + username);
            throw new NotFoundException("User:'{}' not found", username);
        }
        log.info("User Authenticated Successfully..!!!");
        return new CustomUserDetails(user);
    }
}