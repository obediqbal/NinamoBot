package dev.rezapu.commands;

import dev.rezapu.dao.FoodBuffDAO;
import dev.rezapu.enums.CommandAccessLevel;
import dev.rezapu.enums.CommandPatternType;
import dev.rezapu.enums.FoodBuffType;
import dev.rezapu.exceptions.BadUsageException;
import dev.rezapu.exceptions.InvalidUsageException;
import dev.rezapu.model.FoodBuff;
import lombok.NonNull;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;
import java.util.Map;


public class DisplayFoodBuffCommand extends BaseCommand implements MessageActionable{
    public DisplayFoodBuffCommand(@NonNull CommandAccessLevel accessLevel, @NonNull String description) {
        super(accessLevel, description, new CommandPatternType[]{CommandPatternType.COMMAND, CommandPatternType.STRING});
    }

    @Override
    public void action(MessageReceivedEvent event) throws InvalidUsageException, IllegalArgumentException, BadUsageException {
        String[] prompts = getPrompt(event);
        if(!isUsageValid(event.getMessage().getContentRaw().strip().toLowerCase())) throw new InvalidUsageException(".buff <all|type>");

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setColor(Color.GRAY)
                .setTitle("Food Buffs");
        FoodBuffDAO foodBuffDAO = new FoodBuffDAO();

        switch (prompts[1]){
            case "all" -> {
                Map<FoodBuffType, List<FoodBuff>> foodBuffTypeListMap = foodBuffDAO.getAll();

                for(FoodBuffType type: FoodBuffType.values()){
                    List<FoodBuff> foodBuffs = foodBuffTypeListMap.get(type);
                    if(foodBuffs==null || foodBuffs.isEmpty()) continue;
                    load(embedBuilder, foodBuffs.get(0).getType(), foodBuffs);
                }
            }
            case "stats", "stat" -> {
                loadTypes(embedBuilder, foodBuffDAO,
                        FoodBuffType.STR,
                        FoodBuffType.AGI,
                        FoodBuffType.DEX,
                        FoodBuffType.INT,
                        FoodBuffType.VIT);
            }
            case "resist", "resis", "res" -> {
                loadTypes(embedBuilder, foodBuffDAO,
                        FoodBuffType.MAGIC_RESIST,
                        FoodBuffType.PHYSICAL_RESIST,
                        FoodBuffType.FRACTIONAL_BARRIER);
            }
            case "aggro", "agro", "aggr", "agrro" -> {
                loadTypes(embedBuilder, foodBuffDAO,
                        FoodBuffType.PLUS_AGGRO,
                        FoodBuffType.MINUS_AGGRO);
            }
            case "dte" -> {
                loadTypes(embedBuilder, foodBuffDAO,
                        FoodBuffType.DTE_WATER,
                        FoodBuffType.DTE_WIND,
                        FoodBuffType.DTE_NEUTRAL,
                        FoodBuffType.DTE_EARTH,
                        FoodBuffType.DTE_DARK,
                        FoodBuffType.DTE_LIGHT,
                        FoodBuffType.DTE_FIRE);
            }
            default -> {
                FoodBuffType type = FoodBuffType.fromString(prompts[1]);
                List<FoodBuff> foodBuffs = foodBuffDAO.getByType(type);
                load(embedBuilder, type, foodBuffs);
            }
        }
        event.getMessage().replyEmbeds(embedBuilder.build()).queue();
    }

    private void load(EmbedBuilder embedBuilder, FoodBuffType type, List<FoodBuff> foodBuffs) {
        StringBuilder stringBuilder = new StringBuilder();

        for(FoodBuff foodBuff: foodBuffs){
            stringBuilder.append(String.format("> %s `LV%d %s`\n", foodBuff.getAddress() ,foodBuff.getLevel(), foodBuff.getStats()));
        }
        embedBuilder.addField(type.getDisplay_name()+"★", stringBuilder.toString(), false);
    }

    private void loadTypes(EmbedBuilder embedBuilder, FoodBuffDAO foodBuffDAO, FoodBuffType... types){
        Map<FoodBuffType, List<FoodBuff>> foodBuffTypeListMap = foodBuffDAO
                .getByTypes(types);

        for(FoodBuffType type: types){
            List<FoodBuff> foodBuffs = foodBuffTypeListMap.get(type);
            if(foodBuffs==null || foodBuffs.isEmpty()) continue;
            load(embedBuilder, foodBuffs.get(0).getType(), foodBuffs);
        }
    }
}
