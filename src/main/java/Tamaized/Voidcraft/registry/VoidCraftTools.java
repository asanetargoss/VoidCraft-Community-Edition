package Tamaized.Voidcraft.registry;

import Tamaized.TamModized.registry.ITamModel;
import Tamaized.TamModized.registry.ITamRegistry;
import Tamaized.TamModized.tools.*;
import Tamaized.Voidcraft.VoidCraft;
import Tamaized.Voidcraft.starforge.tools.StarForgeAxe;
import Tamaized.Voidcraft.starforge.tools.StarForgePickaxe;
import Tamaized.Voidcraft.starforge.tools.StarForgeShovel;
import Tamaized.Voidcraft.starforge.tools.StarForgeSword;
import Tamaized.Voidcraft.tools.VoidHoe;
import Tamaized.Voidcraft.tools.arch.ArchSword;
import Tamaized.Voidcraft.tools.chain.ChainSword;
import Tamaized.Voidcraft.tools.demon.DemonSword;
import Tamaized.Voidcraft.tools.molten.MoltenSword;
import Tamaized.Voidcraft.tools.spectre.AngelicSword;

import java.util.ArrayList;

public class VoidCraftTools implements ITamRegistry {

	public static ArrayList<ITamModel> modelList;

	public static TamPickaxe voidPickaxe;
	public static TamSpade voidSpade;
	public static TamAxe voidAxe;
	public static TamSword voidSword;
	public static TamHoe voidHoe;
	public static AngelicSword angelicSword;
	public static ChainSword chainSword;
	public static MoltenSword moltenSword;
	public static ArchSword archSword;
	public static DemonSword demonSword;
	public static TamPickaxe spectrePickaxe;
	public static TamAxe spectreAxe;
	public static TamSpade spectreSpade;
	public static TamHoe spectreHoe;

	public static StarForgeSword starforgedSword;
	public static StarForgePickaxe starforgedPickaxe;
	public static StarForgeAxe starforgedAxe;
	public static StarForgeShovel starforgedSpade;

	@Override
	public void preInit() {
		modelList = new ArrayList<ITamModel>();

		// Swords: 3+dmg; Axe: dmg; Pick: 1+dmg; Spade: 1.5+dmg;
		// Axe: -3; Pick: -2.8; Spade: -3;
		// Tools
		// void
		modelList.add(voidPickaxe = new TamPickaxe(VoidCraft.tabs.tabVoid, VoidCraft.materials.voidTools, "voidpickaxe"));
		modelList.add(voidSpade = new TamSpade(VoidCraft.tabs.tabVoid, VoidCraft.materials.voidTools, "voidshovel"));
		modelList.add(voidAxe = new TamAxe(VoidCraft.tabs.tabVoid, VoidCraft.materials.voidTools, "voidaxe"));
		modelList.add(voidSword = new TamSword(VoidCraft.tabs.tabVoid, VoidCraft.materials.voidTools, "voidsword"));
		modelList.add(voidHoe = new VoidHoe(VoidCraft.tabs.tabVoid, VoidCraft.materials.voidTools, "voidhoe"));

		// spectre
		modelList.add(angelicSword = new AngelicSword(VoidCraft.tabs.tabVoid, VoidCraft.materials.spectreTools, "angelicsword"));
		modelList.add(spectrePickaxe = new TamPickaxe(VoidCraft.tabs.tabVoid, VoidCraft.materials.spectreTools, "spectrepickaxe"));
		modelList.add(spectreAxe = new TamAxe(VoidCraft.tabs.tabVoid, VoidCraft.materials.spectreTools, "spectreaxe"));
		modelList.add(spectreSpade = new TamSpade(VoidCraft.tabs.tabVoid, VoidCraft.materials.spectreTools, "spectrespade"));
		modelList.add(spectreHoe = new VoidHoe(VoidCraft.tabs.tabVoid, VoidCraft.materials.spectreTools, "spectrehoe"));
		// chain
		modelList.add(chainSword = new ChainSword(VoidCraft.tabs.tabVoid, VoidCraft.materials.chainTools, "chainsword"));
		// molten
		modelList.add(moltenSword = new MoltenSword(VoidCraft.tabs.tabVoid, VoidCraft.materials.MoltenTools, "moltensword"));
		// arch
		modelList.add(archSword = new ArchSword(VoidCraft.tabs.tabVoid, VoidCraft.materials.ArchTools, "archsword"));
		// demon
		modelList.add(demonSword = new DemonSword(VoidCraft.tabs.tabVoid, VoidCraft.materials.DemonTools, "demonsword"));

		// Cosmic
		modelList.add(starforgedSword = new StarForgeSword(VoidCraft.tabs.tabVoid, VoidCraft.materials.CosmicTools, "starforgedsword"));
		modelList.add(starforgedPickaxe = new StarForgePickaxe(VoidCraft.tabs.tabVoid, VoidCraft.materials.CosmicTools, "starforgedpickaxe"));
		modelList.add(starforgedAxe = new StarForgeAxe(VoidCraft.tabs.tabVoid, VoidCraft.materials.CosmicTools, "starforgedaxe"));
		modelList.add(starforgedSpade = new StarForgeShovel(VoidCraft.tabs.tabVoid, VoidCraft.materials.CosmicTools, "starforgedshovel"));
	}

