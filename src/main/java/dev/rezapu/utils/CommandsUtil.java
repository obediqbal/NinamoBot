package dev.rezapu.utils;

import dev.rezapu.commands.ActionableCommands;
import dev.rezapu.commands.BaseCommand;
import dev.rezapu.commands.InteractionActionable;
import dev.rezapu.commands.MessageActionable;
import dev.rezapu.enums.CommandAccessLevel;
import dev.rezapu.enums.CommandPatternType;
import dev.rezapu.exceptions.UnauthorizedException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.Route;

import javax.management.relation.Role;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class CommandsUtil {
    private static final CommandsUtil commandsUtil = new CommandsUtil();
    private final Map<Class<? extends BaseCommand>, BaseCommand> commands;

    private CommandsUtil(){
        commands = new HashMap<>();
    }

    public static <T extends BaseCommand> T getCommand(Class<T> clazz, MessageReceivedEvent event) throws UnauthorizedException, InstantiationException {
        return getCommand(clazz, event.getMember(), MessageActionable.class);
    }

    public static <T extends BaseCommand> T getCommand(Class<T> clazz, GenericCommandInteractionEvent event) throws UnauthorizedException, InstantiationException{
        return getCommand(clazz, event.getMember(), InteractionActionable.class);
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

    @SuppressWarnings("unchecked")
    private static <T extends BaseCommand> T getCommand(Class<T> clazz, Member member, Class<? extends ActionableCommands> clazzAct) throws UnauthorizedException, InstantiationException{
        if(clazzAct.isAssignableFrom(clazz)){
            T command = (T) commandsUtil.commands.get(clazz);
            if (CommandsUtil.isAuthorized(Objects.requireNonNull(member), member.getGuild(), command.getAccessLevel()))
                return command;
            throw new UnauthorizedException();
        }
        throw new InstantiationException();
    }

    private static boolean isAuthorized(Member member, Guild guild , CommandAccessLevel commandAccessLevel){
        if(member.getUser().isBot()) return false;



        CommandAccessLevel memberLevel;
        if(member.isOwner() || member.hasPermission(Permission.ADMINISTRATOR)) memberLevel = CommandAccessLevel.ADMIN;
        else if (member.getRoles().contains(member.getGuild().getRoleById("1205192109745766432"))) memberLevel = commandAccessLevel.MOD;
        else memberLevel = CommandAccessLevel.MEMBER;

        return memberLevel.getLevel() >= commandAccessLevel.getLevel();
    }
}