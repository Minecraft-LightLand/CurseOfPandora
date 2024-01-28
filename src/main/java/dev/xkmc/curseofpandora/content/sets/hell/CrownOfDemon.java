package dev.xkmc.curseofpandora.content.sets.hell;

import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.curseofpandora.init.registrate.CoPMisc;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2library.capability.conditionals.ConditionalData;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CrownOfDemon extends ITokenProviderItem<CrownOfDemon.Data> {

	public static boolean check(Player player) {
		return ConditionalData.HOLDER.get(player).hasData(CoPItems.CROWN_OF_DEMON.get().getKey());
	}

	public static boolean isPeon(Mob entity) {
		return entity.getMobType() == MobType.UNDEAD && entity.getAttributeBaseValue(Attributes.MAX_HEALTH) <= getThreshold();
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
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq()).withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.Hell.CROWN.get(
				//TODO
		).withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPMisc.REALITY.get()) >= getIndexReq())
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
		public void onPlayerAttackTarget(Player player, AttackCache cache) {
			refresh(player);
			//TODO
		}

		@Override
		public void onPlayerAttacked(Player player, AttackCache cache) {
			refresh(player);
			//TODO
		}
	}

}
