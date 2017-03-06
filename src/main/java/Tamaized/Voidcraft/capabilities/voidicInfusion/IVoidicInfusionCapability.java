package Tamaized.Voidcraft.capabilities.voidicInfusion;

import java.io.IOException;

import io.netty.buffer.ByteBufInputStream;
import net.minecraft.entity.EntityLivingBase;

public interface IVoidicInfusionCapability {

	public boolean hasLoaded();

	public void load(EntityLivingBase living);

	public void update(EntityLivingBase player);
	
	public int getInfusion();

	public void addInfusion(int amount);

	public void setInfusion(int i);

	public int getMaxInfusion();

	public void setMaxInfusion(int i);

	public float getInfusionPerc();

	public boolean canFly(EntityLivingBase entity);
	
	public int getXiaDefeats();
	
	public void setXiaDefeats(int amount);

	public void copyFrom(IVoidicInfusionCapability cap);

	public void decodePacket(ByteBufInputStream stream) throws IOException;

}
