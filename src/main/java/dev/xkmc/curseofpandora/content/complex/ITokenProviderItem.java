package dev.xkmc.curseofpandora.content.complex;

import dev.xkmc.l2complements.content.item.curios.CurioItem;
import dev.xkmc.l2library.capability.conditionals.ConditionalData;
import dev.xkmc.l2library.capability.conditionals.Context;
import dev.xkmc.l2library.capability.conditionals.TokenKey;
import dev.xkmc.l2library.capability.conditionals.TokenProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class ITokenProviderItem<R extends BaseTickingToken> extends CurioItem
		implements ICurioItem, TokenProvider<R, ITokenProviderItem<R>>, Context {

	private final Supplier<R> sup;

	public ITokenProviderItem(Properties properties, Supplier<R> sup) {
		super(properties);
		this.sup = sup;
	}

	@Override
	public final R getData(ITokenProviderItem<R> item) {
		return sup.get();
	}

	public void tick(Player player) {
		ConditionalData.HOLDER.get(player).getOrCreateData(this, this).update();
	}

	@Override
	public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
		var ans = new ArrayList<>(ICurioItem.super.getAttributesTooltip(tooltips, stack));
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

	@Override
	public final TokenKey<R> getKey() {
		if (key == null) {
			var id = ForgeRegistries.ITEMS.getKey(this);
			assert id != null;
			key = TokenKey.of(id);
		}
		return key;
	}

}
