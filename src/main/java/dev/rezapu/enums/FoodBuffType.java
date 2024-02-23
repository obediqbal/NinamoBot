package dev.rezapu.enums;

import dev.rezapu.exceptions.BadUsageException;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FoodBuffType {
    MAX_HP("hp", "Max HP"),
    MAX_MP("mp", "Max MP"),
    AMPR("ampr", "Attack MP Recovery"),
    CRITICAL_RATE("cr", "Critical Rate"),
    MATK("matk", "Magic Attack"),
    ATK("atk", "Attack"),
    WATK("watk", "Weapon Attack"),
    STR("str", "Strength"),
    DEX("dex", "Dexterity"),
    INT("int", "Intelligence"),
    AGI("agi", "Agility"),
    VIT("vit", "Vitality"),
    ACCURACY("acc", "Accuracy"),
    DTE_NEUTRAL("neutral", "Damage To Element: Neutral"),
    DTE_FIRE("fire", "Damage To Element: Fire"),
    DTE_WATER("water", "Damage To Element: Water"),
    DTE_WIND("wind", "Damage To Element: Wind"),
    DTE_DARK("dark", "Damage To Element: Dark"),
    DTE_LIGHT("light", "Damage To Element: Light"),
    MAGIC_RESIST("mresist", "Magic Resist"),
    PHYSICAL_RESIST("presist", "Physical Resist"),
    PLUS_AGGRO("paggro", "+Aggro%"),
    MINUS_AGGRO("maggro", "-Aggro%"),
    FRACTIONAL_BARRIER("fracbarrier", "Fractional Barrier"),
    DTE_EARTH("earth", "Damage To Element: Earth");

    @Getter
    @NonNull
    private final String type;
    @Getter
    @NonNull
    private final String display_name;
    public static FoodBuffType fromString(String value) throws BadUsageException{
        for(FoodBuffType type: FoodBuffType.values()){
            if(value.equalsIgnoreCase(type.getType())) return type;
        }
        throw new BadUsageException("Tidak ada buff dengan tipe "+value);
    }
}
