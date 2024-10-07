package aspect;

import java.lang.reflect.Method;

import annotation.HasRole;
import exception.IncorrectRoleException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import util.ContextHelper;

@Component
@Aspect
public class HasRoleAspect {
    @Before(value = "within(annotation.HasRole *)")
    public void before(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        HasRole hasRoleAnnotation = method.getAnnotation(HasRole.class);
        String needRole = hasRoleAnnotation.value();

        if (needRole == null || needRole.isEmpty()) {
            joinPoint.proceed();
            return;
        }

        if (!needRole.equals(ContextHelper.getCurrentUserRole())) {
            throw new IncorrectRoleException(String.format("User should has role %s", needRole));
        }

        joinPoint.proceed();
    }
}
