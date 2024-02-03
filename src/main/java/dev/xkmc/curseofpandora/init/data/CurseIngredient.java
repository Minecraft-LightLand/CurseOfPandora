package dev.xkmc.curseofpandora.init.data;

import com.google.gson.JsonObject;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.l2library.serial.ingredients.BaseIngredient;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SerialClass
public class CurseIngredient extends BaseIngredient<CurseIngredient> {

	public static final BaseIngredient.Serializer<CurseIngredient> INSTANCE =
			new BaseIngredient.Serializer<>(CurseIngredient.class, new ResourceLocation(CurseOfPandora.MODID, "cursed_enchantments"));

	public CurseIngredient() {
		super();
	}

	private CurseIngredient(Stream<? extends Value> stream) {
		super(stream);
	}

	protected CurseIngredient validate() {
		return new CurseIngredient(ForgeRegistries.ENCHANTMENTS.getValues().stream().filter(Enchantment::isCurse).map(e -> new EnchValue(e, 1)));
	}

	public boolean test(ItemStack stack) {
		return stack.is(Items.ENCHANTED_BOOK) && EnchantmentHelper.getEnchantments(stack).entrySet().stream().anyMatch(e -> e.getKey().isCurse());
	}

	public BaseIngredient.Serializer<CurseIngredient> getSerializer() {
		return INSTANCE;
	}

	private static record EnchValue(Enchantment ench, int min) implements Ingredient.Value {
		private EnchValue(Enchantment ench, int min) {
			this.ench = ench;
			this.min = min;
		}

		public Collection<ItemStack> getItems() {
			return IntStream.range(this.min, this.ench.getMaxLevel() + 1).mapToObj((i) -> {
				return EnchantedBookItem.createForEnchantment(new EnchantmentInstance(this.ench, i));
			}).toList();
		}

		public JsonObject serialize() {
			throw new IllegalStateException("This value should not be serialized as such");
		}

		public Enchantment ench() {
			return this.ench;
		}

		public int min() {
			return this.min;
		}
	}

}