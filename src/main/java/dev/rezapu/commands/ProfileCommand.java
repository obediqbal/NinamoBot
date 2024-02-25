package dev.rezapu.commands;

import dev.rezapu.dao.MemberDAO;
import dev.rezapu.enums.CommandAccessLevel;
import dev.rezapu.enums.CommandPatternType;
import dev.rezapu.exceptions.BadUsageException;
import dev.rezapu.exceptions.InvalidUsageException;
import dev.rezapu.exceptions.NotFoundException;
import dev.rezapu.utils.CommandsUtil;
import lombok.NonNull;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ProfileCommand extends BaseCommand implements MessageActionable{
    public ProfileCommand(@NonNull CommandAccessLevel accessLevel, @NonNull String description) {
        super(accessLevel, description, new CommandPatternType[]{CommandPatternType.COMMAND, CommandPatternType.MENTION});
    }

    @Override
    public void action(MessageReceivedEvent event) throws NotFoundException, InvalidUsageException, BadUsageException {
        if (!isUsageValid(event.getMessage().getContentRaw().strip())) throw new InvalidUsageException(".profile <@User>");

        String[] prompts = getPrompt(event);
        Member target = event.getGuild().getMemberById(getDiscordIdFromMention(prompts[1]));
        if(target==null) throw new NotFoundException("Profil tak ditemukan");
        if (target.getUser().isBot()) throw new BadUsageException("Bot tidak bisa memiliki profil");

        MemberDAO memberDAO = new MemberDAO();
        String discord_id = target.getId();
        dev.rezapu.model.Member targetModel = memberDAO.getByDiscordId(discord_id);
        if(targetModel == null) targetModel = memberDAO.addData(new dev.rezapu.model.Member(discord_id));


        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setColor(Color.WHITE)
                .setThumbnail(target.getEffectiveAvatarUrl())
                .setTitle(target.getEffectiveName())
                .addField("Username", target.getUser().getName(), false)
                .addField("IGN", targetModel.getIgn(), false)
                .addField("Point", Integer.toString(targetModel.getPoint()), false)
                .addField("Raid", Integer.toString(targetModel.getRaid()), false);

        event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }
}
