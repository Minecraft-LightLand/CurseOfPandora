package dev.xkmc.curseofpandora.content.sets.hell;

import dev.xkmc.curseofpandora.content.complex.BasePandoraToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.event.ItemEffectHandlers;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.l2core.events.SchedulerHandler;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CursedSoulCrystal extends ITokenProviderItem<CursedSoulCrystal.Data> {

	public static int getIndexReq() {
		return CoPConfig.SERVER.hell.cursedSoulCrystalRealityIndex.get();
	}

	public static int getCoolDown() {
		return CoPConfig.SERVER.hell.cursedSoulCrystalTriggerCoolDown.get();
	}

	public static double getRange() {
		return CoPConfig.SERVER.hell.cursedSoulCrystalRange.get();
	}

	public CursedSoulCrystal(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(ctx.level()) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.Hell.CRYSTAL.get(
				Math.round(getRange()),
				Math.round(getCoolDown() / 20d)
		).withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPAttrs.REALITY) >= getIndexReq())
			super.tick(player);
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
			if (data.getSource().is(DamageTypeTags.BYPASSES_EFFECTS) ||
					data.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
				return false;
			}
			if (data.getSource().getEntity() instanceof Mob mob) {
				if (!mob.getType().is(EntityTypeTags.UNDEAD)) {
					var item = CoPItems.CURSED_SOUL_CRYSTAL.get();
					if (player.getCooldowns().isOnCooldown(item))
						return false;
					Mob target = redirect(player);
					if (target != null) {
						SchedulerHandler.schedule(() -> {
							target.hurt(data.getSource(), data.getDamageOriginal());
							player.getCooldowns().addCooldown(item, getCoolDown());
							ItemEffectHandlers.CURSED_SOUL_CRYSTAL.trigger(target);
						});
						return true;
					}
				}
			}
			return false;
		}

		@Nullable
		private static Mob redirect(Player player) {
			AABB aabb = player.getBoundingBox().inflate(getRange());
			List<Mob> list = new ArrayList<>();
			for (var e : player.level().getEntities(EntityTypeTest.forClass(Mob.class), aabb, e -> e.getType().is(EntityTypeTags.UNDEAD))) {
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
