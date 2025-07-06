package org.skypro.recommendationService.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.skypro.recommendationService.configuration.BuildInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
/**
 * REST-контроллер для получения информации о проекте.
 */
@Tag(name = "Management Info", description = "Контроллер для получения информации о проекте")
@RestController
public class ManagementInfoController {

    private final BuildProperties buildProperties;

    public ManagementInfoController(@Autowired(required = false) BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }
    /**
     * Получает название и версию приложения
     *
     * @return ответ с названием и версией приложения
     */
    @Operation(summary = "Получает название и версию приложения")
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
