package fmi.project.mySchedule.model.database.event;

import fmi.project.mySchedule.internal.constants.DatabaseConstants;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

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
    @Size(min = DatabaseConstants.EVENT_NAME_MIN, max = DatabaseConstants.EVENT_NAME_MAX)
    private String name;
    @Size(max = DatabaseConstants.EVENT_DESCRIPTION_MAX)
    private String description;
    private Date creationTime;
    private Date startTime;
    private Date endTime;

    @OneToMany(cascade = CascadeType.ALL , orphanRemoval = true)
    @JoinColumn(name="event_id")
    private Set<EventParticipant> participants;
    private String creatorId;
    private Priority priority = Priority.LOW;
    private boolean isPersonal;
}
