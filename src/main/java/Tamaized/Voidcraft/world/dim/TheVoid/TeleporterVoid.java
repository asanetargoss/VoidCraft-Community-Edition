package Tamaized.Voidcraft.world.dim.TheVoid;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;

import java.util.Random;

import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import Tamaized.Voidcraft.blocks.BlockPortalVoid;
import Tamaized.Voidcraft.common.voidCraft;

public class TeleporterVoid extends Teleporter {

	private final WorldServer worldServerInstance;
	private final Random random;

	private final Long2ObjectMap<Teleporter.PortalPosition> destinationCoordinateCache = new Long2ObjectOpenHashMap(4096);

	public TeleporterVoid(WorldServer par1WorldServer) {
		super(par1WorldServer);

		this.worldServerInstance = par1WorldServer;
		this.random = new Random(par1WorldServer.getSeed());
	}

	/**
	 * Place an entity in a nearby portal, creating one if necessary.
	 */
	@Override
	public void placeInPortal(Entity entity, float rotationYaw){
		if(entity instanceof EntityPlayer) ((EntityPlayer) entity).addStat(voidCraft.achievements.voidCraftAchMainLine_2, 1);
	
		if (!this.placeInExistingPortal(entity, rotationYaw)){
			this.makePortal(entity);
			this.placeInExistingPortal(entity, rotationYaw);
		}
	}

	@Override
	public boolean placeInExistingPortal(Entity entity, float rotationYaw){ //TODO simulate entity vectors, use code from 1.8.9
		int i = 128;
		double d0 = -1.0D;
        int j = MathHelper.floor_double(entity.posX);
        int k = MathHelper.floor_double(entity.posZ);
		boolean flag = true;
        BlockPos blockpos = BlockPos.ORIGIN;
        long l = ChunkPos.chunkXZ2Int(j, k);
        
        if (this.destinationCoordinateCache.containsKey(l)){
    		Teleporter.PortalPosition teleporter$portalposition = (Teleporter.PortalPosition)this.destinationCoordinateCache.get(l);
            d0 = 0.0D;
            blockpos = teleporter$portalposition;
            teleporter$portalposition.lastUpdateTime = this.worldServerInstance.getTotalWorldTime();
            flag = false;
        }else{
        	BlockPos blockpos3 = new BlockPos(entity);
            for (int i1 = -128; i1 <= 128; ++i1){
            	BlockPos blockpos2;
                
                for (int j1 = -128; j1 <= 128; ++j1){
                	for (BlockPos blockpos1 = blockpos3.add(i1, this.worldServerInstance.getActualHeight() - 1 - blockpos3.getY(), j1); blockpos1.getY() >= 0; blockpos1 = blockpos2){
                    	blockpos2 = blockpos1.down();
                        
                        if (this.worldServerInstance.getBlockState(blockpos1).getBlock() == voidCraft.blocks.blockPortalVoid){
                        	while (this.worldServerInstance.getBlockState(blockpos2 = blockpos1.down()).getBlock() == voidCraft.blocks.blockPortalVoid){
                            	blockpos1 = blockpos2;
                            }
                            
                            double d1 = blockpos1.distanceSq(blockpos3);
                            
                            if (d0 < 0.0D || d1 < d0){
                            	d0 = d1;
                                blockpos = blockpos1;
                            }
                        }
                    }
                }
            }
        }
        
        if(d0 >= 0.0D){
        	if (flag){
        		this.destinationCoordinateCache.put(l, new Teleporter.PortalPosition(blockpos, this.worldServerInstance.getTotalWorldTime()));
        	}

        	double d5 = (double)blockpos.getX() + 0.5D;
        	double d6 = (double)blockpos.getY() + 0.5D;
        	double d7 = (double)blockpos.getZ() + 0.5D;
            double origYd6 = d6;
        	BlockPattern.PatternHelper blockpattern$patternhelper = ((BlockPortalVoid)voidCraft.blocks.blockPortalVoid).createPatternHelper(this.worldServerInstance, blockpos);
            boolean flag1 = blockpattern$patternhelper.getForwards().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE;
            double d2 = blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X ? (double)blockpattern$patternhelper.getFrontTopLeft().getZ() : (double)blockpattern$patternhelper.getFrontTopLeft().getX();
        	
        	double aG0 = blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X ? (double)blockpattern$patternhelper.getFrontTopLeft().getZ() : (double)blockpattern$patternhelper.getFrontTopLeft().getX(); 
        	double aG1 = blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X ? entity.posZ : entity.posX;
        	aG1 = Math.abs(MathHelper.pct(aG1 - (double)(blockpattern$patternhelper.getForwards().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE ? 1 : 0), aG0, aG0 - (double)blockpattern$patternhelper.getWidth()));
        	double aG2 = MathHelper.pct(entity.posY - 1.0D, (double)blockpattern$patternhelper.getFrontTopLeft().getY(), (double)(blockpattern$patternhelper.getFrontTopLeft().getY() - blockpattern$patternhelper.getHeight()));
        	Vec3d aG = new Vec3d(aG1, aG2, 0.0D);
        	EnumFacing eTD = blockpattern$patternhelper.getForwards();
        	
        	d6 = (double)(blockpattern$patternhelper.getFrontTopLeft().getY() + 1) - aG.yCoord * (double)blockpattern$patternhelper.getHeight();
            
        	if (flag1){
        		++d2;
        	}
        	
        	if (blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X){
        		d7 = d2 + (1.0D - aG.xCoord) * (double)blockpattern$patternhelper.getWidth() * (double)blockpattern$patternhelper.getForwards().rotateY().getAxisDirection().getOffset();
        	}else{
        		d5 = d2 + (1.0D - aG.xCoord) * (double)blockpattern$patternhelper.getWidth() * (double)blockpattern$patternhelper.getForwards().rotateY().getAxisDirection().getOffset();
        	}
        	
        	float f = 0.0F;
        	float f1 = 0.0F;
        	float f2 = 0.0F;
        	float f3 = 0.0F;
        	
        	if (blockpattern$patternhelper.getForwards().getOpposite() == eTD){
        		f = 1.0F;
        		f1 = 1.0F;
        	}else if (blockpattern$patternhelper.getForwards().getOpposite() == eTD.getOpposite()){
        		f = -1.0F;
        		f1 = -1.0F;
        	}else if (blockpattern$patternhelper.getForwards().getOpposite() == eTD.rotateY()){
        		f2 = 1.0F;
        		f3 = -1.0F;
            }else{
            	f2 = -1.0F;
            	f3 = 1.0F;
            }
        	
        	double d3 = entity.motionX;
        	double d4 = entity.motionZ;
        	entity.motionX = d3 * (double)f + d4 * (double)f3;
        	entity.motionZ = d3 * (double)f2 + d4 * (double)f1;
        	entity.rotationYaw = rotationYaw - (float)(eTD.getOpposite().getHorizontalIndex() * 90) + (float)(blockpattern$patternhelper.getForwards().getHorizontalIndex() * 90);
        	d6 = d6 < origYd6 ? origYd6+1 : d6;
        	entity.setLocationAndAngles(d5, d6, d7, entity.rotationYaw, entity.rotationPitch);
        	return true;
        }else{
        	return false;
        }
	}
	
