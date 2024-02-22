package dev.xkmc.curseofpandora.compat.cataclysm;

import com.github.L_Ender.cataclysm.client.render.entity.RendererDeath_Laser_beam;
import com.github.L_Ender.cataclysm.entity.projectile.Death_Laser_Beam_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;

public class FreeLaserBeamRenderer extends RendererDeath_Laser_beam {

	public FreeLaserBeamRenderer(EntityRendererProvider.Context mgr) {
		super(mgr);
	}

	@Override
	public void render(Death_Laser_Beam_Entity solarBeam, float entityYaw, float delta, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		var caster = solarBeam.caster;
		var timer = solarBeam.appear.getTimer();
		solarBeam.caster = null;
		int frame = Mth.floor(((float) (solarBeam.appear.getTimer() - 1) + delta) * 2.0F);
		if (solarBeam.tickCount > 5 && frame < 0) {
			solarBeam.appear.setTimer((int) Math.ceil(1.50f - delta));
		}
		super.render(solarBeam, entityYaw, delta, matrixStackIn, bufferIn, packedLightIn);
		solarBeam.appear.setTimer(timer);
		solarBeam.caster = caster;

	}
}
