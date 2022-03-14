package com.masterskayadudoshka.masterskaya.bot;

import com.masterskayadudoshka.masterskaya.command.CommandContainer;
import com.masterskayadudoshka.masterskaya.service.SendBotMessageServiceImpl;
import com.masterskayadudoshka.masterskaya.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    public DudoshkaBot(TelegramUserService userService) {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this), userService);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();
                commandContainer.retrieveCommand(commandIdentifier).execute(update);
                SendMessage sendMessage = new SendMessage();
            } else {
                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
            }
        } else if (update.hasCallbackQuery()) {
            try {
                String callbackQuery = update.getCallbackQuery().getData();

                if (callbackQuery.equals("Каталог")) {
                    List<InputMedia> inputMedia = new ArrayList<>();
                    inputMedia.add(new InputMediaPhoto("src/main/resources/images/img.jpeg"));
                    inputMedia.add(new InputMediaPhoto("src/main/resources/images/img1.jpeg"));
                    inputMedia.add(new InputMediaPhoto("src/main/resources/images/img2.jpeg"));
                    inputMedia.add(new InputMediaPhoto("src/main/resources/images/img3.jpeg"));
                    SendMediaGroup sendMediaGroup = new SendMediaGroup();
                    sendMediaGroup.setMedias(inputMedia);
                    execute(sendMediaGroup);
                }
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText(callbackQuery);
                sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
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
