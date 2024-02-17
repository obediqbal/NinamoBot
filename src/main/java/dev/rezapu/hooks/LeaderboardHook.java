package dev.rezapu.hooks;

import dev.rezapu.dao.MemberDAO;
import dev.rezapu.model.Member;
import lombok.Getter;
import lombok.NonNull;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import net.dv8tion.jda.api.entities.channel.unions.GuildChannelUnion;

import java.awt.*;
import java.util.List;


public class LeaderboardHook extends BaseHook {
    @Getter
    private GuildMessageChannel channel;
    @Getter
    private Message message;
    @Getter
    private MessageEmbed messageEmbed;

    public LeaderboardHook(){
        this.messageEmbed = new EmbedBuilder()
                .setTitle("Guild Leaderboard")
                .setColor(Color.RED)
                .build();
    }

    public void send(GuildMessageChannel channel){
        this.channel = channel;
        this.channel.sendMessageEmbeds(messageEmbed)
                .queue(message -> {
                    this.message = message;
                    this.update();
                });
    }

    public void update(){
        assert this.message != null;
        updateEmbed();

        this.message.editMessageEmbeds(messageEmbed).queue();
    }

    private void updateEmbed(){
        MemberDAO memberDAO = new MemberDAO();
        List<Member> memberList = memberDAO.getAllSortedByPoints();
        EmbedBuilder embedBuilder = new EmbedBuilder(this.messageEmbed).clearFields();
        int rank = 1;
        for(Member member: memberList){
            net.dv8tion.jda.api.entities.Member discordMember = this.message.getGuild().getMemberById(member.getDiscord_id());

            if(discordMember == null) continue;
            MessageEmbed.Field field = new MessageEmbed.Field(
                    String.format("%d. %s%s",
                            rank,
                            discordMember.getEffectiveName(),
                            member.getIgn().equals("-")?"":String.format("(%s)", member.getIgn())),
                    String.format("%d point", member.getPoint()),
                    false);
            embedBuilder.addField(field);
            rank++;
        }
        this.messageEmbed = embedBuilder.build();
    }
}