	@Override
	public void init() {
		/*VoidCraft.addShapedRecipe(new ItemStack(voidPickaxe, 1), 3, 3,

				VoidCraft.items.voidcrystal, VoidCraft.items.voidcrystal, VoidCraft.items.voidcrystal,

				ItemStack.EMPTY, Blocks.OBSIDIAN, ItemStack.EMPTY,

				ItemStack.EMPTY, Items.DIAMOND, ItemStack.EMPTY

		);
		VoidCraft.addShapedRecipe(new ItemStack(voidAxe, 1), 2, 3,

				VoidCraft.items.voidcrystal, VoidCraft.items.voidcrystal,

				Blocks.OBSIDIAN, VoidCraft.items.voidcrystal,

				Items.DIAMOND, ItemStack.EMPTY

		);
		VoidCraft.addShapedRecipe(new ItemStack(voidSpade, 1), 1, 3,

				VoidCraft.items.voidcrystal,

				Blocks.OBSIDIAN,

				Items.DIAMOND

		);
		VoidCraft.addShapedRecipe(new ItemStack(voidHoe, 1), 2, 3,

				VoidCraft.items.voidcrystal, VoidCraft.items.voidcrystal,

				Blocks.OBSIDIAN, ItemStack.EMPTY,

				Items.DIAMOND, ItemStack.EMPTY

		);
		VoidCraft.addShapedRecipe(new ItemStack(voidSword, 1), 3, 3,

				ItemStack.EMPTY, VoidCraft.items.voidcrystal, ItemStack.EMPTY,

				Blocks.OBSIDIAN, VoidCraft.items.voidcrystal, Blocks.OBSIDIAN,

				ItemStack.EMPTY, Items.DIAMOND, ItemStack.EMPTY

		);
		// +spectre
		VoidCraft.addShapedRecipe(new ItemStack(angelicSword, 1), 3, 3,

				ItemStack.EMPTY, VoidCraft.items.ectoplasm, ItemStack.EMPTY,

				VoidCraft.items.ectoplasm, new ItemStack(voidSword, 1, VoidCraft.WILDCARD_VALUE), VoidCraft.items.ectoplasm,

				ItemStack.EMPTY, VoidCraft.items.voidicSteel, ItemStack.EMPTY

		);
		VoidCraft.addShapedRecipe(new ItemStack(spectrePickaxe, 1), 3, 3,

				ItemStack.EMPTY, VoidCraft.items.ectoplasm, ItemStack.EMPTY,

				VoidCraft.items.ectoplasm, new ItemStack(voidPickaxe, 1, VoidCraft.WILDCARD_VALUE), VoidCraft.items.ectoplasm,

				ItemStack.EMPTY, VoidCraft.items.voidicSteel, ItemStack.EMPTY

		);
		VoidCraft.addShapedRecipe(new ItemStack(spectreAxe, 1), 3, 3,

				ItemStack.EMPTY, VoidCraft.items.ectoplasm, ItemStack.EMPTY,

				VoidCraft.items.ectoplasm, new ItemStack(voidAxe, 1, VoidCraft.WILDCARD_VALUE), VoidCraft.items.ectoplasm,

				ItemStack.EMPTY, VoidCraft.items.voidicSteel, ItemStack.EMPTY

		);
		VoidCraft.addShapedRecipe(new ItemStack(spectreSpade, 1), 3, 3,

				ItemStack.EMPTY, VoidCraft.items.ectoplasm, ItemStack.EMPTY,

				VoidCraft.items.ectoplasm, new ItemStack(voidSpade, 1, VoidCraft.WILDCARD_VALUE), VoidCraft.items.ectoplasm,

				ItemStack.EMPTY, VoidCraft.items.voidicSteel, ItemStack.EMPTY

		);
		VoidCraft.addShapedRecipe(new ItemStack(spectreHoe, 1), 3, 3,

				ItemStack.EMPTY, VoidCraft.items.ectoplasm, ItemStack.EMPTY,

				VoidCraft.items.ectoplasm, new ItemStack(voidHoe, 1, VoidCraft.WILDCARD_VALUE), VoidCraft.items.ectoplasm,

				ItemStack.EMPTY, VoidCraft.items.voidicSteel, ItemStack.EMPTY

		);
		// +molten and beyond
		VoidCraft.addShapedRecipe(new ItemStack(moltenSword), 3, 3,

				ItemStack.EMPTY, VoidCraft.items.MoltenvoidChain, ItemStack.EMPTY,

				VoidCraft.items.MoltenvoidChain, new ItemStack(chainSword, 1, VoidCraft.WILDCARD_VALUE), VoidCraft.items.MoltenvoidChain,

				ItemStack.EMPTY, VoidCraft.items.voidicSteel, ItemStack.EMPTY

		);
		VoidCraft.addShapedRecipe(new ItemStack(chainSword), 3, 3,

				ItemStack.EMPTY, VoidCraft.items.voidChain, ItemStack.EMPTY,

				VoidCraft.items.voidChain, new ItemStack(voidSword, 1, VoidCraft.WILDCARD_VALUE), VoidCraft.items.voidChain,

				ItemStack.EMPTY, VoidCraft.items.voidicSteel, ItemStack.EMPTY

		);
		VoidCraft.addShapedRecipe(new ItemStack(archSword), 3, 3,

				VoidCraft.items.MoltenvoidChain, VoidCraft.items.MoltenvoidChain, VoidCraft.items.MoltenvoidChain,

				new ItemStack(chainSword, 1, VoidCraft.WILDCARD_VALUE), new ItemStack(angelicSword, 1, VoidCraft.WILDCARD_VALUE), new ItemStack(moltenSword, 1, VoidCraft.WILDCARD_VALUE),

				VoidCraft.items.MoltenvoidChain, VoidCraft.items.MoltenvoidChain, VoidCraft.items.MoltenvoidChain

		);*/
	}

	@Override
	public void postInit() {

	}

	@Override
	public ArrayList<ITamModel> getModelList() {
		return modelList;
	}

	@Override
	public String getModID() {
		return VoidCraft.modid;
	}

	@Override
	public void clientPreInit() {

	}

	@Override
	public void clientInit() {

	}

	@Override
	public void clientPostInit() {

	}

}
