package bohdan.abramovych.expandapis.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @Column(name = "USERNAME", unique = true)
    private String username;

    @JsonIgnore
    @Column(name = "PASSWORD")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLES")
    private UserRole roles;
}
