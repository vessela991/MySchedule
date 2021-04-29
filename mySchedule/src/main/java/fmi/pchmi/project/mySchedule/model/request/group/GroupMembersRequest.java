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
public class GroupMembersRequest {
    private List<String> memberIds;
}
