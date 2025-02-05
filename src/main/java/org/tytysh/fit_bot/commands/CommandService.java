package org.tytysh.fit_bot.commands;

import lombok.Data;
import org.tytysh.fit_bot.config.ChatSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.tytysh.fit_bot.BotConstance.COMMAND_NOT_FOUND;
import static org.tytysh.fit_bot.commands.Command.*;

@Service
public class CommandService {

    private static final String CMD_PREFIX = "/";


    public CommandService(List<Command> commands) {
        init(commands);
    }

    private Map<String, Command> registry;



    private void init(List<Command> commands) {
        registry = new HashMap<>();
        for (Command command : commands) {
            registry.put(command.getCommandName(), command);
        }
    }

    public boolean isCommand(String msg) {
        msg = msg.trim();
        return msg.startsWith(CMD_PREFIX);
    }

    public String executeCommand(String msg, String chatId) {
        if (msg == null || msg.trim().isEmpty() || chatId.equals("0")) {
            return COMMAND_NOT_FOUND;
        }
        return "Будь ласка, введіть команду або текст.";
    }


    public void onMessage(ChatSession chatSession) {
        String msg = chatSession.getLastMessage();

        if (isCommand(msg)) {
            CmdKey cmdKey = parseCommand(msg);
            Command command = registry.get(cmdKey.getCommandKey());
            if (command != null) {
                command.execute(chatSession, cmdKey.getCommandArgs());
                chatSession.setMessageProcessed();
            }
        }
    }

    private CmdKey parseCommand(String msg) {
        Map<String, String> commandArgs = new HashMap<>();
        CmdKey cmdKey = new CmdKey();
        cmdKey.setCommandArgs(commandArgs);
        int paramsStart = msg.indexOf(CMD_ARG_SYMBOL);
        if (paramsStart == -1) {
            cmdKey.setCommandKey(msg);
            return cmdKey;
        }
        cmdKey.setCommandKey(msg.substring(0, paramsStart));
        String[] parts = msg.substring(paramsStart + CMD_ARG_SYMBOL.length()).split(CMD_ARG_SEPARATOR);
        for (int i = 0; i < parts.length; i++) {
            String[] arg = parts[i].split(CMD_ARG_EQUALS);
            if (arg.length == 2) {
                commandArgs.put(arg[0], arg[1]);
            }
        }
        return cmdKey;
    }

    @Data
    private class CmdKey {
        String commandKey;
        Map<String, String> commandArgs;
    }
}

