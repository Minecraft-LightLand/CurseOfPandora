package dev.xkmc.curseofpandora.content.complex;

import dev.xkmc.l2complements.content.item.curios.CurioItem;
import dev.xkmc.l2complements.content.item.curios.ICapItem;
import dev.xkmc.l2library.capability.conditionals.ConditionalData;
import dev.xkmc.l2library.capability.conditionals.Context;
import dev.xkmc.l2library.capability.conditionals.TokenKey;
import dev.xkmc.l2library.capability.conditionals.TokenProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public abstract class ITokenProviderItem<R extends BaseTickingToken> extends CurioItem
		implements ICapItem<BaseCurseCurio>, TokenProvider<R, ITokenProviderItem<R>>, Context {

	private final TokenKey<R> key;

	private final Supplier<R> sup;

	public ITokenProviderItem(Properties properties, TokenKey<R> key, Supplier<R> sup) {
		super(properties);
		this.key = key;
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
	public BaseCurseCurio create(ItemStack stack) {
		return new BaseCurseCurio(this, stack);
	}

	@Override
	public final TokenKey<R> getKey() {
		return key;
	}

}
