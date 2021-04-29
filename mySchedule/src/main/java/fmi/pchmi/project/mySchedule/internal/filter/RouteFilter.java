package fmi.pchmi.project.mySchedule.internal.filter;

import fmi.pchmi.project.mySchedule.model.exception.ForbiddenException;
import fmi.pchmi.project.mySchedule.internal.CommonUtils;
import fmi.pchmi.project.mySchedule.internal.constants.Routes;
import fmi.pchmi.project.mySchedule.model.database.user.Role;
import fmi.pchmi.project.mySchedule.model.database.user.User;
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
        add(Pair.of(HttpMethod.GET, Routes.USERS_ID));
        add(Pair.of(HttpMethod.PUT, Routes.USERS_ID));
    }};

    private static final Set<Pair<HttpMethod, String>> managerRoutes = new HashSet<>(){{
        addAll(employeeRoutes);
        add(Pair.of(HttpMethod.POST, Routes.GROUPS));
    }};

    private static final Set<Pair<HttpMethod, String>> adminRoutes = new HashSet<>(){{
        addAll(managerRoutes);
        add(Pair.of(HttpMethod.POST, Routes.USERS));
        add(Pair.of(HttpMethod.GET, Routes.USERS));
        add(Pair.of(HttpMethod.DELETE, Routes.USERS_ID));
    }};

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws IOException, ServletException {
        User user = CommonUtils.getLoggedUser(httpServletRequest);

        if (user == null) {
            AuthorizeRequest(httpServletRequest, anonymousRoutes);
        }
        else if (user.getRole().equals(Role.EMPLOYEE)) {
            AuthorizeRequest(httpServletRequest, employeeRoutes);
        }
        else if (user.getRole().equals(Role.MANAGER)) {
            AuthorizeRequest(httpServletRequest, managerRoutes);
        }
        else if (user.getRole().equals(Role.ADMINISTRATOR)) {
            AuthorizeRequest(httpServletRequest, adminRoutes);
        }
        else {
            throw ForbiddenException.create();
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void AuthorizeRequest(HttpServletRequest httpServletRequest, Set<Pair<HttpMethod, String>> routes) {
        for (Pair<HttpMethod, String> route: routes) {
            if (antPathMatcher.match(route.getSecond(), getRequestUri(httpServletRequest))
                    && antPathMatcher.match(String.valueOf(route.getFirst()), httpServletRequest.getMethod())) {
                return;
            }
        }

        throw ForbiddenException.create();
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
