package fmi.pchmi.project.mySchedule.internal;


import fmi.pchmi.project.mySchedule.internal.constants.CommonConstants;
import fmi.pchmi.project.mySchedule.model.database.user.User;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class CommonUtils {
    public static User getLoggedUser(HttpServletRequest httpServletRequest) {
        return (User) httpServletRequest.getAttribute(CommonConstants.LOGGED_USER);
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

    public static <T> Collection<T> asCollection(Iterable<T> iterable) {
        Collection<T> collection = new ArrayList<>();
        for (T t : iterable) {
            collection.add(t);
        }
        return collection;
    }

    public static <T> Set<T> asSet(Collection<T> collection) {
        return new HashSet<>(collection);
    }

    public static <T> Set<T> addItemToSet(Set<T> currentSet, T item) {
        Set<T> newSet = new HashSet<>(currentSet);
        newSet.add(item);
        return newSet;
    }

    public static <T> Set<T> removeItemFromSet(Set<T> currentSet, T itemToRemove) {
        Set<T> newSet = new HashSet<>(currentSet);
        newSet.remove(itemToRemove);
        return newSet;
    }
}
