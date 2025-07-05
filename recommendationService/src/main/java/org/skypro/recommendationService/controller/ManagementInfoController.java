package org.skypro.recommendationService.controller;

import org.skypro.recommendationService.configuration.BuildInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/*@RestController
public class ManagementInfoController {

    private final BuildProperties buildProperties;

    public ManagementInfoController(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @GetMapping("/management/info")
    public Map<String, String> getInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("name", buildProperties.getName());
        info.put("version", buildProperties.getVersion());
        return info;
    }
}*/

@RestController
public class ManagementInfoController {

    private final BuildProperties buildProperties;

    public ManagementInfoController(@Autowired(required = false) BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @GetMapping("/management/info")
    public Map<String, String> getInfo() {
        Map<String, String> info = new HashMap<>();
        if (buildProperties != null) {
            info.put("name", buildProperties.getName());
            info.put("version", buildProperties.getVersion());
        } else {
            info.put("name", "My Service");
            info.put("version", "unknown");
        }
        return info;
    }
}
