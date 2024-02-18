package dev.rezapu;

import dev.rezapu.commands.PingCommand;
import dev.rezapu.enums.CommandAccessLevel;
import dev.rezapu.listeners.ManageFoodBuffListener;
import dev.rezapu.listeners.ManageMembersListener;
import dev.rezapu.listeners.PingListener;
import dev.rezapu.utils.CommandsUtil;
import dev.rezapu.utils.HooksUtil;
import dev.rezapu.utils.Utils;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        Dotenv.configure().ignoreIfMissing().systemProperties().load();
        Utils.setPropertiesFromEnv("BOT_TOKEN", "DB_CONNECTION_URL", "DB_USERNAME", "DB_PASSWORD");

        JDABuilder builder = JDABuilder.createDefault(System.getProperty("BOT_TOKEN"));

        JDA jda = builder
                .setActivity(Activity.playing("your eardrum"))
                .addEventListeners(new PingListener(), new ManageMembersListener(), new ManageFoodBuffListener())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_PRESENCES)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build();

        jda.updateCommands().addCommands(
                Commands.slash("ping", "Calculate ping of the bot")
        ).queue();

        jda.awaitReady();
        HooksUtil.initHooks(jda);

        System.out.println("Ready");
}
    }
