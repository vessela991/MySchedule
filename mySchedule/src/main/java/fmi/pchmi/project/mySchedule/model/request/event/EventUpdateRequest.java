package fmi.pchmi.project.mySchedule.model.request.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
public class EventUpdateRequest {
    private String name;
    private String description;
    private Date startTime;
    private Date endTime;
    private List<String> participants;
    private String priority;
}
