package dev.mrsterner.eyesofender.common.components.entity;

import dev.mrsterner.eyesofender.common.registry.EOEComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class TimeStopComponent implements AutoSyncedComponent, ServerTickingComponent {
    private World owner;
    private long timeStoppedTicks = -1;
    private PlayerEntity timeStopper = null;
    private MinecraftServer server;

    public TimeStopComponent(World owner) {
        this.owner = owner;
    }

    public long getTimeStoppedTicks() {
        return timeStoppedTicks;
    }

    public void setTimeStoppedTicks(long timeStoppedTicks) {
        this.timeStoppedTicks = timeStoppedTicks;
        EOEComponents.TIME_STOP_COMPONENT.sync(owner);
    }

    public PlayerEntity getTimeStopper() {
        return timeStopper;
    }

    public void setTimeStopper(PlayerEntity value) {
        timeStopper = value;
        EOEComponents.TIME_STOP_COMPONENT.sync(owner);
    }

    @Override
    public void serverTick() {
        timeStoppedTicks--;
        if (timeStoppedTicks < 0) {
            timeStopper = null;
        }
        EOEComponents.TIME_STOP_COMPONENT.sync(owner);
    }

    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
        buf.writeVarLong(timeStoppedTicks);
        if (timeStopper != null) {
            buf.writeUuid(timeStopper.getUuid());
        }
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        timeStoppedTicks = buf.readVarLong();
        try {
            timeStopper = owner.getPlayerByUuid(buf.readUuid());
        } catch (IndexOutOfBoundsException e) {
            timeStopper = null;
        }
    }

    // We don't want to persist this
    @Override
    public void readFromNbt(NbtCompound tag) {}
    @Override
    public void writeToNbt(NbtCompound tag) {}
}
