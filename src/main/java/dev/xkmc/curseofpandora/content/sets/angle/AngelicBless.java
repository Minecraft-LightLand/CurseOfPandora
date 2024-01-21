package dev.xkmc.curseofpandora.content.sets.angle;

import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.init.registrate.CoPMisc;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

public class AngelicBless extends ITokenProviderItem<AngelicBless.Data> {

	private static final AttrAdder REDUCTION = AttrAdder.of("angelic_bless", CoPMisc.ABSORB, AttributeModifier.Operation.ADDITION, 1);

	public AngelicBless(Properties properties) {
		super(properties, Data::new);
	}

	@SerialClass
	public static class Data extends BaseTickingToken implements IAttackListenerToken {

		@Override
		protected void removeImpl(Player player) {
			REDUCTION.removeImpl(player);
		}

		@Override
		protected void tickImpl(Player player) {
			if (player.level().isClientSide()) return;
			if (check(player)) {
				REDUCTION.tickImpl(player);
			} else {
				REDUCTION.removeImpl(player);
			}
		}

		private boolean check(Player player) {
			int reality = 4;//TODO
			return player.level().canSeeSky(player.blockPosition()) && player.getAttributeValue(CoPMisc.REALITY.get()) >= reality;
		}

		@Override
		public void onPlayerHurt(Player player, AttackCache cache) {
			float factor = 0.8f;//TODO
			if (check(player)) {
				cache.addHurtModifier(DamageModifier.multTotal(factor));
			}
		}

	}

}
