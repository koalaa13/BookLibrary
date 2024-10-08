package util;

import exception.IncorrectRoleException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static util.HttpConstants.CUSTOM_USER_HEADER;
import static util.HttpConstants.CUSTOM_USER_ROLE_HEADER;

public class ContextHelper {
    public static HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }

    public static String getCurrentUser() {
        HttpServletRequest request = getCurrentRequest();
        if (request != null) {
            return request.getHeader(CUSTOM_USER_HEADER);
        }
        return null;
    }

    public static String getCurrentUserRole() {
        HttpServletRequest request = getCurrentRequest();
        if (request != null) {
            return request.getHeader(CUSTOM_USER_ROLE_HEADER);
        }
        return null;
    }

    public static void checkCurrentRole(String role) {
        if (!role.equals(getCurrentUserRole())) {
            throw new IncorrectRoleException("Incorrect user role");
        }
    }
}
