package dev.rezapu.listeners;

import dev.rezapu.commands.PingCommand;
import dev.rezapu.enums.CommandAccessLevel;
import dev.rezapu.exceptions.UnauthorizedException;
import dev.rezapu.utils.CommandsUtil;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class PingListener extends BaseListener {
    public PingListener(){
        PingCommand pingCommand = new PingCommand(CommandAccessLevel.PUBLIC, "Pings the server");
        CommandsUtil.addCommand(pingCommand);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event){
        try{
            if (event.getMessage().getContentRaw().equals(".ping")){
                PingCommand pingCommand = CommandsUtil.getCommand(PingCommand.class, event);
                pingCommand.action(event);
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("ping")){
            try {
                PingCommand pingCommand = CommandsUtil.getCommand(PingCommand.class, event);
                pingCommand.action(event);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
