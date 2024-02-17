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

public class ModifyPointCommand extends BaseCommand implements MessageActionable{
    private final boolean add;
    public ModifyPointCommand(@NonNull CommandAccessLevel accessLevel, @NonNull String description, boolean add) {
        super(accessLevel, description, new CommandPatternType[]{
                CommandPatternType.COMMAND,
                CommandPatternType.MENTION,
                CommandPatternType.INT
        });
        this.add = add;
    }

    public void action(MessageReceivedEvent event) throws InvalidUsageException, BadUsageException {
        String[] prompts = getPrompt(event);

        if(!isUsageValid(event.getMessage().getContentRaw().strip())) throw new InvalidUsageException("<@User> <Jumlah poin>");

        MemberDAO memberDAO = new MemberDAO();
        String target_id = getDiscordIdFromMention(prompts[1]);
        Member target = memberDAO.getByDiscordId(target_id);
        if(target == null) target = memberDAO.addData(new Member(target_id));
        assert target != null;

        int point = Integer.parseInt(prompts[2]);
        try{
            if (this.add){
                memberDAO.updateData(target.addPoint(point));
                event.getMessage().reply("Berhasil menambahkan poin untuk <@"+target_id+"> sebanyak "+point).queue();
            } else{
                memberDAO.updateData(target.removePoint(point));
                event.getMessage().reply("Berhasil mengurangi poin <@"+target_id+"> sebanyak "+point).queue();
            }
            LeaderboardHook leaderboardHook = HooksUtil.getHook(LeaderboardHook.class);
            if(leaderboardHook != null) leaderboardHook.update();
        } catch (BadUsageException e){
            throw new BadUsageException(e.getMessage()+String.format(". Sisa poin <@%s>: %d", target_id, point));
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
