package dev.rezapu.commands;

import dev.rezapu.enums.CommandAccessLevel;
import lombok.NonNull;

public class AddRaidCommand extends ModifyRaidCommand{
    public AddRaidCommand(@NonNull CommandAccessLevel accessLevel, @NonNull String description) {
        super(accessLevel, description, true);
    }
}
