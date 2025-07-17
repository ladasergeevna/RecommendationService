package org.skypro.recommendationService.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.recommendationService.model.UserDeposit;
import org.skypro.recommendationService.model.UserWithdraw;
import org.skypro.recommendationService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UUID validUserId = UUID.randomUUID();
    private UUID invalidUserId = UUID.randomUUID();

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testGetDepositOfUser_Success() throws Exception {

        UserDeposit userDeposit = new UserDeposit(validUserId, 100,200,300,400);
        when(userService.getDeposits(validUserId)).thenReturn(userDeposit);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/deposits/" + validUserId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetDepositOfUser_NotFound() throws Exception {

        when(userService.getDeposits(invalidUserId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/deposits/" + invalidUserId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetWithdrawOfUser_Success() throws Exception {

        UserWithdraw userWithdraw = new UserWithdraw(validUserId, 100,200,300,400);
        when(userService.getWithdraws(validUserId)).thenReturn(userWithdraw);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/withdraws/" + validUserId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetWithdrawOfUser_NotFound() throws Exception {

        when(userService.getWithdraws(invalidUserId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/withdraws/" + invalidUserId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}