package dev.rezapu.commands;

import dev.rezapu.dao.MemberDAO;
import dev.rezapu.enums.CommandAccessLevel;
import dev.rezapu.enums.CommandPatternType;
import dev.rezapu.exceptions.BadUsageException;
import dev.rezapu.exceptions.InvalidUsageException;
import dev.rezapu.model.Member;
import dev.rezapu.utils.CommandsUtil;
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
        String[] prompts = CommandsUtil.getPrompt(event);

        if(!isUsageValid(event.getMessage().getContentRaw().strip())) throw new InvalidUsageException("<@User> <Jumlah poin>");

        MemberDAO memberDAO = new MemberDAO();
        String target_id = prompts[1].substring(2, prompts[1].length()-1);
        Member target = memberDAO.getByDiscordId(target_id);
        if(target == null) memberDAO.addData(new Member(target_id, ""));
        assert target != null;

        int point = Integer.parseInt(prompts[2]);
        if (this.add){
            memberDAO.updateData(target.addPoint(point));
            event.getMessage().reply("Berhasil menambahkan poin untuk <@"+target.getDiscord_id()+"> sebanyak "+prompts[2]).queue();
        }
        else{
            try{
                memberDAO.updateData(target.removePoint(Integer.parseInt(prompts[2])));
                event.getMessage().reply("Berhasil mengurangi poin <@"+target.getDiscord_id()+"> sebanyak "+prompts[2]).queue();
            }catch(Exception e){
                throw new BadUsageException("<@"+target.getDiscord_id()+"> tidak memiliki cukup poin");
            }
        }
    }
}