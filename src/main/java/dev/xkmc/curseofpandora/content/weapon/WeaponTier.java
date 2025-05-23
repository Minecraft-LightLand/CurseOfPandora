package dev.xkmc.curseofpandora.content.weapon;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public enum WeaponTier implements Tier {
	ANGELIC_JUDGEMENT, DOOM_STAR, CURSED_KARMA, ABYSSAL_EDGE;

	@Override
	public int getUses() {
		return 1000;
	}

	@Override
	public float getSpeed() {
		return 8;
	}

	@Override
	public float getAttackDamageBonus() {
		return 0;
	}

	@Override
	public TagKey<Block> getIncorrectBlocksForDrops() {
		return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
	}

	@Override
	public int getEnchantmentValue() {
		return 15;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.EMPTY;
	}

}
