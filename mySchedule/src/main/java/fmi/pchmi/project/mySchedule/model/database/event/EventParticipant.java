package fmi.pchmi.project.mySchedule.model.database.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventParticipant {
    private String userId;
    private EventStatus status;
}
