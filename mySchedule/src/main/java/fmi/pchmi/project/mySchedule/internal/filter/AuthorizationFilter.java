package fmi.pchmi.project.mySchedule.internal.filter;

import fmi.pchmi.project.mySchedule.model.exception.UnauthorizedException;
import fmi.pchmi.project.mySchedule.internal.constants.CommonConstants;
import fmi.pchmi.project.mySchedule.model.database.user.User;
import fmi.pchmi.project.mySchedule.repository.UserRepository;
import fmi.pchmi.project.mySchedule.service.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(2)
public class AuthorizationFilter extends OncePerRequestFilter {
    private static final String BEARER = "Bearer ";
    private static final Integer TOKEN_SUBSTRING = 7;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = extractTokenFromHeader(httpServletRequest);

        if (jwt != null) {
            Claims claims = jwtService.getClaims(jwt);
            if (claims == null) {
                throw UnauthorizedException.create();
            }

            if (!jwtService.validateClaims(claims)) {
                throw UnauthorizedException.create();
            }

            String userId = jwtService.getUserId(claims);
            User user = userRepository.findById(userId).orElseThrow(UnauthorizedException::create);
            httpServletRequest.setAttribute(CommonConstants.LOGGED_USER, user);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String extractTokenFromHeader(HttpServletRequest httpServletRequest) {
        final String authHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith(BEARER)) {
            return authHeader.substring(TOKEN_SUBSTRING);
        }
        return null;
    }
}
