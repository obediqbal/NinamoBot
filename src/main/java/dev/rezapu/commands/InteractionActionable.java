package dev.rezapu.commands;

import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;

public interface InteractionActionable extends ActionableCommands {
    public void action(GenericCommandInteractionEvent event);
}
