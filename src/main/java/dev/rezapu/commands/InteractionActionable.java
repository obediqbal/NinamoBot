package dev.rezapu.commands;

import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;

public interface InteractionActionable {
    public void action(GenericCommandInteractionEvent event);
}
