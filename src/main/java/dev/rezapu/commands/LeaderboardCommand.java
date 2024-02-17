package dev.rezapu.commands;

import dev.rezapu.enums.CommandAccessLevel;
import dev.rezapu.enums.CommandPatternType;
import dev.rezapu.exceptions.InvalidUsageException;
import dev.rezapu.hooks.LeaderboardHook;
import lombok.NonNull;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class LeaderboardCommand extends BaseCommand implements MessageActionable{
    public LeaderboardCommand(@NonNull CommandAccessLevel accessLevel, @NonNull String description) {
        super(accessLevel, description, new CommandPatternType[]{CommandPatternType.COMMAND, CommandPatternType.CHANNEL});
    }

    @Override
    public void action(MessageReceivedEvent event) throws InvalidUsageException{
        if(!isUsageValid(event.getMessage().getContentRaw().strip())) throw new InvalidUsageException(".leaderboard <#Channel>");

        String[] prompts = getPrompt(event);

        LeaderboardHook leaderboardHook = new LeaderboardHook();
        GuildMessageChannel guildMessageChannel = event.getGuild().getChannelById(GuildMessageChannel.class, getChannelIdFromMention(prompts[1]));
        assert guildMessageChannel != null;
        leaderboardHook.send(guildMessageChannel);
    }
}
