package Tamaized.Voidcraft.world.dim.Xia.castle.logic.battle.herobrine;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import Tamaized.Voidcraft.mobs.entity.boss.EntityMobHerobrine;
import Tamaized.Voidcraft.world.dim.Xia.castle.logic.battle.IBattleHandler;

public class HerobrineBattleHandler implements IBattleHandler {

	private int phase = 0;
	private int tick = 0;
	private int childPhase = 0;
	private int childPhaseModulate = 20;
	private boolean readyForInput = false;

	private boolean running;
	private boolean isDone = false;

	private World worldObj;
	private BlockPos pos;

	private EntityMobHerobrine herobrine;

	@Override
	public void update() {
		if (!worldObj.isRemote) {
			if (running) {
				switch (phase) {
					case 0:
						if (readyForInput) {
							herobrine = new EntityMobHerobrine(worldObj, this);
							herobrine.setPositionAndUpdate(pos.getX(), pos.getY()+1, pos.getZ());
							worldObj.spawnEntityInWorld(herobrine);
							herobrine.start();
							phase++;
							readyForInput = false;
						} else {
							readyForInput = true;
						}
						break;
					case 1:
						if(readyForInput){
							
						}else{
							readyForInput = !herobrine.isActive();
						}
						break;
					default:
						break;
				}
				if (!readyForInput) tick++;
			}
		}
	}

	@Override
	public void start(World world, BlockPos p) {
		worldObj = world;
		pos = p;
		stop();
		phase = 0;
		readyForInput = false;
		running = true;
	}

	@Override
	public void stop() {
		readyForInput = false;
		for (Entity e : worldObj.getEntitiesWithinAABB(EntityMobHerobrine.class, new AxisAlignedBB(pos.add(-50, -50, -50), pos.add(50, 50, 50))))
			worldObj.removeEntity(e);
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public boolean isDone() {
		return isDone;
	}

}