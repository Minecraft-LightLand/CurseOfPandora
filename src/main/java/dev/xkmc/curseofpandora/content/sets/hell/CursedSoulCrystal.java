package dev.xkmc.curseofpandora.content.sets.hell;

import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.event.ItemEffectHandlers;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.curseofpandora.init.registrate.CoPMisc;
import dev.xkmc.l2complements.events.MagicEventHandler;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CursedSoulCrystal extends ITokenProviderItem<CursedSoulCrystal.Data> {

	public static int getIndexReq() {
		return CoPConfig.COMMON.hell.cursedSoulCrystalRealityIndex.get();
	}

	public static int getCoolDown() {
		return CoPConfig.COMMON.hell.cursedSoulCrystalCoolDown.get();
	}

	public static double getRange() {
		return CoPConfig.COMMON.hell.cursedSoulCrystalRange.get();
	}

	public CursedSoulCrystal(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.Hell.CRYSTAL.get(
				Math.round(getRange()),
				Math.round(getCoolDown() / 20d)
		).withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPMisc.REALITY.get()) >= getIndexReq())
			super.tick(player);
	}

	@SerialClass
	public static class Data extends BaseTickingToken implements IAttackListenerToken {

		@Override
		protected void removeImpl(Player player) {

		}

		@Override
		protected void tickImpl(Player player) {
		}

		@Override
		public void onPlayerAttacked(Player player, AttackCache cache) {
			var event = cache.getLivingAttackEvent();
			assert event != null;
			if (event.getSource().is(DamageTypeTags.BYPASSES_EFFECTS) ||
					event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
				return;
			}
			if (event.getSource().getEntity() instanceof Mob mob) {
				if (mob.getMobType() != MobType.UNDEAD) {
					var item = CoPItems.CURSED_SOUL_CRYSTAL.get();
					if (player.getCooldowns().isOnCooldown(item))
						return;
					Mob target = redirect(player);
					if (target != null) {
						MagicEventHandler.schedule(() -> {
							target.hurt(event.getSource(), event.getAmount());
							player.getCooldowns().addCooldown(item, getCoolDown());
							ItemEffectHandlers.CURSED_SOUL_CRYSTAL.trigger(target);
						});
						event.setCanceled(true);
					}
				}
			}
		}

		@Nullable
		private static Mob redirect(Player player) {
			AABB aabb = player.getBoundingBox().inflate(getRange());
			List<Mob> list = new ArrayList<>();
			for (var e : player.level().getEntities(EntityTypeTest.forClass(Mob.class), aabb, e -> e.getMobType() == MobType.UNDEAD)) {
				if (e.distanceTo(player) > getRange())
					continue;
				list.add(e);
			}
			if (!list.isEmpty()) {
				return list.get(player.getRandom().nextInt(list.size()));
			}
			return null;
		}

	}

}
