package dev.rezapu.listeners;

import dev.rezapu.commands.AddPointCommand;
import dev.rezapu.commands.DeductPointCommand;
import dev.rezapu.enums.CommandAccessLevel;
import dev.rezapu.exceptions.BadUsageException;
import dev.rezapu.exceptions.InvalidUsageException;
import dev.rezapu.exceptions.UnauthorizedException;
import dev.rezapu.utils.CommandsUtil;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ManageMembersListener extends BaseListener {
    public ManageMembersListener(){
        AddPointCommand addPointCommand = new AddPointCommand(CommandAccessLevel.MASTER, "Add a specified amount of point to user");
        DeductPointCommand deductPointCommand = new DeductPointCommand(CommandAccessLevel.MASTER, "Deduct a specified amount of point from user");

        CommandsUtil.addCommand(addPointCommand);
        CommandsUtil.addCommand(deductPointCommand);

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        String strippedMessage = event.getMessage().getContentRaw().strip();
        String[] prompts = strippedMessage.split("\\s+");
        String command = prompts[0];
        try{
            switch (command) {
                case ".addp", ".addpoint", ".padd", ".pointadd", ".ap" -> {
                    AddPointCommand addPointCommand = CommandsUtil.getCommand(AddPointCommand.class, event);
                    addPointCommand.action(event);
                }
                case ".deductp", ".deductpoint", ".pdeduct", ".pointdeduct", ".dp" -> {
                    DeductPointCommand deductPointCommand = CommandsUtil.getCommand(DeductPointCommand.class, event);
                    deductPointCommand.action(event);
                }
            }
        }
        catch (BadUsageException | InvalidUsageException | UnauthorizedException e){
            event.getMessage().reply(e.getMessage()).queue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
