package com.masterskayadudoshka.masterskaya.command;

import com.masterskayadudoshka.masterskaya.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    public final static String START_MESSAGE = "Добро пожаловать на Masterskaya Dudoshka. Здесь будет " +
            "возможность просматривать каталог, оформить заказ, и многое другое";

    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);
    }
}
