package dev.xkmc.curseofpandora.content.sets.evil;

import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.content.complex.ListTickingToken;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.curseofpandora.init.registrate.CoPEffects;
import dev.xkmc.l2complements.content.item.misc.ILCTotem;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.network.TotemUseToClient;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2library.base.effects.EffectUtil;
import dev.xkmc.l2library.capability.conditionals.ConditionalData;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class EvilSpiritWalk extends ITokenProviderItem<EvilSpiritWalk.Data> implements ILCTotem {

	private static final AttrAdder MAGIC = AttrAdder.of("evil_spirit_walk", L2DamageTracker.MAGIC_FACTOR::get,
			AttributeModifier.Operation.ADDITION, EvilSpiritWalk::getMagic);


	private static final AttrAdder ATK = AttrAdder.of("evil_spirit_walk", () -> Attributes.ATTACK_DAMAGE,
			AttributeModifier.Operation.MULTIPLY_BASE, EvilSpiritWalk::getAtk);


	private static int getIndexReq() {
		return CoPConfig.COMMON.evil.evilSpiritWalkRealityIndex.get();
	}

	public static double getAtk() {
		return CoPConfig.COMMON.evil.evilSpiritWalkAtkBonus.get();
	}

	public static double getMagic() {
		return CoPConfig.COMMON.evil.evilSpiritWalkMagicBonus.get();
	}

	private static int getCD() {
		return CoPConfig.COMMON.evil.evilSpiritWalkCoolDown.get();
	}


	public EvilSpiritWalk(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.Evil.WALK.get(
				(int) Math.round(getMagic() * 100),
				(int) Math.round(getAtk() * 100),
				(int) Math.round(getCD() / 20d)
		).withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getCooldowns().isOnCooldown(this)) return;
		if (player.getAttributeValue(CoPAttrs.REALITY.get()) >= getIndexReq())
			super.tick(player);
	}

	@Override
	public boolean allow(LivingEntity self, DamageSource source) {
		if (!(self instanceof Player player)) return false;
		if (!ConditionalData.HOLDER.get(player).hasData(getKey())) return false;
		return !player.getCooldowns().isOnCooldown(this);
	}

	@Override
	public void trigger(LivingEntity self, ItemStack holded, Consumer<ItemStack> second) {
		if (!(self instanceof Player player)) return;
		L2Complements.HANDLER.toTrackingPlayers(new TotemUseToClient(player, holded), player);
		self.setHealth(self.getMaxHealth());
		self.removeAllEffects();
		self.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
		self.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
		self.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
		player.getCooldowns().addCooldown(this, getCD());
	}

	@SerialClass
	public static class Data extends ListTickingToken {

		public Data() {
			super(List.of(MAGIC, ATK));
		}

		@Override
		protected void removeImpl(Player player) {
			super.removeImpl(player);
			player.removeEffect(CoPEffects.SPIRIT_WALK.get());
		}

		@Override
		protected void tickImpl(Player player) {
			super.tickImpl(player);
			EffectUtil.refreshEffect(player, new MobEffectInstance(CoPEffects.SPIRIT_WALK.get(), 40, 0,
					true, true), EffectUtil.AddReason.SKILL, player);
		}
	}

}
