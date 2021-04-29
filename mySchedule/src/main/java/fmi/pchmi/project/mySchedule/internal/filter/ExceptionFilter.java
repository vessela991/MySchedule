package fmi.pchmi.project.mySchedule.internal.filter;

import fmi.pchmi.project.mySchedule.internal.constants.ExceptionMessages;
import fmi.pchmi.project.mySchedule.model.exception.*;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.NestedServletException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class ExceptionFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (UnauthorizedException ex) {
            setErrorResponse(response, ex.getStatusCode(), ex.getUserMessage());
        } catch (ForbiddenException ex) {
            setErrorResponse(response, ex.getStatusCode(), ex.getUserMessage());
        } catch (NestedServletException ex) {
            Throwable cause = ex.getCause();

            if (cause instanceof ValidationException) {
                setErrorResponse(response, ((ValidationException) cause).getStatusCode(), ((ValidationException) cause).getUserMessage());
            }
            else if (cause instanceof UnauthorizedException) {
                setErrorResponse(response, ((UnauthorizedException) cause).getStatusCode(), ((UnauthorizedException) cause).getUserMessage());
            }
            else if (cause instanceof ForbiddenException) {
                setErrorResponse(response, ((ForbiddenException) cause).getStatusCode(), ((ForbiddenException) cause).getUserMessage());
            }
            else if (cause instanceof InternalException) {
                setErrorResponse(response, ((InternalException) cause).getStatusCode(), ((InternalException) cause).getUserMessage());
            }
            else {
                ex.printStackTrace();
                setErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, ExceptionMessages.SYSTEM_ERROR);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            setErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, ExceptionMessages.SYSTEM_ERROR);
        }
    }

    private void setErrorResponse(HttpServletResponse response, HttpStatus status, String message) {
        response.setStatus(status.value());
        try {
            response.getWriter().write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
