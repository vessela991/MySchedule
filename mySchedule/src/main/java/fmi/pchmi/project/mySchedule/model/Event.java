package fmi.pchmi.project.mySchedule.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    private String name;
    private String description;
    private Date creationTime;
    private Date startTime;
    private Date endTime;

    @ElementCollection(targetClass=String.class)
    private List<String> participants;
    private String creatorId;
    private Priority priority;
}
