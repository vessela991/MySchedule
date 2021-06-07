package fmi.pchmi.project.mySchedule.model.database.group;

import fmi.pchmi.project.mySchedule.model.request.group.GroupRequest;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

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

    @ElementCollection(targetClass=String.class, fetch = FetchType.EAGER)
    private Set<String> members;
    private String managerId;

}
