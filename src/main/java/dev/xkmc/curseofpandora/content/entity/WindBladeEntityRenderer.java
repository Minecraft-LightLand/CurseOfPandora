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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class WindBladeEntityRenderer extends EntityRenderer<WindBladeEntity> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(CurseOfPandora.MODID, "textures/entity/wind_blade.png");

	public WindBladeEntityRenderer(EntityRendererProvider.Context manager) {
		super(manager);
	}

	@Override
	public void render(WindBladeEntity entity, float yRot, float partial, PoseStack matrix, MultiBufferSource buffer, int light) {
		if(entity.getStack().getItem() instanceof WindBladeWeapon weapon && weapon.glow()){
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
		Matrix4f matrix4f = entry.pose();
		Matrix3f matrix3f = entry.normal();
		rect(matrix4f, matrix3f, ivertexbuilder, 0, 8, -1, light);
		rect(matrix4f, matrix3f, ivertexbuilder, 0, 8, 1, light);
		matrix.popPose();
		super.render(entity, yRot, partial, matrix, buffer, light);
	}

	private void rect(Matrix4f m4f, Matrix3f m3f, VertexConsumer builder, float x, float r, int n, int light) {
		vertex(m4f, m3f, builder, r, -r, x, 0, 0, n, 0, 0, light);
		vertex(m4f, m3f, builder, r, r, x, 1, 0, n, 0, 0, light);
		vertex(m4f, m3f, builder, -r, r, x, 1, 1, n, 0, 0, light);
		vertex(m4f, m3f, builder, -r, -r, x, 0, 1, n, 0, 0, light);
	}

	private void vertex(Matrix4f m4f, Matrix3f m3f, VertexConsumer builder, float x, float y, float z, float u, float v, int nx, int nz, int ny, int light) {
		builder.vertex(m4f, x, y, z)
				.color(255, 255, 255, 255)
				.uv(u, v)
				.overlayCoords(OverlayTexture.NO_OVERLAY)
				.uv2(light)
				.normal(m3f, nx, ny, nz)
				.endVertex();
	}

	@Override
	public ResourceLocation getTextureLocation(WindBladeEntity entity) {
		return TEXTURE;
	}
}
