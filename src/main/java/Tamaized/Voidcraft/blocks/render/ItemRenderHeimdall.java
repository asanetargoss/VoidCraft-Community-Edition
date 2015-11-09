package Tamaized.Voidcraft.blocks.render;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import Tamaized.Voidcraft.blocks.model.BlockModelHeimdall;

public class ItemRenderHeimdall implements IItemRenderer {

	private final BlockModelHeimdall model;
	
	private RenderHeimdall render;
	
	private TileEntity entity;

	public ItemRenderHeimdall(RenderHeimdall render, TileEntity te){
		this.model = new BlockModelHeimdall();
		
		this.entity = te;
		this.render = render;
	}

	@Override
	public boolean handleRenderType( ItemStack itemStack, ItemRenderType type ){
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper( ItemRenderType type, ItemStack item, ItemRendererHelper helper ){
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data){
		if(type == IItemRenderer.ItemRenderType.ENTITY) GL11.glTranslatef(-0.5F,  0.0F,  -0.5F);
		this.render.renderTileEntityAt(this.entity, 0.0d, 0.0d, 0.0d, 0.0f);
		model.render( null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F );
	}
}