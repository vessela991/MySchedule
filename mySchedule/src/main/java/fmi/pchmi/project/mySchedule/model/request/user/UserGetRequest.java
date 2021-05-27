package fmi.pchmi.project.mySchedule.model.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserGetRequest {
    private String username;
    private String email;
    private String userInfo;
    private String gender;
    private String role;
    private String groupId;
}
