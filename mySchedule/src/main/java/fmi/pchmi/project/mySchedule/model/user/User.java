package fmi.pchmi.project.mySchedule.model.user;

import fmi.pchmi.project.mySchedule.model.user.request.UserLoginRequestModel;
import fmi.pchmi.project.mySchedule.model.user.request.UserRegisterRequestModel;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(unique = true)
    @NotNull(message = "Username cannot be empty")
    @Size(min=4, max=32)
    private String username;

    @NotNull(message = "Password cannot be empty")
    @Size(min=4, max=32)
    private String password;

    @Column(unique = true)
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;
    private String userInfo;

    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    @NotNull(message = "Role cannot be null")
    private Role role = Role.EMPLOYEE;

    public static User fromUserRegisterRequestModel(UserRegisterRequestModel userRegisterRequestModel) {
        User user = new User();
        user.setUsername(userRegisterRequestModel.getUsername());
        user.setPassword(userRegisterRequestModel.getPassword());
        user.setEmail(userRegisterRequestModel.getEmail());
        user.setGender(userRegisterRequestModel.getGender());
        user.setRole(userRegisterRequestModel.getRole());
        user.setUserInfo(userRegisterRequestModel.getUserInfo());
        return user;
    }
}
