package tamaized.dalquor.common.capabilities.voidicPower;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import tamaized.dalquor.DalQuor;

public class VoidicPowerCapabilityStorage implements IStorage<IVoidicPowerCapability> {

	public VoidicPowerCapabilityStorage() {
		DalQuor.instance.logger.info("VoidicPowerCapabilityStorage Registered");
	}

	@Override
	public NBTBase writeNBT(Capability<IVoidicPowerCapability> capability, IVoidicPowerCapability instance, EnumFacing side) {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setInteger("currPower", instance.getCurrentPower());
		compound.setInteger("maxPower", instance.getMaxPower());
		compound.setBoolean("isDefault", instance.isDefault());
		return compound;
	}

	@Override
	public void readNBT(Capability<IVoidicPowerCapability> capability, IVoidicPowerCapability instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound compound = (NBTTagCompound) nbt;
		instance.setValues(compound.getInteger("currPower"), compound.getInteger("maxPower"));
		instance.setDefault(compound.getBoolean("isDefault"));
		instance.setLoaded();
	}

}
