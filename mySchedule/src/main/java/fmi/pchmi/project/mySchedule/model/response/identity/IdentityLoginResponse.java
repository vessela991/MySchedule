package fmi.pchmi.project.mySchedule.model.response.identity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IdentityLoginResponse {
    private String jwt;
}
