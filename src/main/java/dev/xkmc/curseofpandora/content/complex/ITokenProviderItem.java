package dev.xkmc.curseofpandora.content.complex;

import dev.xkmc.l2core.capability.conditionals.TokenKey;
import dev.xkmc.l2core.init.L2LibReg;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class ITokenProviderItem<R extends BasePandoraToken> extends Item implements ICurioItem {

	private final Supplier<R> sup;

	public ITokenProviderItem(Properties properties, Supplier<R> sup) {
		super(properties);
		this.sup = sup;
	}

	public void tick(Player player) {
		L2LibReg.CONDITIONAL.type().getOrCreate(player).getOrCreateData(getKey(), sup).update();
	}

	@Override
	public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
		var ans = new ArrayList<>(ICurioItem.super.getAttributesTooltip(tooltips, context, stack));
		if (this instanceof ISlotAdderItem<?> sa) {
			for (var e : sa.getSlotAdder()) {
				ans.add(e.getTooltip().withStyle(ChatFormatting.BLUE));
			}
		}
		return ans;
	}

	@Override
	public void curioTick(SlotContext slotContext, ItemStack stack) {
		if (slotContext.entity() instanceof Player player && player.isAlive()) {
			tick(player);
		}

	}

	private TokenKey<R> key;

	public final TokenKey<R> getKey() {
		if (key == null) {
			var id = builtInRegistryHolder().unwrapKey().orElseThrow().location();
			key = TokenKey.of(id);
		}
		return key;
	}

}
