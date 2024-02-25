package dev.rezapu.commands;

import dev.rezapu.enums.CommandAccessLevel;
import lombok.NonNull;

public class DeductRaidCommand extends ModifyRaidCommand{
    public DeductRaidCommand(@NonNull CommandAccessLevel accessLevel, @NonNull String description) {
        super(accessLevel, description, false);
    }
}
