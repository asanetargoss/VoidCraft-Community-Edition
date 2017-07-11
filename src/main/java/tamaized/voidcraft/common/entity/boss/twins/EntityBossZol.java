package tamaized.voidcraft.common.entity.boss.twins;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import tamaized.voidcraft.common.entity.EntityVoidBoss;
import tamaized.voidcraft.common.sound.VoidSoundEvents;
import tamaized.voidcraft.common.xiacastle.logic.battle.twins.TwinsBattleHandler;

import java.util.ArrayList;
import java.util.List;

public class EntityBossZol extends EntityVoidBoss<TwinsBattleHandler> {

	public EntityBossZol(World par1World) {
		super(par1World);
		this.setInvulnerable(true);
	}

	public EntityBossZol(World world, TwinsBattleHandler handler) {
		super(world, handler, true);
		this.setInvulnerable(true);
	}

	@Override
	protected void triggerOnDamage(int phase, DamageSource source, float amount) {

	}
	
	@Override
	protected void deathHook() {
		
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		// start();
		return super.processInteract(player, hand);
	}

	@Override
	protected void initPhase(int phase) {

	}

	@Override
	protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {

	}

	@Override
	protected void updatePhase(int phase) {

	}

	@Override
	protected List<Class> getFilters() {
		List<Class> filter = new ArrayList<>();
		filter.add(EntityPlayer.class);
		return filter;
	}

	@Override
	protected boolean immuneToFire() {
		return true;
	}

	@Override
	protected float sizeWidth() {
		return 0.6F;
	}

	@Override
	protected float sizeHeight() {
		return 1.8F;
	}

	@Override
	protected int maxPhases() {
		return 3;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return VoidSoundEvents.EntityMobZolSoundEvents.ambientSound;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return VoidSoundEvents.EntityMobZolSoundEvents.hurtSound;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return VoidSoundEvents.EntityMobZolSoundEvents.deathSound;
	}

	@Override
	protected float getSoundVolume() {
		return 0.5F;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString("Zol");
	}

	@Override
	protected void encodePacketData(ByteBuf stream) {
		
	}

	@Override
	protected void decodePacketData(ByteBuf stream) {
		
	}

}