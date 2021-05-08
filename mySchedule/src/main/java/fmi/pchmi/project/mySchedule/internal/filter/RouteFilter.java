package fmi.pchmi.project.mySchedule.internal.filter;

import fmi.pchmi.project.mySchedule.internal.CommonUtils;
import fmi.pchmi.project.mySchedule.internal.constants.Routes;
import fmi.pchmi.project.mySchedule.model.database.user.Role;
import fmi.pchmi.project.mySchedule.model.database.user.User;
import fmi.pchmi.project.mySchedule.model.exception.ForbiddenException;
import fmi.pchmi.project.mySchedule.model.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
@Order(3)
public class RouteFilter extends OncePerRequestFilter {

    @Autowired
    private AntPathMatcher antPathMatcher;

    private static final Set<Pair<HttpMethod, String>> anonymousRoutes = new HashSet<>(){{
        add(Pair.of(HttpMethod.POST, Routes.LOGIN));
    }};

    private static final Set<Pair<HttpMethod, String>> employeeRoutes = new HashSet<>(){{
        add(Pair.of(HttpMethod.GET, Routes.USERS));
        add(Pair.of(HttpMethod.GET, Routes.USERS_ID));
        add(Pair.of(HttpMethod.PUT, Routes.USERS_ID));

        add(Pair.of(HttpMethod.GET, Routes.GROUPS));
        add(Pair.of(HttpMethod.GET, Routes.GROUPS_ID));

        add(Pair.of(HttpMethod.GET, Routes.EVENTS));
        add(Pair.of(HttpMethod.GET, Routes.EVENTS_ID));
        add(Pair.of(HttpMethod.GET, Routes.EVENTS_USER_ID));
        add(Pair.of(HttpMethod.GET, Routes.EVENTS_GROUP_ID));
        add(Pair.of(HttpMethod.POST, Routes.EVENTS));
        add(Pair.of(HttpMethod.PUT, Routes.EVENTS_ID));
        add(Pair.of(HttpMethod.DELETE, Routes.EVENTS_ID));
    }};

    private static final Set<Pair<HttpMethod, String>> managerRoutes = new HashSet<>(){{
        addAll(employeeRoutes);
    }};

    private static final Set<Pair<HttpMethod, String>> adminRoutes = new HashSet<>(){{
        addAll(managerRoutes);
        add(Pair.of(HttpMethod.POST, Routes.USERS));
        add(Pair.of(HttpMethod.DELETE, Routes.USERS_ID));

        add(Pair.of(HttpMethod.POST, Routes.GROUPS));
        add(Pair.of(HttpMethod.PUT, Routes.GROUPS_ID));
        add(Pair.of(HttpMethod.DELETE, Routes.GROUPS_ID));
    }};

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws IOException, ServletException {
        User user = CommonUtils.getLoggedUser(httpServletRequest);

        if (user == null) {
            if (!isRequestValid(httpServletRequest, anonymousRoutes)) {
                throw UnauthorizedException.create();
            }
        } else if (Role.EMPLOYEE.equals(user.getRole())) {
            if (!isRequestValid(httpServletRequest, employeeRoutes)) {
                throw ForbiddenException.create();
            }
        } else if (Role.MANAGER.equals(user.getRole())) {
            if (!isRequestValid(httpServletRequest, managerRoutes)) {
                throw ForbiddenException.create();
            }
        } else if (Role.ADMINISTRATOR.equals(user.getRole())) {
            if (!isRequestValid(httpServletRequest, adminRoutes)) {
                throw ForbiddenException.create();
            }
        } else {
            throw ForbiddenException.create();
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean isRequestValid(HttpServletRequest httpServletRequest, Set<Pair<HttpMethod, String>> routes) {
        for (Pair<HttpMethod, String> route: routes) {
            if (antPathMatcher.match(route.getSecond(), getRequestUri(httpServletRequest))
                    && antPathMatcher.match(String.valueOf(route.getFirst()), httpServletRequest.getMethod())) {
                return true;
            }
        }
        return false;
    }

    private String getRequestUri(HttpServletRequest httpServletRequest) {
        String url = httpServletRequest.getRequestURI();
        if(url.endsWith("/"))
        {
            url = url.substring(0, url.length() - 1);
        }
        return url;
    }
}
