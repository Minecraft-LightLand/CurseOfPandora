package dev.xkmc.curseofpandora.init.data;

import dev.xkmc.curseofpandora.init.CurseOfPandora;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public record EnchantmentTagIngredient(HolderSet<Enchantment> enchantment) implements ICustomIngredient {

	public static Ingredient of(HolderSet<Enchantment> ench) {
		return (new EnchantmentTagIngredient(ench)).toVanilla();
	}

	public static Ingredient of(HolderLookup.Provider pvd, TagKey<Enchantment> ench) {
		var holder = pvd.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ench);
		return of(holder);
	}

	public Stream<ItemStack> getItems() {
		return this.enchantment.stream().flatMap(e -> IntStream.range(1, e.value().definition().maxLevel() + 1)
				.mapToObj(i -> EnchantedBookItem.createForEnchantment(new EnchantmentInstance(e, i))));
	}

	public boolean isSimple() {
		return false;
	}

	public IngredientType<?> getType() {
		return CurseOfPandora.ING_ENCH_TAG.get();
	}

	public boolean test(ItemStack stack) {
		var map = EnchantmentHelper.getEnchantmentsForCrafting(stack);
		for (var e : map.keySet()) {
			if (enchantment.contains(e))
				return true;
		}
		return false;
	}

}