package dev.mrsterner.eyesofender.common.components.entity;

import dev.mrsterner.eyesofender.api.enums.BodyPart;
import dev.mrsterner.eyesofender.common.registry.EOEComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class BodyComponent implements ServerTickingComponent, AutoSyncedComponent {
    private final Map<BodyPart, Map.Entry<Boolean, Integer>> bodyParts = new HashMap<>(){{
        put(BodyPart.HEAD, new SimpleEntry<>(true, 0));
        put(BodyPart.TORSO, new SimpleEntry<>(true, 0));
        put(BodyPart.LEFTLEG, new SimpleEntry<>(true, 0));
        put(BodyPart.RIGHTLEG, new SimpleEntry<>(true, 0));
        put(BodyPart.LEFTARM, new SimpleEntry<>(true, 0));
        put(BodyPart.RIGHTARM, new SimpleEntry<>(true, 0));
        put(BodyPart.EYES, new SimpleEntry<>(true, 0));
    }};

    private final PlayerEntity player;

    public BodyComponent(PlayerEntity player) {
        this.player = player;
    }


    public void setBodyPart(BodyPart bodyPart, boolean b, int i) {
        bodyParts.replace(bodyPart, new AbstractMap.SimpleEntry<>(b, i));
        EOEComponents.BODY_COMPONENT.sync(player);
    }

    public Map<BodyPart, Map.Entry<Boolean, Integer>> getBodyPart() {
        return bodyParts;
    }

    public boolean hasBodyPart(BodyPart bodyPart) {
        return bodyParts.get(bodyPart).getKey();
    }

    public int getBodyPartLevel(BodyPart bodyPart){
        return bodyParts.get(bodyPart).getValue();
    }

    public void setBodyPartLevel(BodyPart bodyPart, int i){
        bodyParts.replace(bodyPart, new AbstractMap.SimpleEntry<>(bodyParts.get(bodyPart).getKey(), i));
    }

    public void serverTick() {

    }

    public float getHeight(){
        if(!EOEComponents.BODY_COMPONENT.get(player).hasBodyPart(BodyPart.RIGHTLEG) && !EOEComponents.BODY_COMPONENT.get(player).hasBodyPart(BodyPart.LEFTLEG)){
            if(!EOEComponents.BODY_COMPONENT.get(player).hasBodyPart(BodyPart.TORSO) && !EOEComponents.BODY_COMPONENT.get(player).hasBodyPart(BodyPart.LEFTARM) && !EOEComponents.BODY_COMPONENT.get(player).hasBodyPart(BodyPart.RIGHTARM)){
                return 0.55F;
            }
            return 1.2F;
        }
        return 1.8F;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        int index = 0;
        NbtList bodyPartList = tag.getList("BodyParts", NbtType.COMPOUND);
        for(Map.Entry<BodyPart, Map.Entry<Boolean, Integer>> entry : bodyParts.entrySet()){
            NbtCompound bodyCompound = bodyPartList.getCompound(index);
            setBodyPart(entry.getKey().fromString(bodyCompound.getString("BodyPart")), entry.getValue().getKey(), entry.getValue().getValue());
            index++;
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.put("BodyParts", toNbtBody());
    }


    public NbtList toNbtBody() {
        NbtList bodyList = new NbtList();
        bodyParts.forEach((bodyParts, entry) -> {
            NbtCompound bodyCompound = new NbtCompound();
            bodyCompound.putString("BodyPart", bodyParts.toString());
            bodyCompound.putBoolean("Has", entry.getKey());
            bodyCompound.putInt("Level", entry.getValue());
            bodyList.add(bodyCompound);

        });
        return bodyList;
    }
}
