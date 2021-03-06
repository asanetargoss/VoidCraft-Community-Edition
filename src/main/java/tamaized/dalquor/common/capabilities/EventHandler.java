package tamaized.dalquor.common.capabilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tamaized.dalquor.common.capabilities.elytraFlying.ElytraFlyingCapabilityHandler;
import tamaized.dalquor.common.capabilities.elytraFlying.IElytraFlyingCapability;
import tamaized.dalquor.common.capabilities.vadeMecum.IVadeMecumCapability;
import tamaized.dalquor.common.capabilities.vadeMecum.VadeMecumCapabilityHandler;
import tamaized.dalquor.common.capabilities.voidicInfusion.IVoidicInfusionCapability;
import tamaized.dalquor.common.capabilities.voidicInfusion.VoidicInfusionCapabilityHandler;

public class EventHandler {

	@SubscribeEvent
	public void attachCapabilityEntity(AttachCapabilitiesEvent<Entity> e) { // TODO: move capability stuff into TamModized
		if (e.getObject() instanceof EntityLivingBase) {
			e.addCapability(VoidicInfusionCapabilityHandler.ID, new ICapabilitySerializable<NBTTagCompound>() {

				IVoidicInfusionCapability inst = CapabilityList.VOIDICINFUSION.getDefaultInstance();

				@Override
				public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
					return capability == CapabilityList.VOIDICINFUSION;
				}

				@Override
				public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
					return capability == CapabilityList.VOIDICINFUSION ? CapabilityList.VOIDICINFUSION.<T>cast(inst) : null;
				}

				@Override
				public NBTTagCompound serializeNBT() {
					return (NBTTagCompound) CapabilityList.VOIDICINFUSION.getStorage().writeNBT(CapabilityList.VOIDICINFUSION, inst, null);
				}

				@Override
				public void deserializeNBT(NBTTagCompound nbt) {
					CapabilityList.VOIDICINFUSION.getStorage().readNBT(CapabilityList.VOIDICINFUSION, inst, null, nbt);
				}

			});
		}
		if (e.getObject() instanceof EntityPlayer) {
			e.addCapability(VadeMecumCapabilityHandler.ID, new ICapabilitySerializable<NBTTagCompound>() {

				IVadeMecumCapability inst = CapabilityList.VADEMECUM.getDefaultInstance();

				@Override
				public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
					return capability == CapabilityList.VADEMECUM;
				}

				@Override
				public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
					return capability == CapabilityList.VADEMECUM ? CapabilityList.VADEMECUM.<T>cast(inst) : null;
				}

				@Override
				public NBTTagCompound serializeNBT() {
					return (NBTTagCompound) CapabilityList.VADEMECUM.getStorage().writeNBT(CapabilityList.VADEMECUM, inst, null);
				}

				@Override
				public void deserializeNBT(NBTTagCompound nbt) {
					CapabilityList.VADEMECUM.getStorage().readNBT(CapabilityList.VADEMECUM, inst, null, nbt);
				}

			});
			e.addCapability(ElytraFlyingCapabilityHandler.ID, new ICapabilitySerializable<NBTTagCompound>() {

				IElytraFlyingCapability inst = CapabilityList.ELYTRAFLYING.getDefaultInstance();

				@Override
				public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
					return capability == CapabilityList.ELYTRAFLYING;
				}

				@Override
				public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
					return capability == CapabilityList.ELYTRAFLYING ? CapabilityList.ELYTRAFLYING.<T>cast(inst) : null;
				}

				@Override
				public NBTTagCompound serializeNBT() {
					return (NBTTagCompound) CapabilityList.ELYTRAFLYING.getStorage().writeNBT(CapabilityList.ELYTRAFLYING, inst, null);
				}

				@Override
				public void deserializeNBT(NBTTagCompound nbt) {
					CapabilityList.ELYTRAFLYING.getStorage().readNBT(CapabilityList.ELYTRAFLYING, inst, null, nbt);
				}

			});
		}
	}

	@SubscribeEvent
	public void updateClone(PlayerEvent.Clone e) {
		EntityPlayer oldPlayer = e.getOriginal();
		EntityPlayer newPlayer = e.getEntityPlayer();
		newPlayer.getCapability(CapabilityList.VOIDICINFUSION, null).copyFrom(oldPlayer.getCapability(CapabilityList.VOIDICINFUSION, null));
		newPlayer.getCapability(CapabilityList.VADEMECUM, null).copyFrom(oldPlayer.getCapability(CapabilityList.VADEMECUM, null));
		newPlayer.getCapability(CapabilityList.ELYTRAFLYING, null).copyFrom(oldPlayer.getCapability(CapabilityList.ELYTRAFLYING, null));
	}

}
