package dev.mrsterner.eyesofender.common.networking.packet;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.common.ability.HamonAbility;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.PlayerLookup;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class HamonAbilityPacket {
	public static final Identifier ID = EyesOfEnder.id("ability");

	public static void send(LivingEntity user, NbtCompound abilityTag) {
		PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
		data.writeNbt(abilityTag);
		data.writeInt(user.getId());
		PlayerLookup.tracking(user).forEach(p -> ServerPlayNetworking.send(p, ID, data));
		if (user instanceof ServerPlayerEntity) {
			ServerPlayNetworking.send((ServerPlayerEntity) user, ID, data);
		}
	}

	@Environment(EnvType.CLIENT)
	public static void sendFromClientPlayer(ClientPlayerEntity user, NbtCompound abilityTag) {
		PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
		data.writeNbt(abilityTag);
		data.writeInt(user.getId());
		ClientPlayNetworking.send(ID, data);
	}

	@Environment(EnvType.CLIENT)
	public static void handle(MinecraftClient client, ClientPlayNetworkHandler networkHandler, PacketByteBuf packetByteBuf, PacketSender sender) {
		HamonAbility hamonAbility = HamonAbility.fromTag(packetByteBuf.readNbt());
		Entity entity = client.world.getEntityById(packetByteBuf.readInt());
		if (entity instanceof LivingEntity) {
			client.execute(() -> hamonAbility.use((LivingEntity) entity));
		}
	}

	public static void handleFromClient(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf packetByteBuf, PacketSender sender) {
		HamonAbility hamonAbility = HamonAbility.fromTag(packetByteBuf.readNbt());
		if (hamonAbility != null) {
			server.execute(() -> hamonAbility.use(player));
		}
	}
}
