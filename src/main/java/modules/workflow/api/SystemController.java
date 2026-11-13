
package com.buildflow.modules.workflow.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/system")
public class SystemController {

    @GetMapping("/ping")
    public SystemStatus ping() {
        return new SystemStatus(
                "BuildFlow is running",
                Instant.now()
        );
    }

    public record SystemStatus(
            String message,
            Instant timestamp
    ) {}
}
