package fmi.pchmi.project.mySchedule.internal.constants;

public class ExceptionMessages {
    public final static String BAD_CREDENTIALS = "Bad credentials!";

    public final static String SYSTEM_ERROR = "System error!";

    public final static String USER_ID_DOES_NOT_EXIST = "User with id: %s does not exist!";

    public final static String NO_USERNAME = "Please fill in username!";
    public final static String USERNAME_ALREADY_EXISTS = "Username already exists!";
    public final static String USERNAME_OUT_OF_RANGE = "Username must be between 4 and 32 characters!";

    public final static String NO_PASSWORD = "Please fill in password!";
    public final static String PASSWORD_REGEX_DOES_NOT_MATCH = "Password must be between 8 and 32 characters and contain at least one digit, lowercase character, uppercase character and a special character, no whitespaces permitted!";

    public final static String NO_EMAIL = "Please fill in email!";
    public final static String EMAIL_ALREADY_EXISTS = "Email already exists!";
    public final static String EMAIL_IS_INVALID = "Email is invalid!";

    public final static String NO_GENDER = "Please fill in gender!";
    public final static String WRONG_GENDER = "Gender must be either MALE, FEMALE or OTHER!";

    public final static String NO_ROLE = "Please fill in role!";
    public final static String WRONG_ROLE = "Role must be either EMPLOYEE, MANAGER or ADMINISTRATOR!";

    public static final String USER_INFO_TOO_LONG = "User info is maximum of 256 characters!";

    public static final String BROKEN_PICTURE = "Failed to process your picture, please insert a new one!";

    //group validation
    public static final String NO_NAME = "You must specify group name.";
    public static final String NAME_ALREADY_EXISTS = "Name already exists!";
    public final static String GROUP_ID_DOES_NOT_EXIST = "Group with id: %s does not exist!";

}
