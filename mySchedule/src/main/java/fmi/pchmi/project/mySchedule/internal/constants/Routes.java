package fmi.pchmi.project.mySchedule.internal.constants;

public class Routes {
    private final static String API = "/api";
    public final static String LOGIN = API + "/login";

    public final static String USERS = API + "/users";
    public final static String USERS_ID = API + "/users/{id}";
    public final static String USER_ID_EVENTS = API + "/users/{id}/events";

    public final static String GROUPS = API + "/groups";
    public final static String GROUPS_ID = API + "/groups/{id}";
    public final static String GROUP_ID_EVENTS = API + "/groups/{id}/events";

    public final static String EVENTS = API + "/events";
    public final static String EVENTS_ID = API + "/events/{id}";

//    public final static String SCHEDULES = "/schedules";
//    public final static String SCHEDULES_ID = "/schedules/{id}";

}
