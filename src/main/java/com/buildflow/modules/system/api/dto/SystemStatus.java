
package com.buildflow.modules.system.api.dto;

import java.time.Instant;

public class SystemStatus {

    private final String message;
    private final Instant timestamp;

    public SystemStatus(String message, Instant timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
