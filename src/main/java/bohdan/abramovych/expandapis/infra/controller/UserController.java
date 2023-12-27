package bohdan.abramovych.expandapis.infra.controller;

import bohdan.abramovych.expandapis.infra.controller.dto.AuthRequestDTO;
import bohdan.abramovych.expandapis.infra.controller.dto.JwtResponseDTO;
import bohdan.abramovych.expandapis.infra.service.user.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping("/add")
    public void addUser(@RequestBody AuthRequestDTO authRequestDTO) {
        userService.saveUser(authRequestDTO);
    }

    @PostMapping("/authenticate")
    public JwtResponseDTO authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        return userService.getToken(authRequestDTO);
    }
}
