package af.dfi.lang.enums;

/**
 * @author Rahul Goel created on 10/6/18
 */


public enum ErrorCodes {

    ACTIVEMQ_001("Middleware-Kafka", "Error while establishing connection with Kafka");

    private final String code;
    private final String description;

    private ErrorCodes(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
