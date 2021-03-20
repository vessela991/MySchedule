package fmi.pchmi.project.mySchedule.model;

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
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @ElementCollection(targetClass=String.class)
    private List<String> events;
    private String creatorId;
    private boolean isPrivate;
}
