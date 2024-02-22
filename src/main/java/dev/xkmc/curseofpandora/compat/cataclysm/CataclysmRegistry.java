package dev.xkmc.curseofpandora.compat.cataclysm;

import com.tterrag.registrate.util.entry.EntityEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.pandora.init.registrate.PandoraItems;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.MobCategory;

import static dev.xkmc.curseofpandora.init.registrate.CoPItems.weapon;

public class CataclysmRegistry {

	public static final EntityEntry<FreeLaserBeam> LASER_BEAM = CurseOfPandora.REGISTRATE
			.<FreeLaserBeam>entity("death_laser_beam", FreeLaserBeam::new, MobCategory.MISC)
			.properties(e -> e.sized(0.1F, 0.1F).fireImmune())
			.renderer(() -> FreeLaserBeamRenderer::new).register();

	public static final ItemEntry<CataclysmSword> CATACLYSM_SWORD =
			weapon("cataclysm", p -> new CataclysmSword(p.fireResistant())).tab(PandoraItems.TAB.getKey())
					.tag(ItemTags.SWORDS).register();

	public static void register() {

	}

}
