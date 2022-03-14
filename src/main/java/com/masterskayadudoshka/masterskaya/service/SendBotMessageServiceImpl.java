package com.masterskayadudoshka.masterskaya.service;

import com.masterskayadudoshka.masterskaya.bot.DudoshkaBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static com.masterskayadudoshka.masterskaya.command.CommandName.START;

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
        if (message.equals(START.getCommandName())) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText("Каталог");
            inlineKeyboardButton.setCallbackData("Просмотр каталога");

            InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
            inlineKeyboardButton.setText("Заказ");
            inlineKeyboardButton.setCallbackData("Оформление заказа");

            InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
            inlineKeyboardButton.setText("Оплата");
            inlineKeyboardButton.setCallbackData("Оплата заказа");

            InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
            inlineKeyboardButton.setText("Контакты");
            inlineKeyboardButton.setCallbackData("Контакты");


            List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
            keyboardButtonsRow1.add(inlineKeyboardButton);
            keyboardButtonsRow1.add(inlineKeyboardButton1);
            keyboardButtonsRow1.add(inlineKeyboardButton2);
            keyboardButtonsRow1.add(inlineKeyboardButton3);

            List<List<InlineKeyboardButton>> rowList= new ArrayList<>();
            rowList.add(keyboardButtonsRow1);

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(rowList);
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        }

        try {
            dudoshkaBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
