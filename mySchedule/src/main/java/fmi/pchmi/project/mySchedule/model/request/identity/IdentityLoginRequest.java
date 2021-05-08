package fmi.pchmi.project.mySchedule.model.request.identity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IdentityLoginRequest {
    private String username;
    private String password;
}
