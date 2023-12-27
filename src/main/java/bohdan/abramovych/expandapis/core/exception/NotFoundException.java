package bohdan.abramovych.expandapis.core.exception;


public class NotFoundException extends ApiException {

    public NotFoundException(String messageTemplate, String... params) {
        super(messageTemplate, params);
    }
}
