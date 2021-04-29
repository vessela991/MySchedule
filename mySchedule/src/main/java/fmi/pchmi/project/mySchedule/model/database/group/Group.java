package fmi.pchmi.project.mySchedule.model.database.group;

import fmi.pchmi.project.mySchedule.model.request.group.GroupRequest;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "company_group")
public class Group {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    private String name;

    @ElementCollection(targetClass=String.class)
    private List<String> members;
    private String creatorId;

    public static Group fromGroupRequest(GroupRequest groupRequest, String creatorId) {
        Group group = new Group();
        group.setName(groupRequest.getName());
        group.setMembers(groupRequest.getMembers());
        group.setCreatorId(creatorId);
        return group;
    }
}
