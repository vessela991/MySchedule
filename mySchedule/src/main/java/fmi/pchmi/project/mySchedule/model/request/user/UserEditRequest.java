package fmi.pchmi.project.mySchedule.model.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEditRequest extends UserRequest {
    private MultipartFile picture;
}
