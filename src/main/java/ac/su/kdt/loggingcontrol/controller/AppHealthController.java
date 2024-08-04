package ac.su.kdt.loggingcontrol.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppHealthController {
    @GetMapping("/app-health")
    public String healthCheck() {
        return "app_health_status 1.0\n# EOF";
    }
}
