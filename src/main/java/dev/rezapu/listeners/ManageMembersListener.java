package dev.rezapu.listeners;

import dev.rezapu.commands.AddPointCommand;
import dev.rezapu.dao.MemberDAO;
import dev.rezapu.enums.CommandAccessLevel;
import dev.rezapu.enums.CommandPatternType;
import dev.rezapu.model.Member;
import dev.rezapu.utils.CommandsUtil;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ManageMembersListener extends BaseListener {
    public ManageMembersListener(){
        AddPointCommand addPointCommand = new AddPointCommand(CommandAccessLevel.MEMBER, "Add a specified amount of point to user");
        CommandsUtil.addCommand(addPointCommand, true, false);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        String strippedMessage =event.getMessage().getContentRaw().strip();
        String[] prompts = strippedMessage.split("\\s+");
        String command = prompts[0];
        try{
            switch (command) {
                case ".addp", ".addpoint", ".padd", ".pointadd", ".ap" -> {
                    AddPointCommand addPointCommand = CommandsUtil.getCommand(AddPointCommand.class);
                    if (addPointCommand != null) addPointCommand.action(event);
                }
//                case ".deductp", ".deductpoint", ".pdeduct", ".pointdeduct", ".dp" -> {
//                    if(CommandPatternUtil.match(
//                            strippedMessage,
//                            CommandPatternType.COMMAND,
//                            CommandPatternType.MENTION,
//                            CommandPatternType.INT)
//                    ) {
//                        MemberDAO memberDAO = createProtectedDAO(event, MemberDAO.class);
//                        Member author = memberDAO.getByDiscordId(event.getAuthor().getId());
//                        memberDAO.updateData(author.removePoint(Integer.parseInt(prompts[1])));
//                    }
//                }
//                case ".register", ".reg" -> {
//                    MemberDAO memberDAO = createPublicDAO(MemberDAO.class);
//                    memberDAO.addData(new Member(event.getAuthor().getId(), prompts[1]));
//                }
//                case ".unregister", ".unreg" -> {
////                    memberDAO.deleteData(author);
//                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
