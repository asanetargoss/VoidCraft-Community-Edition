package tamaized.dalquor.common.entity.nonliving;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.dalquor.common.damagesources.DamageSourceAcid;
import tamaized.dalquor.common.entity.boss.xia.EntityBossXia;
import tamaized.dalquor.common.entity.boss.xia.EntityBossXia2;

import javax.annotation.Nullable;

public class ProjectileDisintegration extends EntityArrow implements IProjectile, IEntityAdditionalSpawnData {

	private static final Predicate<Entity> ARROW_TARGETS = Predicates.and(new Predicate[]{EntitySelectors.NOT_SPECTATING, EntitySelectors.IS_ALIVE, new Predicate<Entity>() {
		public boolean apply(@Nullable Entity p_apply_1_) {
			return p_apply_1_.canBeCollidedWith();
		}
	}});
	private int xTile;
	private int yTile;
	private int zTile;
	private Block inTile;
	private int inData;
	protected boolean inGround;
	protected int timeInGround;
	/**
	 * Seems to be some sort of timer for animating an arrow.
	 */
	public int arrowShake;
	/**
	 * The owner of this arrow.
	 */
	public Entity shootingEntity;
	private int ticksInGround;
	private int ticksInAir;
	private double damage;
	/**
	 * The amount of knockback an arrow applies when it hits a mob.
	 */
	private int knockbackStrength;

	private double speed = 0.5D;
	private float range = 0.0F;

	public ProjectileDisintegration(World worldIn) {
		super(worldIn);
		xTile = -1;
		yTile = -1;
		zTile = -1;
		pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
		damage = 2.0D;
		setSize(0.5F, 0.5F);
	}

	public ProjectileDisintegration(World worldIn, double x, double y, double z) {
		this(worldIn);
		setPosition(x, y, z);
	}

	public ProjectileDisintegration(World worldIn, EntityLivingBase shooter, double x, double y, double z) {
		this(worldIn);
		shootingEntity = shooter;
		setPosition(x, y + shooter.getEyeHeight(), z);
		Vec3d vec = shooter.getLook(1.0f);
		setTheVelocity(vec.x, vec.y, vec.z);
	}

	public ProjectileDisintegration(World worldIn, EntityLivingBase shooter, EntityLivingBase target, float dmg) {
		this(worldIn, shooter.posX, shooter.posY + (double) shooter.getEyeHeight() - 0.10000000149011612D, shooter.posZ);
		shootingEntity = shooter;
		damage = dmg;
		double d0 = target.posX - posX;
		double d1 = target.getEntityBoundingBox().minY + (double) (target.height / 2.0F) - posY;
		double d2 = target.posZ - posZ;
		double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
		shoot(d0, d1/* + d3 * 0.20000000298023224D */, d2, 1.6F, (float) (14 - world.getDifficulty().getId() * 4));
	}

