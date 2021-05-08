package fmi.pchmi.project.mySchedule.model.request.event;

import lombok.Getter;

@Getter
public class EventCreateRequest extends EventUpdateRequest {
    private boolean isPersonal;
}
