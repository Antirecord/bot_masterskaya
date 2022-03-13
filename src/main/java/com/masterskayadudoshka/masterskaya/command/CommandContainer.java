package com.masterskayadudoshka.masterskaya.command;

import com.google.common.collect.ImmutableMap;
import com.masterskayadudoshka.masterskaya.service.SendBotMessageService;

import static com.masterskayadudoshka.masterskaya.command.CommandName.*;

public class CommandContainer {
    private final ImmutableMap<String, Command> container;
    private final UnknownCommand unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService) {
        container = ImmutableMap.<String, Command> builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .build();
        this.unknownCommand = new UnknownCommand(sendBotMessageService);
    }
    public Command retrieveCommand(String commandIdentifier) {
        return container.getOrDefault(commandIdentifier, unknownCommand);
    }

}
