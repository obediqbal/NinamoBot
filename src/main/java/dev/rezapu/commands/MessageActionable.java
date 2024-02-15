package dev.rezapu.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface MessageActionable {
    public void action(MessageReceivedEvent event) throws Exception;
}
