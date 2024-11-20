package dev.xkmc.curseofpandora.compat.enigmaticlegacy;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class InsomniaRelief extends ITokenProviderItem<InsomniaRelief.Data> {

	private static int getIndexReq() {
		return 3;//TODO
	}

	public InsomniaRelief(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(CoPAttrs.REALITY.get(), new AttributeModifier(uuid, "Insomnia Relief", -1, AttributeModifier.Operation.ADDITION));
		return builder.build();
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.Compat.EL_INSOMNIA.get()
				.withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPAttrs.REALITY.get()) >= getIndexReq())
			super.tick(player);
	}

	@SerialClass
	public static class Data extends BaseTickingToken {

		@Override
		protected void removeImpl(Player player) {

		}

		@Override
		protected void tickImpl(Player player) {

		}

	}

}
