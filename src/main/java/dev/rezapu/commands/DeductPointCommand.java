package dev.rezapu.commands;

import dev.rezapu.enums.CommandAccessLevel;
import lombok.NonNull;

public class DeductPointCommand extends ModifyPointCommand{
    public DeductPointCommand(@NonNull CommandAccessLevel accessLevel, @NonNull String description) {
        super(accessLevel, description, false);
    }
}
