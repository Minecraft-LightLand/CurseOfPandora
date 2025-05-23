package dev.xkmc.curseofpandora.content.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class WindBladeEntityRenderer extends EntityRenderer<WindBladeEntity> {

	private static final ResourceLocation TEXTURE = CurseOfPandora.loc("textures/entity/wind_blade.png");

	public WindBladeEntityRenderer(EntityRendererProvider.Context manager) {
		super(manager);
	}

	@Override
	public void render(WindBladeEntity entity, float yRot, float partial, PoseStack matrix, MultiBufferSource buffer, int light) {
		if (entity.getStack().getItem() instanceof WindBladeWeapon weapon && weapon.glow()) {
			light = LightTexture.FULL_BRIGHT;
		}
		matrix.pushPose();
		matrix.translate(0, entity.getBbHeight() / 2f, 0);
		matrix.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partial, entity.yRotO, entity.getYRot()) - 90));
		matrix.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partial, entity.xRotO, entity.getXRot())));
		matrix.mulPose(Axis.XP.rotationDegrees(entity.getZRot()));
		matrix.mulPose(Axis.ZP.rotationDegrees(-90f));
		matrix.scale(0.05625F, 0.05625F, 0.05625F);
		VertexConsumer ivertexbuilder = buffer.getBuffer(RenderType.entityTranslucent(getTextureLocation(entity)));
		PoseStack.Pose entry = matrix.last();
		rect(entry, ivertexbuilder, 0, 8, -1, light);
		rect(entry, ivertexbuilder, 0, 8, 1, light);
		matrix.popPose();
		super.render(entity, yRot, partial, matrix, buffer, light);
	}

	private void rect(PoseStack.Pose pose, VertexConsumer builder, float x, float r, int n, int light) {
		vertex(pose, builder, r, -r, x, 0, 0, n, 0, 0, light);
		vertex(pose, builder, r, r, x, 1, 0, n, 0, 0, light);
		vertex(pose, builder, -r, r, x, 1, 1, n, 0, 0, light);
		vertex(pose, builder, -r, -r, x, 0, 1, n, 0, 0, light);
	}

	private void vertex(PoseStack.Pose pose, VertexConsumer builder, float x, float y, float z, float u, float v, int nx, int nz, int ny, int light) {
		builder.addVertex(pose, x, y, z)
				.setColor(255, 255, 255, 255)
				.setUv(u, v)
				.setOverlay(OverlayTexture.NO_OVERLAY)
				.setLight(light)
				.setNormal(pose, nx, ny, nz);
	}

	@Override
	public ResourceLocation getTextureLocation(WindBladeEntity entity) {
		if (entity.getStack().getItem() instanceof WindBladeWeapon weapon) {
			return weapon.bladeTexture();
		}
		return TEXTURE;
	}
}
