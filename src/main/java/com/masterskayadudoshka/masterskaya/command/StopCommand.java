package com.masterskayadudoshka.masterskaya.command;

import com.masterskayadudoshka.masterskaya.service.SendBotMessageService;
import com.masterskayadudoshka.masterskaya.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StopCommand implements Command{
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService userService;
    private final String STOP_MESSAGE = "Подписки деактивированы";

    public StopCommand(SendBotMessageService sendBotMessageService, TelegramUserService userService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        sendBotMessageService.sendMessage(chatId, STOP_MESSAGE);
        userService.findByChatId(chatId).ifPresent(
                user -> {
                    user.setActive(false);
                    userService.save(user);
                }
        );
    }
}
