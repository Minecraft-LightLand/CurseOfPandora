package dev.xkmc.curseofpandora.init.data;

import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.l2core.init.L2LibReg;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
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

public record CurseIngredient(HolderSet<Enchantment> enchantment) implements ICustomIngredient {

	public static Ingredient of(Holder<Enchantment> ench, int min) {
		return (new dev.xkmc.l2core.serial.ingredients.EnchantmentIngredient(ench, min)).toVanilla();
	}

	public static Ingredient of(HolderLookup.Provider pvd, ResourceKey<Enchantment> ench, int min) {
		Holder.Reference<Enchantment> holder = pvd.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ench);
		return of(holder, min);
	}

	public Stream<ItemStack> getItems() {
		return this.enchantment.stream().flatMap(e -> IntStream.range(1, e.value().definition().maxLevel())
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