package dev.rezapu.listeners;

import dev.rezapu.commands.DisplayFoodBuffCommand;
import dev.rezapu.enums.CommandAccessLevel;
import dev.rezapu.exceptions.BadUsageException;
import dev.rezapu.exceptions.InvalidUsageException;
import dev.rezapu.exceptions.UnauthorizedException;
import dev.rezapu.utils.CommandsUtil;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ManageFoodBuffListener extends BaseListener {
    public ManageFoodBuffListener() {
        DisplayFoodBuffCommand displayFoodBuffCommand = new DisplayFoodBuffCommand(CommandAccessLevel.PUBLIC, "Displays foodbuffs");

        CommandsUtil.addCommands(displayFoodBuffCommand);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String strippedMessage = event.getMessage().getContentRaw().strip();
        String[] prompts = strippedMessage.split("\\s+");
        String command = prompts[0];
        try {
            if (".buff".equals(command)) {
                CommandsUtil.getCommand(DisplayFoodBuffCommand.class, event).action(event);
            }
        } catch (BadUsageException | InvalidUsageException | UnauthorizedException e) {
            event.getMessage().reply(e.getMessage()).queue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}