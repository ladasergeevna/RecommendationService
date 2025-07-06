package org.skypro.recommendationService.telegramBot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
/**
 * Конфигурационный класс для регистрации Telegram-бота.
 */
@Configuration
public class BotRegistrationConfig {

    private final RecommendationTelegramBot bot;

    public BotRegistrationConfig(RecommendationTelegramBot bot) {
        this.bot = bot;
        registerBot();
    }

    /**
     * Метод для регистрации бота в Telegram API.
     * Выполняется при создании экземпляра класса.
     */
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