package Tamaized.Voidcraft.tools;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;

public class archSword extends ItemSword {

	public archSword(ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
	}
	
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase){
        par1ItemStack.damageItem(1, par3EntityLivingBase);
        par2EntityLivingBase.addPotionEffect(new PotionEffect(9, 300)); //Confuse
        par2EntityLivingBase.addPotionEffect(new PotionEffect(18, 300)); //Weakness
        par2EntityLivingBase.addPotionEffect(new PotionEffect(15, 300)); //Blind
        par2EntityLivingBase.addPotionEffect(new PotionEffect(19, 300)); //Poison
        par2EntityLivingBase.addPotionEffect(new PotionEffect(20, 300)); //Wither
        par2EntityLivingBase.setFire(100); //Fire
        return true;
    }

}