package dev.rezapu.hooks;

import dev.rezapu.dao.MemberDAO;
import dev.rezapu.model.Hook;
import dev.rezapu.model.Member;
import dev.rezapu.utils.HooksUtil;
import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import net.dv8tion.jda.api.entities.channel.unions.GuildMessageChannelUnion;

import java.awt.*;
import java.util.List;
import java.util.Objects;


public class LeaderboardHook extends BaseHook {
    @Getter
    private MessageEmbed messageEmbed;

    public LeaderboardHook(){
        super(HookType.LEADERBOARD);
        this.messageEmbed = new EmbedBuilder()
                .setTitle("Guild Leaderboard")
                .setColor(Color.RED)
                .build();
    }

    public LeaderboardHook connect(JDA jda, Hook hook) throws InstantiationException{
        if(!hook.getType().getHookClass().equals(LeaderboardHook.class)) throw new InstantiationException();

        this.message = ((GuildMessageChannelUnion) Objects.requireNonNull(jda.getGuildChannelById(hook.getChannel_id())))
                .retrieveMessageById(hook.getMessage_id())
                .complete();

        this.messageEmbed = new EmbedBuilder()
                .setTitle("Guild Leaderboard")
                .setColor(Color.RED)
                .build();

        return this;
    }

    public void send(GuildMessageChannel channel){
        channel.sendMessageEmbeds(messageEmbed)
                .queue(message -> {
                    this.message = message;
                    this.update();

                    this.model = new Hook(message.getId(), message.getChannelId(), this.type);
                    HooksUtil.addHook(this);
                });
    }

    public void update(){
        this.message.getChannel().retrieveMessageById(this.message.getId())
                .queue(message -> {
                    this.message = message;
                    if(this.message == null) HooksUtil.deleteHook(this);
                    updateEmbed();
                    this.message.editMessageEmbeds(messageEmbed).queue();
                }
        );
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
