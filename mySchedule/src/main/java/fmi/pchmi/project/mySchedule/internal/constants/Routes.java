package fmi.pchmi.project.mySchedule.internal.constants;

public class Routes {
    public final static String LOGIN = "/identity/login";

    public final static String USERS = "/users";
    public final static String USERS_ID = "/users/{id}";

    public final static String GROUPS = "/groups";
    public final static String GROUPS_ID = "/groups/{id}";

    public final static String EVENTS = "/events";
    public final static String EVENTS_ID = "/events/{id}";
    public final static String EVENTS_USER_ID = "/events/users/{id}";// why not /users/id/events
    public final static String EVENTS_GROUP_ID = "/events/groups/{id}"; // why not /groups/id/events

//    public final static String SCHEDULES = "/schedules";
//    public final static String SCHEDULES_ID = "/schedules/{id}";

}
