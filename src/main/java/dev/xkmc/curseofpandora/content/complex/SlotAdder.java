package dev.xkmc.curseofpandora.content.complex;

import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.UUID;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public record SlotAdder(
		String name, Supplier<String> slotId, UUID slot, IntSupplier slotCount
) implements ISubToken, IAttrAdder {

	public static SlotAdder of(String name, String id, int count) {
		return new SlotAdder(name + "_bonus", () -> id, MathHelper.getUUIDFromString(name + "_" + id), () -> count);
	}

	public static SlotAdder of(String name, Supplier<String> id) {
		return new SlotAdder(name + "_bonus",
				() -> id.get().split("#")[0],
				MathHelper.getUUIDFromString(name + "_slots"),
				() -> id.get().contains("#") ? Integer.parseInt(id.get().split("#")[1]) : 1
		);
	}

	public void tickImpl(Player player) {
		if (player.level().isClientSide()) return;
		var opt = CuriosApi.getCuriosInventory(player).resolve()
				.flatMap(x -> x.getStacksHandler(slotId.get()));
		if (opt.isEmpty()) return;
		var old = opt.get().getModifiers().get(slot);
		if (old == null || old.getAmount() != slotCount.getAsInt()) {
			opt.get().removeModifier(slot);
			opt.get().addPermanentModifier(new AttributeModifier(slot, name,
					slotCount.getAsInt(), AttributeModifier.Operation.ADDITION));
		}
	}

	public void removeImpl(Player player) {
		if (player.level().isClientSide()) return;
		var opt = CuriosApi.getCuriosInventory(player).resolve()
				.flatMap(x -> x.getStacksHandler(slotId.get()));
		if (opt.isEmpty()) return;
		opt.get().removeModifier(slot);
	}

	public MutableComponent getTooltip() {
		return Component.translatable(
						"attribute.modifier.plus." + AttributeModifier.Operation.ADDITION.toValue(),
						ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(slotCount.getAsInt()),
						Component.translatable("curios.identifier." + slotId.get()));
	}

}
