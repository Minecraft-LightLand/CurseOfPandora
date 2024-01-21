package dev.xkmc.curseofpandora.content.complex;

import dev.xkmc.l2complements.content.item.curios.CurioItem;
import dev.xkmc.l2complements.content.item.curios.ICapItem;
import dev.xkmc.l2library.capability.conditionals.ConditionalData;
import dev.xkmc.l2library.capability.conditionals.Context;
import dev.xkmc.l2library.capability.conditionals.TokenKey;
import dev.xkmc.l2library.capability.conditionals.TokenProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public abstract class ITokenProviderItem<R extends BaseTickingToken> extends CurioItem
		implements ICapItem<BaseCurseCurio>, TokenProvider<R, ITokenProviderItem<R>>, Context {

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
	public BaseCurseCurio create(ItemStack stack) {
		return new BaseCurseCurio(this, stack);
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
