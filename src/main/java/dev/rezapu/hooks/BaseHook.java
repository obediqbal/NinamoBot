package dev.rezapu.hooks;

import dev.rezapu.model.Hook;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;

@RequiredArgsConstructor
public abstract class BaseHook {
    @Getter
    protected Message message;
    @Getter
    @NonNull
    protected HookType type;
    @Getter
    protected Hook model;
    public abstract BaseHook connect(JDA jda, Hook hook) throws InstantiationException;
    public abstract void send(GuildMessageChannel channel);
    public abstract void update();
}
