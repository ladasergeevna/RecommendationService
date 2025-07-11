package org.skypro.recommendationService.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * REST-контроллер для очистки кэша.
 */
@Tag(name = "Cache Management", description = "Контроллер для управления кэшированием и его очистки")
@RestController
@RequestMapping("/management")
public class CacheManagementController {

    private final CacheManager cacheManager;

    @Autowired
    public CacheManagementController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * Очищает кэш
     *
     * @return ответ об очистке кэша
     */
    @Operation(summary = "Очистка кэша", description = "Очищает все кэши, управляемые CacheManager.")
    @PostMapping("/clear-caches")
    public ResponseEntity<String> clearAllCaches() {
        cacheManager.getCacheNames().forEach(cacheName -> {
            if (cacheManager.getCache(cacheName) != null) {
                cacheManager.getCache(cacheName).clear();
            }
        });
        return ResponseEntity.ok("Кэш очищен!");
    }
}