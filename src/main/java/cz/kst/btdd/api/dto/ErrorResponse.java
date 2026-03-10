package cz.kst.btdd.api.dto;

import java.time.Instant;

public class ErrorResponse {

    private final String message;
    private final String error;
    private final int status;
    private final Instant timestamp;

    public ErrorResponse(String message, String error, int status) {
        this.message = message;
        this.error = error;
        this.status = status;
        this.timestamp = Instant.now();
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public int getStatus() {
        return status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
