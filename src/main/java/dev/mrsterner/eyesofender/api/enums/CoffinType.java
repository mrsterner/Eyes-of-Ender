package dev.mrsterner.eyesofender.api.enums;

import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;

public enum CoffinType implements StringIdentifiable {
    HEAD("head", 2),
    FOOT("foot", 1);

    public static final CoffinType[] VALUES = values();
    private final String name;
    private final int opposite;
    public static final EnumProperty<CoffinType> COFFIN_TYPE = EnumProperty.of("type", CoffinType.class);

    private CoffinType(String string2, int j) {
        this.name = string2;
        this.opposite = j;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public CoffinType getOpposite() {
        return VALUES[this.opposite];
    }
}