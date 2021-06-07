package fmi.pchmi.project.mySchedule.model.database.event;

import fmi.pchmi.project.mySchedule.internal.constants.DatabaseConstants;
import fmi.pchmi.project.mySchedule.model.database.user.User;
import fmi.pchmi.project.mySchedule.model.request.event.EventUpdateRequest;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
//@Transactional
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
