package org.skypro.recommendationService.controller;

import org.skypro.recommendationService.model.UserDeposit;
import org.skypro.recommendationService.model.UserWithdraw;
import org.skypro.recommendationService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //User with withdraw transaction
    @GetMapping(path = "/withdraws/{id}")
    public ResponseEntity<UserWithdraw> getWithdrawOfUser(@PathVariable("id") String id){
        if (userService.getWithdraws(id) == null){return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(userService.getWithdraws(id));
    }

    //User with deposit transaction
    @GetMapping(path = "/deposits/{id}")
    public ResponseEntity<UserDeposit> getDepositOfUser(@PathVariable("id") String id){
        if (userService.getDeposits(id) == null){return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(userService.getDeposits(id));
    }
}
