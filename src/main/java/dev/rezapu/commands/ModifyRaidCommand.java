package dev.rezapu.commands;

import dev.rezapu.dao.MemberDAO;
import dev.rezapu.enums.CommandAccessLevel;
import dev.rezapu.enums.CommandPatternType;
import dev.rezapu.exceptions.BadUsageException;
import dev.rezapu.exceptions.InvalidUsageException;
import dev.rezapu.hooks.LeaderboardHook;
import dev.rezapu.model.Member;
import dev.rezapu.utils.CommandsUtil;
import dev.rezapu.utils.HooksUtil;
import lombok.NonNull;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ModifyRaidCommand extends BaseCommand implements MessageActionable{
    private final boolean add;
    public ModifyRaidCommand(@NonNull CommandAccessLevel accessLevel, @NonNull String description, boolean add) {
        super(accessLevel, description, new CommandPatternType[]{
                CommandPatternType.COMMAND,
                CommandPatternType.MENTION,
        });
        this.add = add;
    }

    public void action(MessageReceivedEvent event) throws InvalidUsageException, BadUsageException {
        String[] prompts = getPrompt(event);

        if(!isUsageValid(event.getMessage().getContentRaw().strip())) throw new InvalidUsageException("<@User>");

        MemberDAO memberDAO = new MemberDAO();
        String target_id = getDiscordIdFromMention(prompts[1]);
        Member target = memberDAO.getByDiscordId(target_id);
        if(target == null) target = memberDAO.addData(new Member(target_id));
        assert target != null;
        try{
            if (this.add){
                memberDAO.updateData(target.incRaid());
                event.getMessage().reply("Berhasil menambahkan raid untuk <@"+target_id+">").queue();
            } else{
                memberDAO.updateData(target.decRaid());
                event.getMessage().reply("Berhasil mengurangi raid <@"+target_id+">").queue();
            }
            HooksUtil.updateHooks(LeaderboardHook.class);
        } catch (BadUsageException e){
            throw new BadUsageException(e.getMessage()+String.format(". Sisa raid <@%s>: %d", target_id, target.getRaid()));
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}