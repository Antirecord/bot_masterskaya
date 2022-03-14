package com.masterskayadudoshka.masterskaya.command;

public enum CommandName {
    START("/start"),
    HELP("/help"),
    STOP("/stop"),
    STAT("/stat"),
    NO("nocommand");


    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
