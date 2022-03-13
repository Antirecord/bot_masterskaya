package com.masterskayadudoshka.masterskaya.bot;

import com.masterskayadudoshka.masterskaya.command.CommandContainer;
import com.masterskayadudoshka.masterskaya.service.SendBotMessageServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.Locale;

import static com.masterskayadudoshka.masterskaya.command.CommandName.NO;

@Component
public class DudoshkaBot extends TelegramLongPollingBot {
    public static String COMMAND_PREFIX = "/";

    @Value("${bot.token}")
    private String token;
    @Value("${bot.username}")
    private String userName;

    private final CommandContainer commandContainer;

    public DudoshkaBot() {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this));
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)){
                String commandIdentifier = message.split(" ")[0].toLowerCase();
                commandContainer.retrieveCommand(commandIdentifier).execute(update);
                SendMessage sendMessage = new SendMessage();
            }
            else commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
        }
    }


    @Override
    public void clearWebhook() throws TelegramApiRequestException {

    }

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
