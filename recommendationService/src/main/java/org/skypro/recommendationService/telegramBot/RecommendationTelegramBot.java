package org.skypro.recommendationService.telegramBot;

import org.skypro.recommendationService.dto.RecommendationDto;
import org.skypro.recommendationService.model.User;
import org.skypro.recommendationService.service.RecommendationService;
import org.skypro.recommendationService.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.util.List;
import java.util.UUID;
/**
 * Реализация Телеграм-бота для обработки команд и отправки рекомендаций пользователям.
 */
@Component
public class RecommendationTelegramBot extends TelegramLongPollingBot {

    private final UserBotService userBotService;
    private final RecommendationService recommendationService;
    private final TelegramBotProperties properties;
    public RecommendationTelegramBot(UserBotService userBotService,
                                     RecommendationService recommendationService,
                                     TelegramBotProperties properties) {

        this.userBotService = userBotService;
        this.recommendationService = recommendationService;
        this.properties = properties;
        System.out.println("Bot username: " + properties.getUsername());
        System.out.println("Bot token: " + properties.getToken());
    }

    @Override
    public String getBotUsername() {
        return properties.getUsername();
    }

    @Override
    public String getBotToken() {
        return properties.getToken();
    }

    /**
     * Обрабатывает входящие обновления (сообщения).
     *
     * @param update входящее обновление от Telegram.
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            System.out.println("Получено сообщение: " + messageText);
            System.out.println("Received update: " + update);

            if (messageText.startsWith("/start")) {
                sendMessage(chatId, "Welcome to RecommendationService Bot! Use /recommend <username> to get recommendations.");
            } else if (messageText.startsWith("/recommend")) {
                handleRecommendCommand(chatId, messageText);
            } else {
                sendMessage(chatId, "Unknown command. Please, use /recommend <username>");
            }
        } System.out.println("Received update: " + update);
    }

    /**
     * Обрабатывает команду /recommend и отправляет рекомендации пользователю.
     *
     * @param chatId идентификатор чата.
     * @param messageText текст команды.
     */

    private void handleRecommendCommand(Long chatId, String messageText) {
        String[] parts = messageText.split("\\s+");
        if (parts.length != 2) {
            sendMessage(chatId, "Пожалуйста, используйте формат: /recommend username");
            return;
        }
        String nameUser = parts[1];

        List<UserBot> users = userBotService.findUsersByUsername(nameUser);
        if (users.size() != 1) {
            sendMessage(chatId, "Пользователь не найден");
            return;
        }
        UserBot user = users.get(0);
        UUID userId = user.getUserId();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        List<RecommendationDto> recommendations = recommendationService.getRecommendations(userId);

        StringBuilder recommendationsText = new StringBuilder();

        if (recommendations.isEmpty()) {
            recommendationsText.append("Нет подходящих рекомендаций для вас.");
        } else {
            for (RecommendationDto rec : recommendations) {
                recommendationsText.append(rec.getText()).append("\n");
            }
        }

        String responseText = "Здравствуйте, " + firstName + " " + lastName + "!\n" +  "Новые продукты для вас:"  + "\n"
                + recommendationsText.toString();
        sendMessage(chatId, responseText);
    }
    /**
     * Отправляет сообщение в чат по его идентификатору.
     *
     * @param chatId идентификатор чата.
     * @param text текст сообщения.
     */
    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
