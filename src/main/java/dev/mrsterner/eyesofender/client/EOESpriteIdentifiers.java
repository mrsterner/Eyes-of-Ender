package dev.mrsterner.eyesofender.client;

import dev.mrsterner.eyesofender.EyesOfEnder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

import java.util.*;

import static net.minecraft.screen.PlayerScreenHandler.BLOCK_ATLAS_TEXTURE;

@Environment(EnvType.CLIENT)
public class EOESpriteIdentifiers {
    public static final Map<SpriteIdentifier, Identifier> SPRITE_IDENTIFIER = new LinkedHashMap<>();

    public static final SpriteIdentifier COFFIN_SPRITE = new SpriteIdentifier(BLOCK_ATLAS_TEXTURE, EyesOfEnder.id("block/coffin"));


    public static final EOESpriteIdentifiers INSTANCE = new EOESpriteIdentifiers();
    private final List<SpriteIdentifier> identifiers = new ArrayList<>();


    public SpriteIdentifier addIdentifier(SpriteIdentifier sprite) {
        this.identifiers.add(sprite);
        return sprite;
    }

    public Collection<SpriteIdentifier> getIdentifiers() {
        return Collections.unmodifiableList(identifiers);
    }
}
