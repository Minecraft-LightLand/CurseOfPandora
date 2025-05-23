package dev.xkmc.curseofpandora.init.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class LuckAppendTableLootModifier extends LootModifier {

	public static final MapCodec<LuckAppendTableLootModifier> CODEC = RecordCodecBuilder.mapCodec(i -> codecStart(i).and(i.group(
					Codec.DOUBLE.fieldOf("chance").forGetter(e -> e.chance),
					Codec.DOUBLE.fieldOf("bonus").forGetter(e -> e.bonus),
					Codec.STRING.fieldOf("table").forGetter(e -> e.table.toString())))
			.apply(i, LuckAppendTableLootModifier::new));

	private final double chance, bonus;
	private final ResourceKey<LootTable> table;

	private LuckAppendTableLootModifier(LootItemCondition[] conditionsIn, double chance, double bonus, String table) {
		super(conditionsIn);
		this.chance = chance;
		this.bonus = bonus;
		this.table = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse(table));
	}

	public LuckAppendTableLootModifier(double chance, double bonus, ResourceKey<LootTable> table, LootItemCondition... conditionsIn) {
		super(conditionsIn);
		this.chance = chance;
		this.bonus = bonus;
		this.table = table;
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> list, LootContext context) {
		double luckFactor = CoPConfig.SERVER.lootLuckFactor.get();
		double total = chance + bonus * luckFactor * context.getLuck();
		int count = (int) total;
		if (total - count > context.getRandom().nextDouble()) {
			count++;
		}
		int maxItem = CoPConfig.SERVER.maxItemGenerated.get();
		count = Math.min(maxItem, count);
		for (int i = 0; i < count; i++) {
			add(list, context);
		}
		return list;
	}

	private void add(ObjectArrayList<ItemStack> list, LootContext context) {
		var lootTable = context.getLevel().getServer().reloadableRegistries().getLootTable(table);
		ObjectArrayList<ItemStack> objectarraylist = new ObjectArrayList<>();
		lootTable.getRandomItemsRaw(context, objectarraylist::add);
		list.addAll(objectarraylist);
	}

	@Override
	public MapCodec<LuckAppendTableLootModifier> codec() {
		return CODEC;
	}

}
