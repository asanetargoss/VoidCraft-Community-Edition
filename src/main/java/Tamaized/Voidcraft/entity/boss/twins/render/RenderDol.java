package Tamaized.Voidcraft.entity.boss.twins.render;

import Tamaized.Voidcraft.voidCraft;
import Tamaized.Voidcraft.entity.boss.model.ModelVoidBoss;
import Tamaized.Voidcraft.entity.boss.twins.EntityBossDol;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDol<T extends EntityLiving> extends RenderLiving<T> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(voidCraft.modid, "textures/entity/dol.png");

	public RenderDol(float shadow) {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelVoidBoss<EntityBossDol>(), shadow);
	}

	@Override
	public void doRender(T entity, double x, double y, double z, float yaw, float ticks) {
		super.doRender(entity, x, y, z, yaw, ticks);
		this.renderLabel(entity, x, y, z);
	}

	protected void renderLabel(T yourentityLiving, double par2, double par4, double par6) {
		int distanceToEntity = 32;
		this.renderLivingLabel(yourentityLiving, yourentityLiving.getDisplayName().getFormattedText(), par2, par4, par6, distanceToEntity);
		par4 += (double) ((float) this.getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * par6);
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return TEXTURE;
	}
}