package bohdan.abramovych.expandapis.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole  implements GrantedAuthority {
    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
    public static UserRole getRole(String role) {
        return UserRole.valueOf(role);
    }
}