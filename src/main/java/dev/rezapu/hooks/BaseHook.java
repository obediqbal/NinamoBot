package dev.rezapu.hooks;

import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;

public abstract class BaseHook {
    public abstract void send(GuildMessageChannel channel);
    public abstract void update();
}
