package dev.rezapu.listeners;

import dev.rezapu.commands.*;
import dev.rezapu.enums.CommandAccessLevel;
import dev.rezapu.exceptions.BadUsageException;
import dev.rezapu.exceptions.InvalidUsageException;
import dev.rezapu.exceptions.NotFoundException;
import dev.rezapu.exceptions.UnauthorizedException;
import dev.rezapu.utils.CommandsUtil;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ManageMembersListener extends BaseListener {
    public ManageMembersListener(){
        AddPointCommand addPointCommand = new AddPointCommand(CommandAccessLevel.ADMIN, "Add a specified amount of point to user");
        DeductPointCommand deductPointCommand = new DeductPointCommand(CommandAccessLevel.ADMIN, "Deduct a specified amount of point from user");
        ProfileCommand profileCommand = new ProfileCommand(CommandAccessLevel.PUBLIC, "Shows the profile of the selected user");
        LeaderboardCommand leaderboardCommand = new LeaderboardCommand(CommandAccessLevel.ADMIN, "Shows the guild leaderboard");

        CommandsUtil.addCommands(addPointCommand, deductPointCommand, profileCommand, leaderboardCommand);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        String strippedMessage = event.getMessage().getContentRaw().strip();
        String[] prompts = strippedMessage.split("\\s+");
        String command = prompts[0];
        try{
            switch (command) {
                case ".addp", ".addpoint", ".padd", ".pointadd", ".ap" -> {
                    CommandsUtil.getCommand(AddPointCommand.class, event).action(event);
                }
                case ".deductp", ".deductpoint", ".pdeduct", ".pointdeduct", ".dp" -> {
                    CommandsUtil.getCommand(DeductPointCommand.class, event).action(event);
                }
                case ".addr", ".addraid", ".radd", ".raidadd", "ar" -> {
                    CommandsUtil.getCommand(AddRaidCommand.class, event).action(event);
                }
                case ".deductr", ".deductraid", ".rdeduct", ".raiddeduct", ".dr" -> {
                    CommandsUtil.getCommand(DeductRaidCommand.class, event).action(event);
                }
                case ".p", ".profile" -> {
                    CommandsUtil.getCommand(ProfileCommand.class, event).action(event);
                }
                case ".lb", ".leaderboard" -> {
                    CommandsUtil.getCommand(LeaderboardCommand.class, event).action(event);
                }
            }
        }
        catch (NotFoundException | BadUsageException | InvalidUsageException | UnauthorizedException e){
            event.getMessage().reply(e.getMessage()).queue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
