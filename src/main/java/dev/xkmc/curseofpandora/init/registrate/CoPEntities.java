package dev.xkmc.curseofpandora.init.registrate;

import com.tterrag.registrate.util.entry.EntityEntry;
import dev.xkmc.curseofpandora.content.entity.AbyssalFangs;
import dev.xkmc.curseofpandora.content.entity.AbyssalFangsRenderer;
import dev.xkmc.curseofpandora.content.entity.WindBladeEntity;
import dev.xkmc.curseofpandora.content.entity.WindBladeEntityRenderer;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import net.minecraft.world.entity.MobCategory;

public class CoPEntities {

	public static final EntityEntry<WindBladeEntity> WIND_BLADE;
	public static final EntityEntry<AbyssalFangs> ABYSSAL_FANGS;

	static {

		WIND_BLADE = CurseOfPandora.REGISTRATE
				.<WindBladeEntity>entity("wind_blade", WindBladeEntity::new, MobCategory.MISC)
				.properties(e -> e.sized(0.5f, 0.5f)
						.clientTrackingRange(4)
						.setShouldReceiveVelocityUpdates(true)
						.updateInterval(20).fireImmune())
				.renderer(() -> WindBladeEntityRenderer::new)
				.defaultLang().register();

		ABYSSAL_FANGS = CurseOfPandora.REGISTRATE
				.<AbyssalFangs>entity("abyssal_fangs", AbyssalFangs::new, MobCategory.MISC)
				.properties(e -> e.sized(0.5F, 0.8F)
						.clientTrackingRange(6)
						.updateInterval(2))
				.renderer(() -> AbyssalFangsRenderer::new)
				.defaultLang().register();
	}

	public static void register() {

	}

}
