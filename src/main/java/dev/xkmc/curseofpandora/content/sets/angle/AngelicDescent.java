package dev.xkmc.curseofpandora.content.sets.angle;

import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.init.registrate.CoPMisc;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2library.capability.conditionals.TokenKey;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class AngelicDescent extends ITokenProviderItem<AngelicDescent.Data> {

	private static final AttrAdder ATTACK = AttrAdder.of("angelic_descent", () -> Attributes.ATTACK_DAMAGE, AttributeModifier.Operation.MULTIPLY_BASE, 0.3);

	public AngelicDescent(Properties properties, TokenKey<Data> key) {
		super(properties, Data::new);
	}

	@SerialClass
	public static class Data extends BaseTickingToken implements IAttackListenerToken {

		@Override
		public void onPlayerHurtTarget(Player player, AttackCache cache) {
			float bonus = 2f;//TODO
			if (cache.getAttackTarget().getMobType() == MobType.UNDEAD && check(player)) {
				cache.addHurtModifier(DamageModifier.multTotal(bonus));
			}
		}

		@Override
		protected void removeImpl(Player player) {
			ATTACK.removeImpl(player);
		}

		@Override
		protected void tickImpl(Player player) {
			if (player.level().isClientSide()) return;
			if (check(player)) {
				ATTACK.tickImpl(player);
			} else {
				ATTACK.removeImpl(player);
			}
		}

		private boolean check(Player player) {
			int reality = 5;//TODO
			return player.level().canSeeSky(player.blockPosition()) && player.getAttributeValue(CoPMisc.REALITY.get()) >= reality;
		}

	}

}
