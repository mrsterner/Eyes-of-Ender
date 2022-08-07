package dev.mrsterner.eyesofender.common.networking.packet;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.interfaces.AbilityUser;
import dev.mrsterner.eyesofender.common.utils.NbtUtils;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class SyncAbilityUserDataPacket {
	public static final Identifier ID = EyesOfEnder.id("sync_ability");

	public static void send(boolean client, PlayerEntity user, AbilityUser caster) {
		PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
		NbtCompound abilityCompound = NbtUtils.writeAbilityData(caster, new NbtCompound());
		data.writeNbt(abilityCompound);
		if (client) {
			ClientPlayNetworking.send(ID, data);
		} else {
			ServerPlayNetworking.send((ServerPlayerEntity) user, ID, data);
		}
	}

	public static void handleFromClient(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf packetByteBuf, PacketSender sender) {
		NbtCompound tag = packetByteBuf.readNbt();
		server.execute(() -> AbilityUser.of(player).ifPresent(caster -> NbtUtils.readAbilityData(caster, tag)));
	}
}
