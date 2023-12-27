package bohdan.abramovych.expandapis.core.exception;

import org.slf4j.helpers.MessageFormatter;

public class ApiException extends RuntimeException {
    public ApiException(String messageTemplate, String... params) {
        super(MessageFormatter.arrayFormat(messageTemplate, params).getMessage());
    }
}
