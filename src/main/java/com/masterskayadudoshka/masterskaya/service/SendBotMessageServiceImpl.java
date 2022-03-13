package com.masterskayadudoshka.masterskaya.service;

import com.masterskayadudoshka.masterskaya.bot.DudoshkaBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {
    private final DudoshkaBot dudoshkaBot;
    @Autowired
    public SendBotMessageServiceImpl(DudoshkaBot dudoshkaBot) {
        this.dudoshkaBot = dudoshkaBot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.enableHtml(true);

        try {
            dudoshkaBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
