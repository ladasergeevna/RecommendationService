package org.skypro.recommendationService.telegramBot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotRegistrationConfig {

    private final RecommendationTelegramBot bot;

    public BotRegistrationConfig(RecommendationTelegramBot bot) {
        this.bot = bot;
        registerBot();
    }

    private void registerBot() {
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(bot);
            System.out.println("Бот зарегистрирован");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}