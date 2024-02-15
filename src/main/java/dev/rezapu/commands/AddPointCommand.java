package dev.rezapu.commands;

import dev.rezapu.dao.MemberDAO;
import dev.rezapu.enums.CommandAccessLevel;
import dev.rezapu.enums.CommandPatternType;
import dev.rezapu.model.Member;
import dev.rezapu.utils.CommandsUtil;
import lombok.NonNull;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class AddPointCommand extends BaseCommand implements MessageActionable{

    public AddPointCommand(@NonNull CommandAccessLevel accessLevel, @NonNull String description) {
        super(accessLevel, description);
    }

    public void action(MessageReceivedEvent event) throws Exception{
        String strippedMessage =event.getMessage().getContentRaw().strip();
        String[] prompts = strippedMessage.split("\\s+");
        if(CommandsUtil.match(
                strippedMessage,
                CommandPatternType.COMMAND,
                CommandPatternType.MENTION,
                CommandPatternType.INT)
        ){
            MemberDAO memberDAO = new MemberDAO();
            String target_id = prompts[1].substring(2, prompts[1].length()-1);
            Member target = memberDAO.getByDiscordId(target_id);
            if(target == null) memberDAO.addData(new Member(target_id, ""));
            assert target != null;
            memberDAO.updateData(target.addPoint(Integer.parseInt(prompts[2])));
        } else throw new Exception("Unknown command");
    }
}
