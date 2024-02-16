package dev.rezapu.enums;

public enum CommandAccessLevel {
    PUBLIC(0),
    MEMBER(1),
    MOD(2),
    ADMIN(3);

    private final int value;
    private CommandAccessLevel(int value){this.value = value;}

    public int getLevel(){return value;}
}
