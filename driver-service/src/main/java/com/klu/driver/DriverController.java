package com.klu.driver;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/driver")
public class DriverController {

    private static final Logger log = LoggerFactory.getLogger(DriverController.class);
    private final Environment env;

    public DriverController(Environment env) {
        this.env = env;
    }

    @GetMapping
    public Map<String, Object> getDriver() {
        return response(null);
    }

    @GetMapping("/{id}")
    public Map<String, Object> getDriverById(@PathVariable Long id) {
        return response(id);
    }

    private Map<String, Object> response(Long id) {
        String port = env.getProperty("local.server.port");
        log.info(">>> Request handled by DRIVER-SERVICE instance on port {}", port);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("service", "DRIVER-SERVICE");
        body.put("port", port);
        if (id != null) {
            body.put("driverId", id);
        }
        body.put("message", "Hello from driver service instance running on port " + port);
        return body;
    }
}