	private boolean func_180265_a(BlockPos p_180265_1_)
    {
        return !this.worldServerInstance.isAirBlock(p_180265_1_) || !this.worldServerInstance.isAirBlock(p_180265_1_.up());
    }

	
	@Override
	public boolean makePortal(Entity entityIn) {
		int i = 16;
		double d0 = -1.0D;
		int j = MathHelper.floor_double(entityIn.posX);
		int k = MathHelper.floor_double(entityIn.posY);
		int l = MathHelper.floor_double(entityIn.posZ);
		int i1 = j;
		int j1 = k;
		int k1 = l;
		int l1 = 0;
		int i2 = this.random.nextInt(4);
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
		
		
		for(int j2 = j - i; j2 <= j + i; ++j2){
			
			double d1 = (double)j2 + 0.5D - entityIn.posX;	
			
			for(int l2 = l - i; l2 <= l + i; ++l2){
                double d2 = (double)l2 + 0.5D - entityIn.posZ;
                label142:
                	for(int j3 = this.worldServerInstance.getActualHeight() - 1; j3 >= 0; --j3){
                		if(this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.set(j2, j3, l2))){
                			while (j3 > 0 && this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.set(j2, j3 - 1, l2))){
                				--j3;
                			}
							
							for(int k3 = i2; k3 < i2 + 4; ++k3){
	                            int l3 = k3 % 2;
	                            int i4 = 1 - l3;

	                            if (k3 % 4 >= 2){
	                                l3 = -l3;
	                                i4 = -i4;
	                            }
								
	                            for(int j4 = 0; j4 < 3; ++j4){
	                            	for(int k4 = 0; k4 < 4; ++k4){
	                            		for(int l4 = -1; l4 < 4; ++l4){
	                            			int i5 = j2 + (k4 - 1) * l3 + j4 * i4;
	                            			int j5 = j3 + l4;
	                            			int k5 = l2 + (k4 - 1) * i4 - j4 * l3;
	                            			blockpos$mutableblockpos.set(i5, j5, k5);
	                            			
	                            			if(l4 < 0 && !this.worldServerInstance.getBlockState(blockpos$mutableblockpos).getMaterial().isSolid() || l4 >= 0 && !this.worldServerInstance.isAirBlock(blockpos$mutableblockpos)){
	                            				continue label142;
	                            			}
	                            		}
	                            	}
	                            }

	                            double d5 = (double)j3 + 0.5D - entityIn.posY;
	                            double d7 = d1 * d1 + d5 * d5 + d2 * d2;
								
	                            if (d0 < 0.0D || d7 < d0){
	                            	d0 = d7;
	                            	i1 = j2;
	                            	j1 = j3;
	                            	k1 = l2;
	                            	l1 = k3 % 4;
	                            }
							}
                		}
                	}
			}
		}

		if (d0 < 0.0D) {
			for (int l5 = j - i; l5 <= j + i; ++l5) {
				double d3 = (double)l5 + 0.5D - entityIn.posX;
				
				for(int j6 = l - i; j6 <= l + i; ++j6){
					double d4 = (double)j6 + 0.5D - entityIn.posZ;
					label562:
						for(int i7 = this.worldServerInstance.getActualHeight() - 1; i7 >= 0; --i7){
							if(this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.set(l5, i7, j6))){
								while (i7 > 0 && this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.set(l5, i7 - 1, j6))){
	                            	--i7;
								}

								for (int k7 = i2; k7 < i2 + 2; ++k7){
	                                int j8 = k7 % 2;
	                                int j9 = 1 - j8;
	                                
	                                for (int j10 = 0; j10 < 4; ++j10){
	                                	for (int j11 = -1; j11 < 4; ++j11){
	                                		int j12 = l5 + (j10 - 1) * j8;
	                                		int i13 = i7 + j11;
	                                		int j13 = j6 + (j10 - 1) * j9;
	                                		blockpos$mutableblockpos.set(j12, i13, j13);
	                                		
	                                		if (j11 < 0 && !this.worldServerInstance.getBlockState(blockpos$mutableblockpos).getMaterial().isSolid() || j11 >= 0 && !this.worldServerInstance.isAirBlock(blockpos$mutableblockpos)){
	                                			continue label562;
	                                        }
										}
									}

	                                double d6 = (double)i7 + 0.5D - entityIn.posY;
	                                double d8 = d3 * d3 + d6 * d6 + d4 * d4;
	                                
	                                if(d0 < 0.0D || d8 < d0){
	                                	d0 = d8;
	                                	i1 = l5;
	                                	j1 = i7;
	                                	k1 = j6;
	                                	l1 = k7 % 2;
	                                }
								}
							}
						}
				}
			}
		}
		
		int i6 = i1;
		int k2 = j1;
		int k6 = k1;
		int l6 = l1 % 2;
		int i3 = 1 - l6;	
		
		if (l1 % 4 >= 2){
			l6 = -l6;
			i3 = -i3;
		}
		
		if (d0 < 0.0D) {
			j1 = MathHelper.clamp_int(j1, 70, this.worldServerInstance.getActualHeight() - 10);
            k2 = j1;

            for (int j7 = -1; j7 <= 1; ++j7)
            {
                for (int l7 = 1; l7 < 3; ++l7)
                {
                    for (int k8 = -1; k8 < 3; ++k8)
                    {
                        int k9 = i6 + (l7 - 1) * l6 + j7 * i3;
                        int k10 = k2 + k8;
                        int k11 = k6 + (l7 - 1) * i3 - j7 * l6;
                        boolean flag = k8 < 0;
                        this.worldServerInstance.setBlockState(new BlockPos(k9, k10, k11), flag ? voidCraft.blocks.blockVoidcrystal.getDefaultState() : Blocks.AIR.getDefaultState());
                    }
                }
            }
		}
		
		IBlockState iblockstate = voidCraft.blocks.blockPortalVoid.getDefaultState().withProperty(BlockPortal.AXIS, l6 != 0 ? EnumFacing.Axis.X : EnumFacing.Axis.Z);

		for (int i8 = 0; i8 < 4; ++i8){
			for (int l8 = 0; l8 < 4; ++l8){
				for (int l9 = -1; l9 < 4; ++l9){
					int l10 = i6 + (l8 - 1) * l6;
					int l11 = k2 + l9;
					int k12 = k6 + (l8 - 1) * i3;
					boolean flag1 = l8 == 0 || l8 == 3 || l9 == -1 || l9 == 3;
					this.worldServerInstance.setBlockState(new BlockPos(l10, l11, k12), flag1 ? voidCraft.blocks.blockVoidcrystal.getDefaultState() : iblockstate, 2);
				}
			}
			
			for (int i9 = 0; i9 < 4; ++i9){
				for (int i10 = -1; i10 < 4; ++i10){
					int i11 = i6 + (i9 - 1) * l6;
					int i12 = k2 + i10;
					int l12 = k6 + (i9 - 1) * i3;
					BlockPos blockpos = new BlockPos(i11, i12, l12);
					this.worldServerInstance.notifyNeighborsOfStateChange(blockpos, this.worldServerInstance.getBlockState(blockpos).getBlock());
				}
			}
		}
		
		return true;
	}

	@Override
	public void removeStalePortalLocations(long par1) {

	}

}
