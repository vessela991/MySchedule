package fmi.pchmi.project.mySchedule.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnauthorizedException extends RuntimeException {
    private final HttpStatus statusCode = HttpStatus.UNAUTHORIZED;
    private final String userMessage = HttpStatus.UNAUTHORIZED.getReasonPhrase();

    private UnauthorizedException() {
        super();
    }

    public static UnauthorizedException create() {
        return new UnauthorizedException();
    }
}
