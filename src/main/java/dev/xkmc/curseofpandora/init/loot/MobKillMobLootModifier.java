package dev.xkmc.curseofpandora.init.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class MobKillMobLootModifier extends LootModifier {

	public static final Codec<MobKillMobLootModifier> CODEC = RecordCodecBuilder.create(i -> codecStart(i).and(i.group(
					ForgeRegistries.ENTITY_TYPES.getCodec().fieldOf("killer").forGetter(e -> e.killer),
					ForgeRegistries.ENTITY_TYPES.getCodec().fieldOf("target").forGetter(e -> e.target),
					Codec.DOUBLE.fieldOf("chance").forGetter(e -> e.chance),
					ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(e -> e.item)))
			.apply(i, MobKillMobLootModifier::new));

	public final EntityType<?> killer, target;
	public final double chance;
	public final Item item;

	private MobKillMobLootModifier(LootItemCondition[] conditionsIn, EntityType<?> killer, EntityType<?> target, double chance, Item item) {
		super(conditionsIn);
		this.chance = chance;
		this.killer = killer;
		this.target = target;
		this.item = item;
	}

	public MobKillMobLootModifier(EntityType<?> killer, EntityType<?> target, double chance, Item item, LootItemCondition... conditionsIn) {
		super(conditionsIn);
		this.chance = chance;
		this.killer = killer;
		this.target = target;
		this.item = item;
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> list, LootContext context) {
		if (!context.hasParam(LootContextParams.KILLER_ENTITY)) return list;
		if (!context.hasParam(LootContextParams.THIS_ENTITY)) return list;
		var kill = context.getParam(LootContextParams.KILLER_ENTITY);
		var self = context.getParam(LootContextParams.THIS_ENTITY);
		if (kill.getType() == killer && self.getType() == target) {
			list.add(item.getDefaultInstance());
		}
		return list;
	}

	@Override
	public Codec<MobKillMobLootModifier> codec() {
		return CODEC;
	}

	public LootItemCondition[] conditions() {
		return conditions;
	}

}
