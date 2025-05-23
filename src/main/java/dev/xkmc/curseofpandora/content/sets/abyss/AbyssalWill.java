package dev.xkmc.curseofpandora.content.sets.abyss;

import dev.xkmc.curseofpandora.content.complex.BasePandoraToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.curseofpandora.init.registrate.CoPEffects;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.l2core.base.effects.EffectUtil;
import dev.xkmc.l2core.init.L2LibReg;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.curios.L2Totem;
import dev.xkmc.l2damagetracker.contents.curios.TotemUseToClient;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.function.Consumer;

public class AbyssalWill extends ITokenProviderItem<AbyssalWill.Data> implements L2Totem {

	public static int getDepth(Player player) {
		return L2LibReg.CONDITIONAL.type().getOrCreate(player).hasData(CoPItems.ABYSSAL_WILL.get().getKey()) ?
				CoPConfig.SERVER.abyssal.abyssalWillDepthStep.get() :
				CoPConfig.SERVER.abyssal.abyssalDepthStep.get();
	}

	public static int getStep(Player player) {
		int depth = getDepth(player);
		double y = player.position().y;
		return y > 0 ? 0 : (int) -Math.floor(y / depth);
	}

	private static int getIndexReq() {
		return CoPConfig.SERVER.abyssal.abyssalWillRealityIndex.get();
	}

	private static int getCoolDown() {
		return CoPConfig.SERVER.abyssal.abyssalWillCoolDown.get();
	}

	private static int getDuration() {
		return CoPConfig.SERVER.abyssal.abyssalWillDuration.get();
	}

	private static MobEffectInstance eff() {
		return new MobEffectInstance(CoPEffects.ABYSSAL_PROTECTION, getDuration());
	}

	public AbyssalWill(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(ctx.level()) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(Component.literal("- ").append(CoPLangData.Abyssal.WILL.get())
				.withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
		list.add(Component.literal("- ").append(CoPLangData.Abyssal.WILL_TOTEM.get(
						Math.round(getDuration() / 20d), Math.round(getCoolDown() / 20d)))
				.withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
		list.add(Component.literal("-> ").append(CoPLangData.Abyssal.WILL_RESTRICT.get())
				.withStyle(pass ? ChatFormatting.RED : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPAttrs.REALITY) >= getIndexReq())
			super.tick(player);
	}

	@Override
	public void trigger(LivingEntity self, ItemStack holded, Consumer<ItemStack> second) {
		L2DamageTracker.PACKET_HANDLER.toTrackingPlayers(TotemUseToClient.of(self, holded), self);
		self.setHealth(1);
		self.removeAllEffects();
		EffectUtil.addEffect(self, eff(), self);
		if (self instanceof Player player) {
			player.getCooldowns().addCooldown(this, getCoolDown());
		}
	}

	@Override
	public boolean allow(LivingEntity self, DamageSource pDamageSource) {
		if (!(self instanceof Player player)) return false;
		if (player.getY() > 0 && !pDamageSource.is(DamageTypeTags.BYPASSES_ENCHANTMENTS)) {
			return false;
		}
		return !player.getCooldowns().isOnCooldown(this);
	}

	@SerialClass
	public static class Data extends BasePandoraToken implements IAttackListenerToken {

		@Override
		protected void removeImpl(Player player) {

		}

		@Override
		protected void tickImpl(Player player) {

		}

		@Override
		public boolean onPlayerAttacked(Player player, DamageData.Attack data) {
			if (!player.hasEffect(CoPEffects.ABYSSAL_PROTECTION)) return false;
			return data.getSource().is(DamageTypeTags.BYPASSES_ENCHANTMENTS);
		}
	}

}
