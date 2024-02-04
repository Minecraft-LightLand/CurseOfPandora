package dev.xkmc.curseofpandora.content.complex;

import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.loot.LootDataToClient;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GLMDropItem extends Item {

	public GLMDropItem(Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		var data = LootDataToClient.LIST_CACHE.get(this);
		if (data != null) {
			double chance = data.chance;
			var killer = data.killer.getDescription();
			var target = data.target.getDescription();
			if (chance < 1) {
				list.add(CoPLangData.IDS.KILL_DROP_CHANCE.get(killer, target, (int) Math.round(data.chance * 100)));
			} else {
				list.add(CoPLangData.IDS.KILL_DROP_CHANCE.get(killer, target));
			}
		}
	}

}
