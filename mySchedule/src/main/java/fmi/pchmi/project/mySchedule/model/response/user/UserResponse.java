package fmi.pchmi.project.mySchedule.model.response.user;

import fmi.pchmi.project.mySchedule.model.database.user.Gender;
import fmi.pchmi.project.mySchedule.model.database.user.Role;
import fmi.pchmi.project.mySchedule.model.database.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String userInfo;
    private Gender gender;
    private Role role;
    private String picture;
    private String groupId;

    public static UserResponse fromUser(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setUserInfo(user.getUserInfo());
        userResponse.setGender(user.getGender());
        userResponse.setRole(user.getRole());
        userResponse.setPicture(user.getPicture());
        userResponse.setGroupId(user.getGroupId());
        return userResponse;
    }

    public static Collection<UserResponse> fromUsers(Iterable<User> users) {
        Collection<UserResponse> userResponses = new ArrayList<>();
        for (User user:users) {
            userResponses.add(fromUser(user));
        }
        return userResponses;
    }
}
