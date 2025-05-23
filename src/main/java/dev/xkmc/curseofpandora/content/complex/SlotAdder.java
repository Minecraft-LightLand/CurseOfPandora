package dev.xkmc.curseofpandora.content.complex;

import dev.xkmc.curseofpandora.init.CurseOfPandora;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

public record SlotAdder(
		ResourceLocation name, Supplier<String> slotId, IntSupplier slotCount
) implements ISubToken, IAttrAdder {

	public static SlotAdder of(String name, String id, int count) {
		return new SlotAdder(CurseOfPandora.loc(name + "_slots"), () -> id, () -> count);
	}

	public static SlotAdder of(String name, Supplier<String> id) {
		return new SlotAdder(CurseOfPandora.loc(name + "_slots"),
				() -> id.get().split("#")[0],
				() -> id.get().contains("#") ? Integer.parseInt(id.get().split("#")[1]) : 1
		);
	}

	public void tickImpl(Player player) {
		if (player.level().isClientSide()) return;
		var opt = CuriosApi.getCuriosInventory(player)
				.flatMap(x -> x.getStacksHandler(slotId.get()));
		if (opt.isEmpty()) return;
		var old = opt.get().getModifiers().get(name);
		if (old == null || old.amount() != slotCount.getAsInt()) {
			opt.get().removeModifier(name);
			opt.get().addPermanentModifier(new AttributeModifier(name,
					slotCount.getAsInt(), AttributeModifier.Operation.ADD_VALUE));
		}
	}

	public void removeImpl(Player player) {
		if (player.level().isClientSide()) return;
		var opt = CuriosApi.getCuriosInventory(player)
				.flatMap(x -> x.getStacksHandler(slotId.get()));
		if (opt.isEmpty()) return;
		opt.get().removeModifier(name);
	}

	public MutableComponent getTooltip() {
		return Component.translatable(
				"attribute.modifier.plus." + AttributeModifier.Operation.ADD_VALUE.id(),
				"" + slotCount.getAsInt(),
				Component.translatable("curios.identifier." + slotId.get()));
	}

}
