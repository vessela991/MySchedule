package fmi.pchmi.project.mySchedule.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ValidationException extends RuntimeException {
    private final HttpStatus statusCode = HttpStatus.BAD_REQUEST;
    private final String userMessage;

    private ValidationException(String userMessage) {
        super();
        this.userMessage = userMessage;
    }

    public static ValidationException create(String userMessage) {
        return new ValidationException(userMessage);
    }
}