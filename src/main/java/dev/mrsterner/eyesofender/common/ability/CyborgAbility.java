package dev.mrsterner.eyesofender.common.ability;

import dev.mrsterner.eyesofender.api.interfaces.CyborgUser;
import dev.mrsterner.eyesofender.api.registry.CyborgKnowledge;
import dev.mrsterner.eyesofender.common.networking.packet.CyborgAbilityPacket;
import dev.mrsterner.eyesofender.common.registry.EOERegistries;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class CyborgAbility {
    public CyborgKnowledge cyborgKnowledge;
    public int cyborgLevel;

    public CyborgAbility(CyborgKnowledge cyborgKnowledge, int cyborgLevel) {
        this.cyborgKnowledge = cyborgKnowledge;
        this.cyborgLevel = cyborgLevel;
    }

    public boolean use(LivingEntity user) {
        Optional<CyborgUser> abilityUserOptional = CyborgUser.of(user);
        if (!user.world.isClient && abilityUserOptional.isPresent()) {
            CyborgAbilityPacket.send(user, toTag(new NbtCompound()));
        }
        return cyborgKnowledge.canUse(user);
    }

    public NbtCompound toTag(NbtCompound tag) {
        tag.putString("CyborgKnowledge", cyborgKnowledge.getId().toString());
        tag.putInt("CyborgLevel", cyborgLevel);
        return tag;
    }

    public static CyborgAbility fromTag(NbtCompound tag) {
        return tag.isEmpty() ? null : new CyborgAbility(
                EOERegistries.CYBORG_ABILITY.get(new Identifier(tag.getString("CyborgKnowledge"))),
                tag.getInt("CyborgLevel"));
    }
}
