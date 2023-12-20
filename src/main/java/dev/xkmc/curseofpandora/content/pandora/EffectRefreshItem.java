package dev.xkmc.curseofpandora.content.pandora;

import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.l2complements.content.item.curios.CurioItem;
import dev.xkmc.l2complements.content.item.curios.EffectValidItem;
import dev.xkmc.l2complements.content.item.curios.ICapItem;
import dev.xkmc.l2library.base.effects.EffectUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.List;
import java.util.function.Supplier;

public class EffectRefreshItem extends CurioItem implements EffectValidItem, ICapItem<EffectRefreshItem.Data> {

	private final Supplier<MobEffectInstance> sup;

	public EffectRefreshItem(Properties properties, Supplier<MobEffectInstance> sup) {
		super(properties);
		this.sup = sup;
	}

	@Override
	public boolean isEffectValid(MobEffectInstance ins, ItemStack stack, LivingEntity user) {
		return ins.getEffect() == sup.get().getEffect();
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		MobEffectInstance ins = sup.get();
		MutableComponent ans = Component.translatable(ins.getDescriptionId());
		if (ins.getAmplifier() > 0) {
			ans = Component.translatable("potion.withAmplifier", ans,
					Component.translatable("potion.potency." + ins.getAmplifier()));
		}
		ans = ans.withStyle(ins.getEffect().getCategory().getTooltipFormatting());
		list.add(CoPLangData.IDS.EFFECT_REFRESH_CURIO.get().withStyle(ChatFormatting.GRAY).append(ans));
	}

	@Override
	public Data create(ItemStack stack) {
		return new Data(this, stack);
	}

	public record Data(EffectRefreshItem item, ItemStack stack) implements ICurio {

		@Override
		public ItemStack getStack() {
			return stack;
		}

		@Override
		public void curioTick(SlotContext slotContext) {
			EffectUtil.refreshEffect(slotContext.entity(), item.sup.get(),
					EffectUtil.AddReason.SELF, slotContext.entity());
		}

	}

}

