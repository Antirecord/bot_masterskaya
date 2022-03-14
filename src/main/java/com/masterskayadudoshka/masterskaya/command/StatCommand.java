package com.masterskayadudoshka.masterskaya.command;

import com.masterskayadudoshka.masterskaya.service.SendBotMessageService;
import com.masterskayadudoshka.masterskaya.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StatCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService userService;

    public final static String STAT_MESSAGE = "Нашим ботом пользуется %s человек";

    public StatCommand(SendBotMessageService sendBotMessageService, TelegramUserService userService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {
        int activeUserCount = userService.retrieveAllActiveUsers().size();
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),
                String.format(STAT_MESSAGE, activeUserCount));

    }
}
