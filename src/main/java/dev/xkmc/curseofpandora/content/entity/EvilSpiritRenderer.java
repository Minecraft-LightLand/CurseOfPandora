package dev.xkmc.curseofpandora.content.entity;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EvilSpiritRenderer extends MobRenderer<EvilSpirit, EvilSpiritModel> {
	private static final ResourceLocation VEX_LOCATION = new ResourceLocation("textures/entity/illager/vex.png");
	private static final ResourceLocation VEX_CHARGING_LOCATION = new ResourceLocation("textures/entity/illager/vex_charging.png");

	public EvilSpiritRenderer(EntityRendererProvider.Context ctx) {
		super(ctx, new EvilSpiritModel(ctx.bakeLayer(ModelLayers.VEX)), 0.3F);
		this.addLayer(new ItemInHandLayer<>(this, ctx.getItemInHandRenderer()));
	}

	protected int getBlockLightLevel(EvilSpirit entity, BlockPos pos) {
		return 15;
	}

	public ResourceLocation getTextureLocation(EvilSpirit entity) {
		return entity.isCharging() ? VEX_CHARGING_LOCATION : VEX_LOCATION;
	}
}