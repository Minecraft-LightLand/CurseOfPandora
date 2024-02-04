package dev.xkmc.curseofpandora.init.loot;

import com.mojang.datafixers.util.Pair;
import dev.xkmc.l2serial.network.SerialPacketBase;
import dev.xkmc.l2serial.serialization.SerialClass;
import dev.xkmc.l2serial.serialization.SerialClass.SerialField;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.network.NetworkEvent;

import java.util.*;

@SerialClass
public class LootDataToClient extends SerialPacketBase {
	public static Map<Item, MobKillMobLootModifier> LIST_CACHE = new HashMap<>();
	@SerialField
	public ArrayList<CompoundTag> list = new ArrayList<>();

	/**
	 * @deprecated
	 */
	@Deprecated
	public LootDataToClient() {
	}

	public LootDataToClient(List<MobKillMobLootModifier> list) {
		for (MobKillMobLootModifier e : list) {
			Optional<Tag> res = IGlobalLootModifier.DIRECT_CODEC.encodeStart(NbtOps.INSTANCE, e).result();
			if (res.isPresent()) {
				Object var6 = res.get();
				if (var6 instanceof CompoundTag ct) {
					this.list.add(ct);
				}
			}
		}

	}

	public void handle(NetworkEvent.Context context) {
		LIST_CACHE = new HashMap<>();
		for (CompoundTag ct : this.list) {
			Optional<Pair<IGlobalLootModifier, Tag>> ans = IGlobalLootModifier.DIRECT_CODEC.decode(NbtOps.INSTANCE, ct).result();
			if (ans.isPresent()) {
				if (ans.get().getFirst() instanceof MobKillMobLootModifier mod) {
					LIST_CACHE.put(mod.item, mod);
				}
			}
		}

	}
}
