package dev.rezapu.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PingCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if (event.getMessage().getContentRaw().equals(".ping")){
            long time = System.currentTimeMillis();
            event.getMessage().reply("Pong!")
                    .flatMap(v ->
                            v.editMessageFormat("Pong in %d ms", System.currentTimeMillis()-time)
                    ).queue();
        }
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("ping")){
            long time = System.currentTimeMillis();
            event.reply("Pong!").setEphemeral(true)
                    .flatMap(v ->
                            event.getHook().editOriginalFormat("Pong in %d ms", System.currentTimeMillis()-time)
                    ).queue();
        }
    }
}
