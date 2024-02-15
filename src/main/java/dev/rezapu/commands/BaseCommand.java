package dev.rezapu.commands;

import dev.rezapu.enums.CommandAccessLevel;
import dev.rezapu.enums.CommandPatternType;
import dev.rezapu.utils.CommandsUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public abstract class BaseCommand {
    @Getter @NonNull
    protected final CommandAccessLevel accessLevel;
    @Getter @Setter @NonNull
    protected String description;
    @Getter @NonNull
    protected final CommandPatternType[] commandPatternTypes;

    protected boolean isUsageValid(String message){
        return CommandsUtil.match(message, commandPatternTypes);
    }
}
