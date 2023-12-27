package bohdan.abramovych.expandapis;

import bohdan.abramovych.expandapis.infra.controller.dto.AuthRequestDTO;
import bohdan.abramovych.expandapis.infra.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("sql/users.sql")
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringBootTest(classes = ExpandApIsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    private static final String USER_ADD_ENDPOINT = "/user/add";
    private static final String USER_AUTHENTICATE_ENDPOINT = "/user/authenticate";

    @Autowired
    MockMvc mockMvc;

    @SpyBean
    UserService userService;

    @DisplayName("Add New User - Successful")
    @Test
    public void successfulAddUser() throws Exception {
        AuthRequestDTO authRequestDTO = getAuthRequestDTO("newUser", "test");

        mockMvc.perform(post(USER_ADD_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(authRequestDTO)))
                .andExpect(status().isOk());

        verify(userService, times(1))
                .saveUser(authRequestDTO);
    }

    @DisplayName("Add New User - Duplicate Username")
    @Test
    public void failedAddUserExistUserName() throws Exception {
        AuthRequestDTO authRequestDTO = getAuthRequestDTO("test", "test");

        mockMvc.perform(post(USER_ADD_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(authRequestDTO)))
                .andExpect(status().isConflict());

        verify(userService, times(1))
                .saveUser(authRequestDTO);
    }

    @DisplayName("Get JWT for Existed User - Successful")
    @Test
    public void authenticateAndGetToken_Successful() throws Exception {
        AuthRequestDTO authRequestDTO = getAuthRequestDTO("test", "test");

        mockMvc.perform(post(USER_AUTHENTICATE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(authRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty());

        verify(userService, times(1))
                .getToken(authRequestDTO);
    }

    @DisplayName("Get JWT for Non-Existed User - Failure")
    @Test
    public void authenticateAndGetToken_Failure() throws Exception {
        AuthRequestDTO authRequestDTO = getAuthRequestDTO("nonExistentUser", "test");

        mockMvc.perform(post(USER_AUTHENTICATE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(authRequestDTO)))
                .andExpect(status().isNotFound());

        verify(userService, times(1))
                .getToken(authRequestDTO);
    }

    private static AuthRequestDTO getAuthRequestDTO(String username, String password) {
        return AuthRequestDTO.builder()
                .username(username)
                .password(password)
                .build();
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


