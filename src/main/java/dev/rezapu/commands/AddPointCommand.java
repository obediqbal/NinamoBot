package dev.rezapu.commands;

import dev.rezapu.enums.CommandAccessLevel;
import lombok.NonNull;

public class AddPointCommand extends ModifyPointCommand{
    public AddPointCommand(@NonNull CommandAccessLevel accessLevel, @NonNull String description) {
        super(accessLevel, description, true);
    }
}
