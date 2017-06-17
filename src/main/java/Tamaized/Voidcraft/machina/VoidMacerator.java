package Tamaized.Voidcraft.machina;

import Tamaized.TamModized.blocks.TamBlockContainer;
import Tamaized.Voidcraft.GUI.GuiHandler;
import Tamaized.Voidcraft.VoidCraft;
import Tamaized.Voidcraft.machina.tileentity.TileEntityVoidMacerator;
import Tamaized.Voidcraft.registry.VoidCraftBlocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class VoidMacerator extends TamBlockContainer {

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	private Random rand = new Random();

	public VoidMacerator(CreativeTabs tab, Material material, String n, float hardness) {
		super(tab, material, n, hardness, SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, Boolean.valueOf(false)));
	}

	public static void setState(boolean active, World worldIn, BlockPos pos) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		TileEntityVoidMacerator te = (TileEntityVoidMacerator) tileentity;

		worldIn.setBlockState(pos, VoidCraftBlocks.voidMacerator.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)).withProperty(ACTIVE, active), 3);
		worldIn.setBlockState(pos, VoidCraftBlocks.voidMacerator.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)).withProperty(ACTIVE, active), 3);

		if (tileentity != null) {
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
	}

	public boolean getIsActive(IBlockState state) {
		return state.getValue(ACTIVE);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{FACING, ACTIVE});
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		int val = meta > 5 ? meta - 6 : meta;
		EnumFacing enumfacing = EnumFacing.getFront(val);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(ACTIVE, meta > 5);
	}

	@SideOnly(Side.CLIENT)
	public IBlockState getStateForEntityRender(IBlockState state) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH).withProperty(ACTIVE, false);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = ((EnumFacing) state.getValue(FACING)).getIndex();
		if (state.getValue(ACTIVE))
			meta = meta + 6;
		return meta;
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (!world.isRemote) {
			IBlockState iblockstate = world.getBlockState(pos.north());
			IBlockState iblockstate1 = world.getBlockState(pos.south());
			IBlockState iblockstate2 = world.getBlockState(pos.west());
			IBlockState iblockstate3 = world.getBlockState(pos.east());
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

			if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
				enumfacing = EnumFacing.SOUTH;
			} else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
				enumfacing = EnumFacing.NORTH;
			} else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
				enumfacing = EnumFacing.EAST;
			} else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
				enumfacing = EnumFacing.WEST;
			}
			world.setBlockState(pos, state.withProperty(FACING, enumfacing).withProperty(ACTIVE, state.getValue(ACTIVE)), 2);
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack heldItem = player.getHeldItem(hand);
		if (!world.isRemote) {
			FMLNetworkHandler.openGui(player, VoidCraft.instance, GuiHandler.getTypeID(GuiHandler.Type.Macerator), world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World arg0, int arg1) {
		return new TileEntityVoidMacerator();
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(ACTIVE, false), 2);
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(VoidCraftBlocks.voidMacerator);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityVoidMacerator) {
			((TileEntityVoidMacerator) tileentity).dropInventoryItems(worldIn, pos);
			worldIn.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(worldIn, pos, state);
	}
}