package fmi.pchmi.project.mySchedule.model.user.request;

import fmi.pchmi.project.mySchedule.model.user.Gender;
import fmi.pchmi.project.mySchedule.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequestModel {
    private String username;
    private String password;
    private String email;
    private String userInfo;
    private Gender gender;
    private Role role;
}
