package dev.rezapu.utils;

import dev.rezapu.commands.BaseCommand;
import dev.rezapu.commands.InteractionActionable;
import dev.rezapu.commands.MessageActionable;
import dev.rezapu.enums.CommandPatternType;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CommandsUtil {
    private static final CommandsUtil commandsUtil = build();
    private final Map<Class<? extends BaseCommand>, MessageActionable> messageCommands;
    private final Map<Class<? extends BaseCommand>, InteractionActionable> interactionCommands;

    @SuppressWarnings("unchecked")
    public static <T extends BaseCommand> T getCommand(Class<T> clazz){
        if(MessageActionable.class.isAssignableFrom(clazz)){
            return (T) commandsUtil.messageCommands.get(clazz);
        }else if(InteractionActionable.class.isAssignableFrom(clazz)){
            return (T) commandsUtil.interactionCommands.get(clazz);
        }
        return null;
    }

    private CommandsUtil(){
        messageCommands = new HashMap<>();
        interactionCommands = new HashMap<>();
    }

    private static CommandsUtil build(){
        return new CommandsUtil();
    }

    public static <T extends BaseCommand> void addCommand(T command, boolean isMessageCommand, boolean isInteractionCommand){
        if(isMessageCommand){
            commandsUtil.messageCommands.put(command.getClass(), (MessageActionable) command);
        }
        if(isInteractionCommand){
            commandsUtil.interactionCommands.put(command.getClass(), (InteractionActionable) command);
        }
    }

    public static boolean match(String target, CommandPatternType... patterns){
        StringBuilder patternBuilder = new StringBuilder();
        patternBuilder.append("^\s*");
        for(int i = 0; i<patterns.length; i++){
            CommandPatternType pattern = patterns[i];
            switch(pattern){
                case INT -> patternBuilder.append("\\d+");
                case COMMAND -> patternBuilder.append("\\.\\w+");
                case MENTION -> patternBuilder.append("<@\\d+>");
                case STRING -> patternBuilder.append("\\w+");
            }
            if(i!=patterns.length-1) patternBuilder.append("\\s+");
            else patternBuilder.append("\\s*$");
        }
        return Pattern.compile(patternBuilder.toString()).matcher(target).matches();
    }
}
