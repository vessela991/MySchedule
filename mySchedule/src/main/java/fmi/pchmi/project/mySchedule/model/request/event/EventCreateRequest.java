package fmi.pchmi.project.mySchedule.model.request.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class EventCreateRequest extends EventUpdateRequest {
    private boolean isPersonal;
}
