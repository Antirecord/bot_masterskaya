package com.masterskayadudoshka.masterskaya.command;

import com.masterskayadudoshka.masterskaya.model.TelegramUser;
import com.masterskayadudoshka.masterskaya.service.SendBotMessageService;
import com.masterskayadudoshka.masterskaya.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class StartCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService userService;
    public final static String START_MESSAGE = "Добро пожаловать на Masterskaya Dudoshka. Здесь будет " +
            "возможность просматривать каталог, оформить заказ, и многое другое";

    public StartCommand(SendBotMessageService sendBotMessageService, TelegramUserService userService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        userService.findByChatId(chatId).ifPresentOrElse(
                user -> {
                    user.setActive(true);
                    userService.save(user);
                },
                () -> {
                    TelegramUser user = new TelegramUser();
                    user.setChatId(chatId);
                    user.setActive(true);
                    userService.save(user);
                });
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Тык");
        inlineKeyboardButton.setCallbackData("result");

        sendBotMessageService.sendMessage(chatId, START_MESSAGE);
    }
}
