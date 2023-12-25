package bohdan.abramovych.expandapis.service.exception;

public class NotFoundException extends ApiException {

    public NotFoundException(String messageTemplate, String... params) {
        super(messageTemplate, params);
    }
}
