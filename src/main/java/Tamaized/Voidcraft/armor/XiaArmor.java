package Tamaized.Voidcraft.armor;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import Tamaized.TamModized.armors.TamArmor;

public class XiaArmor extends TamArmor {

	public XiaArmor(CreativeTabs tab, ArmorMaterial armorMaterial, int par3, EntityEquipmentSlot par4, String type, String n) {
		super(tab, armorMaterial, par3, par4, type, n);
		setHasSubtypes(true);
	}

}
