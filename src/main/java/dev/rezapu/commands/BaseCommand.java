package dev.rezapu.commands;

import dev.rezapu.enums.CommandAccessLevel;
import dev.rezapu.enums.CommandPatternType;
import dev.rezapu.utils.CommandsUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

@AllArgsConstructor
public abstract class BaseCommand {
    @Getter @NonNull
    protected final CommandAccessLevel accessLevel;
    @Getter @Setter @NonNull
    protected String description;
    @Getter @NonNull
    protected final CommandPatternType[] commandPatternTypes;

    protected boolean isUsageValid(String message){
        return match(message, commandPatternTypes);
    }

    protected String getDiscordIdFromMention(String mention){
        return mention.substring(2, mention.length()-1);
    }
    protected String getChannelIdFromMention(String mention){return getDiscordIdFromMention(mention);}

    protected String[] getPrompt(MessageReceivedEvent event){
        String strippedMessage = event.getMessage().getContentRaw().strip().toLowerCase();
        return strippedMessage.split("\\s+");
    }

    private boolean match(String target, CommandPatternType... patterns){
        StringBuilder patternBuilder = new StringBuilder();
        patternBuilder.append("^\s*");
        for(int i = 0; i<patterns.length; i++){
            CommandPatternType pattern = patterns[i];
            switch(pattern){
                case INT -> patternBuilder.append("\\d+");
                case COMMAND -> patternBuilder.append("\\.\\w+");
                case MENTION -> patternBuilder.append("<@\\d+>");
                case STRING -> patternBuilder.append("\\w+");
                case CHANNEL -> patternBuilder.append("<#\\d+>");
            }
            if(i!=patterns.length-1) patternBuilder.append("\\s+");
            else patternBuilder.append("\\s*$");
        }
        return Pattern.compile(patternBuilder.toString()).matcher(target).matches();
    }
}
