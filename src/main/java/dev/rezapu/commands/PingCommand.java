package dev.rezapu.commands;

import dev.rezapu.enums.CommandAccessLevel;
import dev.rezapu.enums.CommandPatternType;
import dev.rezapu.exceptions.InvalidUsageException;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand extends BaseCommand implements MessageActionable, InteractionActionable {
    public PingCommand(CommandAccessLevel accessLevel, String description) {
        super(accessLevel, description, new CommandPatternType[]{CommandPatternType.COMMAND});
    }

    @Override
    public void action(MessageReceivedEvent event) throws InvalidUsageException {
        if(!isUsageValid(event.getMessage().getContentRaw().strip())) throw new InvalidUsageException(".ping");
        long time = System.currentTimeMillis();
        event.getMessage().reply("Pong!")
                .flatMap(v ->
                        v.editMessageFormat("Pong in %d ms", System.currentTimeMillis()-time)
                ).queue();
    }

    @Override
    public void action(GenericCommandInteractionEvent event) {
        long time = System.currentTimeMillis();
        event.reply("Pong!").setEphemeral(true)
                .flatMap(v ->
                        event.getHook().editOriginalFormat("Pong in %d ms", System.currentTimeMillis()-time)
                ).queue();
    }
}
