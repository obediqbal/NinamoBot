package dev.rezapu.commands;

import dev.rezapu.enums.CommandAccessLevel;
import dev.rezapu.enums.CommandPatternType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public abstract class BaseCommand {
    @Getter @NonNull
    protected final CommandAccessLevel accessLevel;
    @Getter @Setter @NonNull
    protected String description;
}
