package fmi.pchmi.project.mySchedule.model.database.user;

import fmi.pchmi.project.mySchedule.internal.constants.DatabaseConstants;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
//@Transactional
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(unique = true)
    @NotNull(message = "Username cannot be empty")
    @Size(min = DatabaseConstants.USER_USERNAME_MIN, max = DatabaseConstants.USER_USERNAME_MAX)
    private String username;

    @NotNull(message = "Password cannot be empty")
    @Size(min = DatabaseConstants.USER_PASSWORD_MIN, max = DatabaseConstants.USER_PASSWORD_MAX)
    private String password;

    @Column(unique = true)
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @Size(max = DatabaseConstants.USER_INFO_MAX)
    private String userInfo;

    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    @NotNull(message = "Role cannot be null")
    private Role role;

    @Column(length = Integer.MAX_VALUE)
    private String picture;

    @NotNull(message = "GroupId cannot be null")
    private String groupId;

    @ElementCollection(targetClass=String.class, fetch = FetchType.EAGER)
    private Set<String> eventIds = new HashSet<>();
}
