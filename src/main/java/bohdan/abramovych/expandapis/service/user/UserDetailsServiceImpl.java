package bohdan.abramovych.expandapis.service.user;

import bohdan.abramovych.expandapis.model.CustomUserDetails;
import bohdan.abramovych.expandapis.model.UserInfo;
import bohdan.abramovych.expandapis.repo.UserRepo;
import bohdan.abramovych.expandapis.service.exception.NotFoundException;
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
    UserRepo userRepo;

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