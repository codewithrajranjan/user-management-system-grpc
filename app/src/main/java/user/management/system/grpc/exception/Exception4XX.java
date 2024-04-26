package user.management.system.grpc.exception;

public class Exception4XX extends Exception {

    private String code;
    private String entity;
    private String message;

    public Exception4XX(String code, String entity, String message) {
        super(message);
        this.code = code;
        this.entity = entity;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getEntity() {
        return entity;
    }

    public String getMessage() {
        return message;
    }
    
    
}
