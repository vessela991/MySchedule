package fmi.pchmi.project.mySchedule.internal;


import fmi.pchmi.project.mySchedule.internal.constants.CommonConstants;
import fmi.pchmi.project.mySchedule.model.database.user.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Base64;

public class CommonUtils {
    public static User getLoggedUser(HttpServletRequest httpServletRequest) {
        return (User)httpServletRequest.getAttribute(CommonConstants.LOGGED_USER);
    }

    public static <T> boolean DoesEnumContain(Class<? extends Enum<?>> clazz, T value) {
        return isValueContained(getEnumNames(clazz), value);
    }

    public static <T> boolean isValueContained(T[] values, T value) {
        for (T val: values) {
            if (val.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String[] getEnumNames(Class<? extends Enum<?>> clazz) {
        return Arrays.stream(clazz.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }

    public static String encodeBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
}
