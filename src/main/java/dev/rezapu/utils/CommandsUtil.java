package dev.rezapu.utils;

import dev.rezapu.commands.BaseCommand;
import dev.rezapu.commands.InteractionActionable;
import dev.rezapu.commands.MessageActionable;
import dev.rezapu.enums.CommandAccessLevel;
import dev.rezapu.enums.CommandPatternType;
import dev.rezapu.exceptions.UnauthorizedException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class CommandsUtil {
    private static final CommandsUtil commandsUtil = build();
    private final Map<Class<? extends BaseCommand>, BaseCommand> commands;

    @SuppressWarnings("unchecked")
    public static <T extends BaseCommand> T getCommand(Class<T> clazz, MessageReceivedEvent event) throws UnauthorizedException, InstantiationException {
        if(MessageActionable.class.isAssignableFrom(clazz)) {
            T command = (T) commandsUtil.commands.get(clazz);
            if (CommandsUtil.isAuthorized(Objects.requireNonNull(event.getMember()), command.getAccessLevel()))
                return command;
            throw new UnauthorizedException();
        }
        throw new InstantiationException();
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseCommand> T getCommand(Class<T> clazz, GenericCommandInteractionEvent event) throws UnauthorizedException, InstantiationException{
        if(InteractionActionable.class.isAssignableFrom(clazz)){
            T command = (T) commandsUtil.commands.get(clazz);
            if (CommandsUtil.isAuthorized(Objects.requireNonNull(event.getMember()), command.getAccessLevel()))
                return command;
            throw new UnauthorizedException();
        }
        throw new InstantiationException();
    }

    public static <T extends BaseCommand> void addCommand(T command){
        commandsUtil.commands.put(command.getClass(), command);
    }

    @SafeVarargs
    public static <T extends BaseCommand> void addCommands(T... commands){
        for(T command: commands){
            addCommand(command);
        }
    }

    public static String getDiscordIdFromMention(String mention){
        return mention.substring(2, mention.length()-1);
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

    public static String[] getPrompt(MessageReceivedEvent event){
        String strippedMessage = event.getMessage().getContentRaw().strip();
        return strippedMessage.split("\\s+");
    }

    private CommandsUtil(){
        commands = new HashMap<>();
    }

    private static CommandsUtil build(){
        return new CommandsUtil();
    }

    private static boolean isAuthorized(Member member, CommandAccessLevel commandAccessLevel){
        if(member.getUser().isBot()) return false;
        if(member.isOwner()) return true;
        switch (commandAccessLevel){
            case PUBLIC -> {
                return true;
            }
            case MEMBER -> {
                // TODO
                return true;
            }
            case MOD -> {
                // TODO
                return true;
            }
            case MASTER -> {
                List<Role> roles = member.getRoles();
                for(Role role: roles){
                    if(role.hasPermission(Permission.ADMINISTRATOR)) return true;
                }
            }
        }
        return false;
    }
}
