package dev.xkmc.curseofpandora.content.sets.hell;

import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.l2core.init.L2LibReg;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CrownOfDemon extends ITokenProviderItem<CrownOfDemon.Data> {

	public static boolean check(Player player) {
		return L2LibReg.CONDITIONAL.type().getOrCreate(player).hasData(CoPItems.CROWN_OF_DEMON.get().getKey());
	}

	public static boolean isPeon(Mob entity) {
		return entity.getType().is(EntityTypeTags.UNDEAD) && entity.getAttributeBaseValue(Attributes.MAX_HEALTH) <= getThreshold();
	}

	public static int getIndexReq() {
		return CoPConfig.COMMON.hell.crownOfDemonRealityIndex.get();
	}

	public static double getThreshold() {
		return CoPConfig.COMMON.hell.crownOfDemonBaseHealthThreshold.get();
	}

	public static double getRange() {
		return CoPConfig.COMMON.hell.crownOfDemonRange.get();
	}


	public CrownOfDemon(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(ctx.level()) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq()).withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.Hell.CROWN.get(
		).withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPAttrs.REALITY) >= getIndexReq())
			super.tick(player);
	}

	@SerialClass
	public static class Data extends BaseTickingToken implements IAttackListenerToken {

		private static final int REFRESH = 100;

		private final Set<Mob> peon = new LinkedHashSet<>();

		private int active = 0;

		@Override
		protected void removeImpl(Player player) {

		}

		@Override
		protected void tickImpl(Player player) {
			if (player.level().isClientSide()) return;
			if (player.tickCount % 20 != 0) return;
			if (active == 0) return;
			if (active > 0) {
				active--;
				if (active == 0) {
					peon.clear();
					return;
				}
				refresh(player);
			}

		}

		private void refresh(Player player) {
			if (active > 0) {
				active = REFRESH;
				if (player.tickCount % 20 != 0) return;
			}
			active = REFRESH;
			peon.removeIf(e -> !e.isAlive());
			AABB aabb = player.getBoundingBox().inflate(getRange());
			for (var e : player.level().getEntities(EntityTypeTest.forClass(Mob.class), aabb, CrownOfDemon::isPeon)) {
				if (peon.contains(e)) continue;
				peon.add(e);
			}
		}

		@Override
		public boolean onPlayerAttackTarget(Player player, DamageData.Attack data) {
			refresh(player);
			for (var e : peon) {
				if (e.getTarget() == null) {
					e.setLastHurtByMob(data.getTarget());
				}
			}
			return false;
		}

	}

}
