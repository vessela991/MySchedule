package fmi.pchmi.project.mySchedule.model.request.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupRequest {
    private String name;
    private List<String> members;
}
