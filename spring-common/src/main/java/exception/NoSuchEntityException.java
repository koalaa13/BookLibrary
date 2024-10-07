package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchEntityException extends RuntimeException {
    public NoSuchEntityException(String message) {
        super(message);
    }

    public NoSuchEntityException(Class<?> clz, Object id) {
        super(String.format("No such entity %s with id %s", clz.getSimpleName(), id));
    }
}
