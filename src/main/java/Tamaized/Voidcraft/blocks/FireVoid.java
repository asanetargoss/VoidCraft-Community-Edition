package Tamaized.Voidcraft.blocks;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.UP;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.util.Random;

import net.minecraft.block.BlockFire;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import Tamaized.Voidcraft.common.voidCraft;
import Tamaized.Voidcraft.mobs.EntityVoidMob;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FireVoid extends BlockFire{
	
	@SideOnly(Side.CLIENT)
	private IIcon[] iconArray;

	public FireVoid() {
		super();
	}
	
	public void onBlockAdded(World world, int x, int y, int z){
		if(world.getBlock(x, y-1, z) != voidCraft.blockVoidcrystal || !((BlockTeleporter)voidCraft.blockTeleporterVoid).tryToCreatePortal(world, x, y, z)){
			if(!world.doesBlockHaveSolidTopSurface(world, x, y-1, z) && !this.canNeighborBurn(world, x, y, z)){
				world.setBlockToAir(x, y, z);
			}else{
				world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world) + world.rand.nextInt(10));
			}
		}else{
			world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world) + world.rand.nextInt(10));
		}
	}
	
	public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity entity) {
		if(entity instanceof EntityLivingBase && !(entity instanceof EntityVoidMob)){
			if(entity instanceof EntitySkeleton){
				EntitySkeleton skelly = (EntitySkeleton) entity;
				if(Integer.valueOf(skelly.getDataWatcher().getWatchableObjectByte(13))==1) return;
			}
			EntityLivingBase e = ((EntityLivingBase) entity);
			e.addPotionEffect(new PotionEffect(Potion.confusion.getId(), 20, 1));
			e.addPotionEffect(new PotionEffect(Potion.wither.getId(), 20, 1));
			e.addPotionEffect(new PotionEffect(Potion.blindness.getId(), 20, 1));
		}
	}
	
	private boolean canNeighborBurn(World par1World, int par2, int par3, int par4){
		return false;
	}
	
	@SideOnly(Side.CLIENT) 
	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	 public void registerBlockIcons(IIconRegister par1IconRegister){
		 this.iconArray = new IIcon[] {par1IconRegister.registerIcon(this.getTextureName() + "_layer_0"), par1IconRegister.registerIcon(this.getTextureName() + "_layer_1")};
	 }

	 @SideOnly(Side.CLIENT)
	 public IIcon getFireIcon(int par1){
		 return this.iconArray[par1];
	 }

	 @SideOnly(Side.CLIENT)
	 /**
	  * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	  */
	 public IIcon getIcon(int par1, int par2){
		 return this.iconArray[0];
	 }	
	 
	 public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
	    {
	        if (p_149674_1_.getGameRules().getGameRuleBooleanValue("doFireTick"))
	        {
	            boolean flag = p_149674_1_.getBlock(p_149674_2_, p_149674_3_ - 1, p_149674_4_).isFireSource(p_149674_1_, p_149674_2_, p_149674_3_ - 1, p_149674_4_, UP);

	            if (!this.canPlaceBlockAt(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_))
	            {
	                p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
	            }

	            if (!flag && p_149674_1_.isRaining() && (p_149674_1_.canLightningStrikeAt(p_149674_2_, p_149674_3_, p_149674_4_) || p_149674_1_.canLightningStrikeAt(p_149674_2_ - 1, p_149674_3_, p_149674_4_) || p_149674_1_.canLightningStrikeAt(p_149674_2_ + 1, p_149674_3_, p_149674_4_) || p_149674_1_.canLightningStrikeAt(p_149674_2_, p_149674_3_, p_149674_4_ - 1) || p_149674_1_.canLightningStrikeAt(p_149674_2_, p_149674_3_, p_149674_4_ + 1)))
	            {
	                p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
	            }
	            else
	            {
	                int l = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);

	                if (l < 15)
	                {
	                    p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, l + p_149674_5_.nextInt(3) / 2, 4);
	                }

	                p_149674_1_.scheduleBlockUpdate(p_149674_2_, p_149674_3_, p_149674_4_, this, this.tickRate(p_149674_1_) + p_149674_5_.nextInt(10));

	                if (!flag && !this.canNeighborBurn(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_))
	                {
	                    if (!World.doesBlockHaveSolidTopSurface(p_149674_1_, p_149674_2_, p_149674_3_ - 1, p_149674_4_) || l > 3)
	                    {
	                        if(!(p_149674_1_.getBlock(p_149674_2_, p_149674_3_ - 1, p_149674_4_) instanceof blockVoidcrystal)) p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
	                    }
	                }
	                else if (!flag && !this.canCatchFire(p_149674_1_, p_149674_2_, p_149674_3_ - 1, p_149674_4_, UP) && l == 15 && p_149674_5_.nextInt(4) == 0)
	                {
	                    p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
	                }
	                else
	                {
	                    boolean flag1 = p_149674_1_.isBlockHighHumidity(p_149674_2_, p_149674_3_, p_149674_4_);
	                    byte b0 = 0;

	                    if (flag1)
	                    {
	                        b0 = -50;
	                    }

	                    this.tryCatchFire(p_149674_1_, p_149674_2_ + 1, p_149674_3_, p_149674_4_, 300 + b0, p_149674_5_, l, WEST );
	                    this.tryCatchFire(p_149674_1_, p_149674_2_ - 1, p_149674_3_, p_149674_4_, 300 + b0, p_149674_5_, l, EAST );
	                    this.tryCatchFire(p_149674_1_, p_149674_2_, p_149674_3_ - 1, p_149674_4_, 250 + b0, p_149674_5_, l, UP   );
	                    this.tryCatchFire(p_149674_1_, p_149674_2_, p_149674_3_ + 1, p_149674_4_, 250 + b0, p_149674_5_, l, DOWN );
	                    this.tryCatchFire(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_ - 1, 300 + b0, p_149674_5_, l, SOUTH);
	                    this.tryCatchFire(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_ + 1, 300 + b0, p_149674_5_, l, NORTH);

	                    for (int i1 = p_149674_2_ - 1; i1 <= p_149674_2_ + 1; ++i1)
	                    {
	                        for (int j1 = p_149674_4_ - 1; j1 <= p_149674_4_ + 1; ++j1)
	                        {
	                            for (int k1 = p_149674_3_ - 1; k1 <= p_149674_3_ + 4; ++k1)
	                            {
	                                if (i1 != p_149674_2_ || k1 != p_149674_3_ || j1 != p_149674_4_)
	                                {
	                                    int l1 = 100;

	                                    if (k1 > p_149674_3_ + 1)
	                                    {
	                                        l1 += (k1 - (p_149674_3_ + 1)) * 100;
	                                    }

	                                    int i2 = this.getChanceOfNeighborsEncouragingFire(p_149674_1_, i1, k1, j1);

	                                    if (i2 > 0)
	                                    {
	                                        int j2 = (i2 + 40 + p_149674_1_.difficultySetting.getDifficultyId() * 7) / (l + 30);

	                                        if (flag1)
	                                        {
	                                            j2 /= 2;
	                                        }

	                                        if (j2 > 0 && p_149674_5_.nextInt(l1) <= j2 && (!p_149674_1_.isRaining() || !p_149674_1_.canLightningStrikeAt(i1, k1, j1)) && !p_149674_1_.canLightningStrikeAt(i1 - 1, k1, p_149674_4_) && !p_149674_1_.canLightningStrikeAt(i1 + 1, k1, j1) && !p_149674_1_.canLightningStrikeAt(i1, k1, j1 - 1) && !p_149674_1_.canLightningStrikeAt(i1, k1, j1 + 1))
	                                        {
	                                            int k2 = l + p_149674_5_.nextInt(5) / 4;

	                                            if (k2 > 15)
	                                            {
	                                                k2 = 15;
	                                            }

	                                            p_149674_1_.setBlock(i1, k1, j1, this, k2, 3);
	                                        }
	                                    }
	                                }
	                            }
	                        }
	                    }
	                }
	            }
	        }
	    }
	 
	 private void tryCatchFire(World p_149841_1_, int p_149841_2_, int p_149841_3_, int p_149841_4_, int p_149841_5_, Random p_149841_6_, int p_149841_7_, ForgeDirection face)
	    {
	        int j1 = p_149841_1_.getBlock(p_149841_2_, p_149841_3_, p_149841_4_).getFlammability(p_149841_1_, p_149841_2_, p_149841_3_, p_149841_4_, face);

	        if (p_149841_6_.nextInt(p_149841_5_) < j1)
	        {
	            boolean flag = p_149841_1_.getBlock(p_149841_2_, p_149841_3_, p_149841_4_) == Blocks.tnt;

	            if (p_149841_6_.nextInt(p_149841_7_ + 10) < 5 && !p_149841_1_.canLightningStrikeAt(p_149841_2_, p_149841_3_, p_149841_4_))
	            {
	                int k1 = p_149841_7_ + p_149841_6_.nextInt(5) / 4;

	                if (k1 > 15)
	                {
	                    k1 = 15;
	                }

	                p_149841_1_.setBlock(p_149841_2_, p_149841_3_, p_149841_4_, this, k1, 3);
	            }
	            else
	            {
	                p_149841_1_.setBlockToAir(p_149841_2_, p_149841_3_, p_149841_4_);
	            }

	            if (flag)
	            {
	                Blocks.tnt.onBlockDestroyedByPlayer(p_149841_1_, p_149841_2_, p_149841_3_, p_149841_4_, 1);
	            }
	        }
	    }
	 
	 /**
	     * Gets the highest chance of a neighbor block encouraging this block to catch fire
	     */
	    private int getChanceOfNeighborsEncouragingFire(World p_149845_1_, int p_149845_2_, int p_149845_3_, int p_149845_4_)
	    {
	        byte b0 = 0;

	        if (!p_149845_1_.isAirBlock(p_149845_2_, p_149845_3_, p_149845_4_))
	        {
	            return 0;
	        }
	        else
	        {
	            int l = b0;
	            l = this.getChanceToEncourageFire(p_149845_1_, p_149845_2_ + 1, p_149845_3_, p_149845_4_, l, WEST );
	            l = this.getChanceToEncourageFire(p_149845_1_, p_149845_2_ - 1, p_149845_3_, p_149845_4_, l, EAST );
	            l = this.getChanceToEncourageFire(p_149845_1_, p_149845_2_, p_149845_3_ - 1, p_149845_4_, l, UP   );
	            l = this.getChanceToEncourageFire(p_149845_1_, p_149845_2_, p_149845_3_ + 1, p_149845_4_, l, DOWN );
	            l = this.getChanceToEncourageFire(p_149845_1_, p_149845_2_, p_149845_3_, p_149845_4_ - 1, l, SOUTH);
	            l = this.getChanceToEncourageFire(p_149845_1_, p_149845_2_, p_149845_3_, p_149845_4_ + 1, l, NORTH);
	            return l;
	        }
	    }
}