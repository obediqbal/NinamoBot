package dev.rezapu;

import dev.rezapu.commands.PingCommand;
import dev.rezapu.dao.MemberDAO;
import dev.rezapu.model.Member;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        JDABuilder builder = JDABuilder.createDefault("MTIwNTMyOTk1ODIxNDQzODk0NA.G9oy2E.azy41sYisoUJL8bfBkeCW0pKlSWWVBixrqdkJ4");

        JDA jda = builder
                .setActivity(Activity.listening("your heatbeat"))
                .addEventListeners(new PingCommand())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();

        jda.updateCommands().addCommands(
                Commands.slash("ping", "Calculate ping of the bot")
        ).queue();

        jda.awaitReady();
        System.out.println("Ready");
}
    }