	public void setTheVelocity(double x, double y, double z) {
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt(x * x + z * z);
			this.rotationPitch = (float) (MathHelper.atan2(y, (double) f) * (180D / Math.PI));
			this.rotationYaw = (float) (MathHelper.atan2(x, z) * (180D / Math.PI));
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			this.ticksInGround = 0;
		}
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeDouble(posX);
		buffer.writeDouble(posY);
		buffer.writeDouble(posZ);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		setPosition(additionalData.readDouble(), additionalData.readDouble(), additionalData.readDouble());
	}

	@Override
	protected void entityInit() {

	}

	@Override
	public void setIsCritical(boolean critical) {

	}

	@Override
	public boolean getIsCritical() {
		return false;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		// super.onUpdate();
		onEntityUpdate();

		if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);
			prevRotationYaw = rotationYaw = (float) (Math.atan2(motionX, motionZ) * (180.0D / Math.PI));
			prevRotationPitch = rotationPitch = (float) (Math.atan2(motionY, (double) f) * (180.0D / Math.PI));
		}

		BlockPos blockpos = new BlockPos(xTile, yTile, zTile);
		IBlockState iblockstate = world.getBlockState(blockpos);
		Block block = iblockstate.getBlock();

		if (iblockstate.getMaterial() != Material.AIR) {// check if hit block
			AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(world, blockpos);
			if (axisalignedbb != Block.NULL_AABB && axisalignedbb.offset(blockpos).contains(new Vec3d(posX, posY, posZ))) {
				inGround = true;
			}
		}

		if (arrowShake > 0) {
			--arrowShake;
		}

		if (inGround) {// inGround stuff, kills the entity here
			int j = block.getMetaFromState(iblockstate);

			if (block == inTile && j == inData) {
				++ticksInGround;

				// if (ticksInGround == 1200) {
				setDead();
				// }
			} else {
				inGround = false;
				motionX *= (double) (rand.nextFloat() * 0.2F);
				motionY *= (double) (rand.nextFloat() * 0.2F);
				motionZ *= (double) (rand.nextFloat() * 0.2F);
				ticksInGround = 0;
				ticksInAir = 0;
			}
			++timeInGround;
			// setDead();
		} else { // Traveling
			timeInGround = 0;
			++ticksInAir;
			if (ticksInAir > 20 * 10)
				setDead();
			Vec3d vec3d1 = new Vec3d(posX, posY, posZ);
			Vec3d vec3d = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
			RayTraceResult raytraceresult = world.rayTraceBlocks(vec3d1, vec3d, false, true, false);
			vec3d1 = new Vec3d(posX, posY, posZ);
			vec3d = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);

			if (raytraceresult != null) {
				vec3d = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
			}

			Entity entity = findEntityOnPath(vec3d1, vec3d);

			if (entity != null) {
				raytraceresult = new RayTraceResult(entity);
			}

			if (raytraceresult != null && raytraceresult.entityHit != null && raytraceresult.entityHit instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) raytraceresult.entityHit;

				if (shootingEntity instanceof EntityPlayer && !((EntityPlayer) shootingEntity).canAttackPlayer(entityplayer)) {
					raytraceresult = null;
				}
			}

			if (raytraceresult != null) {
				onHit(raytraceresult);
			}

			// if (getIsCritical()){
			// for (int k = 0; k < 4; ++k){
			// worldObj.spawnParticle(EnumParticleTypes.CRIT, posX + motionX * (double)k / 4.0D, posY + motionY * (double)k / 4.0D, posZ + motionZ * (double)k / 4.0D, -motionX, -motionY + 0.2D, -motionZ, new int[0]);
			// }
			// }

			posX += motionX * speed;
			posY += motionY * speed;
			posZ += motionZ * speed;
			float f4 = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);
			rotationYaw = (float) (Math.atan2(motionX, motionZ) * (180.0D / Math.PI));

			for (rotationPitch = (float) (MathHelper.atan2(motionY, (double) f4) * (180D / Math.PI)); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F) {
				;
			}

			while (rotationPitch - prevRotationPitch >= 180.0F) {
				prevRotationPitch += 360.0F;
			}

			while (rotationYaw - prevRotationYaw < -180.0F) {
				prevRotationYaw -= 360.0F;
			}

			while (rotationYaw - prevRotationYaw >= 180.0F) {
				prevRotationYaw += 360.0F;
			}

			rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
			rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
			float f1 = 0.99F;
			float f2 = range;

			if (isInWater()) {
				setDead();
				for (int l = 0; l < 4; ++l) {
					f4 = 0.25F;
					world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * (double) f4, posY - motionY * (double) f4, posZ - motionZ * (double) f4, motionX, motionY, motionZ);
				}
				f1 = 0.6F;
			}

			if (isWet()) {
				extinguish();
			}

			motionX *= (double) f1;
			motionY *= (double) f1;
			motionZ *= (double) f1;
			motionY -= (double) f2;
			setPosition(posX, posY, posZ);
			doBlockCollisions();
		}

		if (world.isRemote)
			particles();
	}

	@Override
	protected void onHit(RayTraceResult raytraceResultIn) {
		Entity entity = raytraceResultIn.entityHit;

		if (entity != null) {
			if (entity == shootingEntity || entity instanceof EntityBossXia || entity instanceof EntityBossXia2)
				return;
			float f = MathHelper.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
			int i = MathHelper.ceil((double) f * damage);

			// if (getIsCritical()){
			// i += rand.nextInt(i / 2 + 2);
			// }

			DamageSource damagesource = new DamageSourceAcid();

			if (isBurning() && !(entity instanceof EntityEnderman)) {
				entity.setFire(5);
			}

			if (entity.attackEntityFrom(damagesource, (float) i)) {
				if (entity instanceof EntityLivingBase) {
					EntityLivingBase entitylivingbase = (EntityLivingBase) entity;

					if (!world.isRemote) {
						// entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
					}

					if (knockbackStrength > 0) {
						float f1 = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);

						if (f1 > 0.0F) {
							entitylivingbase.addVelocity(motionX * (double) knockbackStrength * 0.6000000238418579D / (double) f1, 0.1D, motionZ * (double) knockbackStrength * 0.6000000238418579D / (double) f1);
						}
					}

					if (shootingEntity instanceof EntityLivingBase) {
						// EnchantmentHelper.applyThornEnchantments(entitylivingbase, shootingEntity);
						// EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)shootingEntity, entitylivingbase);
					}

					arrowHit(entitylivingbase);

					if (shootingEntity != null && entitylivingbase != shootingEntity && entitylivingbase instanceof EntityPlayer && shootingEntity instanceof EntityPlayerMP) {
						((EntityPlayerMP) shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
					}
				}

				playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));

				if (!(entity instanceof EntityEnderman)) {
					setDead();
				}
			} else {
				motionX *= -0.10000000149011612D;
				motionY *= -0.10000000149011612D;
				motionZ *= -0.10000000149011612D;
				rotationYaw += 180.0F;
				prevRotationYaw += 180.0F;
				ticksInAir = 0;

				if (!world.isRemote && motionX * motionX + motionY * motionY + motionZ * motionZ < 0.0010000000474974513D) {
					if (pickupStatus == EntityArrow.PickupStatus.ALLOWED) {
						entityDropItem(getArrowStack(), 0.1F);
					}

					setDead();
				}
			}
		} else {
			BlockPos blockpos = raytraceResultIn.getBlockPos();
			xTile = blockpos.getX();
			yTile = blockpos.getY();
			zTile = blockpos.getZ();
			IBlockState iblockstate = world.getBlockState(blockpos);
			inTile = iblockstate.getBlock();
			inData = inTile.getMetaFromState(iblockstate);
			motionX = (double) ((float) (raytraceResultIn.hitVec.x - posX));
			motionY = (double) ((float) (raytraceResultIn.hitVec.y - posY));
			motionZ = (double) ((float) (raytraceResultIn.hitVec.z - posZ));
			float f2 = MathHelper.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
			posX -= motionX / (double) f2 * 0.05000000074505806D;
			posY -= motionY / (double) f2 * 0.05000000074505806D;
			posZ -= motionZ / (double) f2 * 0.05000000074505806D;
			playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
			inGround = true;
			arrowShake = 7;
			// setIsCritical(false);

			if (iblockstate.getMaterial() != Material.AIR) {
				inTile.onEntityCollision(world, blockpos, iblockstate, this);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private void particles() {
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("xTile", xTile);
		compound.setInteger("yTile", yTile);
		compound.setInteger("zTile", zTile);
		compound.setShort("life", (short) ticksInGround);
		ResourceLocation resourcelocation = (ResourceLocation) Block.REGISTRY.getNameForObject(inTile);
		compound.setString("inTile", resourcelocation == null ? "" : resourcelocation.toString());
		compound.setByte("inData", (byte) inData);
		compound.setByte("shake", (byte) arrowShake);
		compound.setByte("inGround", (byte) (inGround ? 1 : 0));
		compound.setByte("pickup", (byte) pickupStatus.ordinal());
		compound.setDouble("damage", damage);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		xTile = compound.getInteger("xTile");
		yTile = compound.getInteger("yTile");
		zTile = compound.getInteger("zTile");
		ticksInGround = compound.getShort("life");

		if (compound.hasKey("inTile", 8)) {
			inTile = Block.getBlockFromName(compound.getString("inTile"));
		} else {
			inTile = Block.getBlockById(compound.getByte("inTile") & 255);
		}

		inData = compound.getByte("inData") & 255;
		arrowShake = compound.getByte("shake") & 255;
		inGround = compound.getByte("inGround") == 1;

		if (compound.hasKey("damage", 99)) {
			damage = compound.getDouble("damage");
		}

		if (compound.hasKey("pickup", 99)) {
			pickupStatus = EntityArrow.PickupStatus.getByOrdinal(compound.getByte("pickup"));
		} else if (compound.hasKey("player", 99)) {
			pickupStatus = compound.getBoolean("player") ? EntityArrow.PickupStatus.ALLOWED : EntityArrow.PickupStatus.DISALLOWED;
		}
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	@Override
	public void onCollideWithPlayer(EntityPlayer p_70100_1_) {

	}

	public void setDamageRangeSpeed(double d, float r, double s) {
		damage = d;
		range = r;
		speed = s;
	}

	@Override
	protected ItemStack getArrowStack() {
		return ItemStack.EMPTY;
	}
}
