package util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static util.HeaderConstants.CUSTOM_USER_HEADER;
import static util.HeaderConstants.CUSTOM_USER_ROLE_HEADER;

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
}
