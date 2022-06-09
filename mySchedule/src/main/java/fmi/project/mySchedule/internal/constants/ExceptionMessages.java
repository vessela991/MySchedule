package fmi.project.mySchedule.internal.constants;

public class ExceptionMessages {
    public final static String SYSTEM_ERROR = "System error!";
    
    // Identity Exceptions
    public final static String IDENTITY_BAD_CREDENTIALS = "Bad credentials!";

    // User Exceptions
    public final static String USER_ID_DOES_NOT_EXIST = "User with id: %s does not exist!";
    public final static String USER_NO_USERNAME = "Please fill in username!";
    public final static String USER_USERNAME_ALREADY_EXISTS = "Username already exists!";
    public final static String USER_USERNAME_OUT_OF_RANGE = "Username must be between 4 and 32 characters!";
    public final static String USER_NO_PASSWORD = "Please fill in password!";
    public final static String USER_PASSWORD_REGEX_DOES_NOT_MATCH = "Password must be between 8 and 32 characters and contain at least one digit, lowercase character, uppercase character and a special character, no whitespaces permitted!";
    public final static String USER_NO_EMAIL = "Please fill in email!";
    public final static String USER_EMAIL_ALREADY_EXISTS = "Email already exists!";
    public final static String USER_EMAIL_IS_INVALID = "Email is invalid!";
    public final static String USER_NO_GENDER = "Please fill in gender!";
    public final static String USER_WRONG_GENDER = "Gender must be either MALE, FEMALE or OTHER!";
    public final static String USER_NO_ROLE = "Please fill in role!";
    public final static String USER_WRONG_ROLE = "Role must be either EMPLOYEE, MANAGER or ADMINISTRATOR!";
    public static final String USER_INFO_TOO_LONG = "User info is maximum of 256 characters!";
    public static final String USER_BROKEN_PICTURE = "Failed to process your picture, please insert a new one!";
    public static final String USER_MUST_HAVE_GROUP_ID = "Please fill in groupId!";
    public final static String USER_WITH_ROLE_MANAGER_ALREADY_EXISTS_IN_GROUP = "User with role manager already exists in group, please first make sure there is no group manager!";

    // Group Exceptions
    public final static String GROUP_ID_DOES_NOT_EXIST = "Group with id: %s does not exist!";
    public final static String GROUP_NAME_ALREADY_EXISTS = "Group with name: %s already exists!";
    public final static String GROUP_MUST_HAVE_NAME = "You must specify group name!";
    public final static String GROUP_MEMBERID_MUST_NOT_BE_BLANK = "You cannot add member without id!";

    // Event Exceptions
    public final static String EVENT_ID_DOES_NOT_EXIST = "Event with id: %s does not exist!";
    public final static String EVENT_MUST_HAVE_NAME = "You must specify event name!";
    public static final String EVENT_NAME_MUST_OUT_OF_RANGE = "Event name should be between 4 and 50 characters!";
    public static final String EVENT_DESCRIPTION_TOO_LONG = "Event description cannot be longer than 2000 characters!";
    public final static String EVENT_START_TIME_MUST_BE_SPECIFIED = "You must specify event start time!";
    public final static String EVENT_END_TIME_MUST_BE_SPECIFIED = "You must specify event end time!";
    public final static String EVENT_END_CANNOT_BE_BEFORE_EVENT_START = "Event end time cannot be before event start time!";
    public final static String EVENT_PRIORITY_IS_INVALID = "Event priority must be either LOW, MEDIUM or HIGH!";
    public static final String EVENT_UPDATE_MUST_BE_CREATOR_OR_ADMIN = "To edit a personal event you must be creator or administrator!";
    public static final String EVENT_UPDATE_MUST_BE_CREATOR_OR_MANAGER_OR_ADMIN = "To edit a group event you must be creator or manager or administrator!";
    public final static String EVENT_STATUS_IS_INVALID = "Event status must be either ACCEPTED, PENDING or DECLINED!";
    public final static String EVENT_MUST_HAVE_STATUS = "You must specify event status!";
    public static final String EVENT_USER_MUST_BE_PARTICIPANT = "You must be participant to update your status!";
    public static final String EVENT_DELETE_MUST_BE_CREATOR_OR_ADMIN = "To delete group event you must be creator, administrator, or participate in the event and be a manager!";
}
