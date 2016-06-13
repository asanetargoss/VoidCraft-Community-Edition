package Tamaized.Voidcraft.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import Tamaized.Voidcraft.common.voidCraft;
import Tamaized.Voidcraft.machina.VoidBox;
import Tamaized.Voidcraft.registry.IBasicVoid;
import Tamaized.Voidcraft.sound.VoidSoundEvents.MusicDiscSoundEvents.SoundTrack;

public class VoidRecord extends ItemRecord implements IBasicVoid{

	/** The name of the record. */
	public final String recordName;

	private int time = 0;
	
	private final String name;

	public VoidRecord(String displayName, SoundTrack track, String n) {
		super(displayName, track.getTrack());
		time = track.getLength();
		this.recordName = displayName;
		name = n;
		setUnlocalizedName(name);
		setMaxStackSize(1);
		setCreativeTab(voidCraft.tabs.tabVoid);
		GameRegistry.registerItem(this, "discs/"+n);
	}
	
	@Override
	public String getName() {
		return name;
	}

	public int getTime() {
		return time;
	}

	/**
	 * Callback for item usage. If the item does something special on right
	 * clicking, he will have one of those. Return True if something happen and
	 * false if it don't. This is for ITEMS, not BLOCKS
	 */
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return EnumActionResult.PASS;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_) {
		p_77624_3_.add(this.getRecordNameLocal());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getRecordNameLocal() {
		return recordName;
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Return an item rarity from EnumRarity
	 */
	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.RARE;
	}

	@Override
	public ResourceLocation getRecordResource(String name) {
		return new ResourceLocation(name);
	}

}
