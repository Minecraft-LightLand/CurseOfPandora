package dev.xkmc.curseofpandora.content.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import net.minecraft.client.model.EvokerFangsModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class AbyssalFangsRenderer extends EntityRenderer<AbyssalFangs> {
   private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(CurseOfPandora.MODID,
           "textures/entity/abyssal_fangs.png");
   private final EvokerFangsModel<AbyssalFangs> model;

   public AbyssalFangsRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
      this.model = new EvokerFangsModel<>(ctx.bakeLayer(ModelLayers.EVOKER_FANGS));
   }

   public void render(AbyssalFangs entity, float f0, float pTck, PoseStack pose, MultiBufferSource buffer, int light) {
      float f = entity.getAnimationProgress(pTck);
      if (f != 0.0F) {
         float f1 = 2.0F;
         if (f > 0.9F) {
            f1 *= (1.0F - f) / 0.1F;
         }

         pose.pushPose();
         pose.mulPose(Axis.YP.rotationDegrees(90.0F - entity.getYRot()));
         pose.scale(-f1, -f1, f1);
         float f2 = 0.03125F;
         pose.translate(0.0D, -0.626D, 0.0D);
         pose.scale(0.5F, 0.5F, 0.5F);
         this.model.setupAnim(entity, f, 0.0F, 0.0F, entity.getYRot(), entity.getXRot());
         VertexConsumer vertexconsumer = buffer.getBuffer(this.model.renderType(TEXTURE_LOCATION));
         this.model.renderToBuffer(pose, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
         pose.popPose();
         super.render(entity, f0, pTck, pose, buffer, light);
      }
   }

   public ResourceLocation getTextureLocation(AbyssalFangs p_114526_) {
      return TEXTURE_LOCATION;
   }
}