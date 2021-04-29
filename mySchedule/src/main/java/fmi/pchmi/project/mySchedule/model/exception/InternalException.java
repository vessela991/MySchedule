package fmi.pchmi.project.mySchedule.model.exception;

import fmi.pchmi.project.mySchedule.internal.constants.ExceptionMessages;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InternalException extends RuntimeException {
    public final HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    public final String userMessage = ExceptionMessages.SYSTEM_ERROR;

    private InternalException() {
        super();
    }

    public static InternalException create() {
        return new InternalException();
    }
}