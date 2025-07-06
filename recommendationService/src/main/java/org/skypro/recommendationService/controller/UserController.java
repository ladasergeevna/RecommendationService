package org.skypro.recommendationService.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.skypro.recommendationService.model.UserDeposit;
import org.skypro.recommendationService.model.UserWithdraw;
import org.skypro.recommendationService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
/**
 * REST-контроллер для работы с пользователями.
 */
@Tag(name = "UserController", description = "Контроллер для работы с пользователями")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    /**
     * Получает информацию о выводе средств пользователя по его идентификатору.
     *
     * @param id уникальный идентификатор пользователя.
     * @return ответ с объектом UserWithdraw, если данные найдены, или 404 Not Found, если нет.
     */
    @Operation(summary = "Получает информацию о выводе средств пользователя по его идентификатору")
    @GetMapping(path = "/withdraws/{id}")
    public ResponseEntity<UserWithdraw> getWithdrawOfUser(@PathVariable("id") UUID id){
        if (userService.getWithdraws(id) == null){return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(userService.getWithdraws(id));
    }
    /**
     * Получает информацию о получении средств пользователя по его идентификатору.
     *
     * @param id уникальный идентификатор пользователя.
     * @return ответ с объектом UserWithdraw, если данные найдены, или 404 Not Found, если нет.
     */
    @Operation(summary = "Получает информацию о получении средств пользователя по его идентификатору")
    @GetMapping(path = "/deposits/{id}")
    public ResponseEntity<UserDeposit> getDepositOfUser(@PathVariable("id") UUID id){
        if (userService.getDeposits(id) == null){return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(userService.getDeposits(id));
    }
}
