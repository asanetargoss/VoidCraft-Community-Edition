package Tamaized.Voidcraft.vadeMecum.contents.documentation.tools.voidhoe;

import Tamaized.Voidcraft.voidCraft;
import Tamaized.Voidcraft.capabilities.vadeMecum.IVadeMecumCapability;
import Tamaized.Voidcraft.vadeMecum.IVadeMecumPage;
import Tamaized.Voidcraft.vadeMecum.IVadeMecumPageProvider;
import Tamaized.Voidcraft.vadeMecum.VadeMecumCraftingNormal;
import Tamaized.Voidcraft.vadeMecum.VadeMecumPageCrafting;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class VadeMecumPageListVoidHoe implements IVadeMecumPageProvider {

	public IVadeMecumPage[] getPageList(IVadeMecumCapability cap) {
		return new IVadeMecumPage[] {
				new VadeMecumPageCrafting(new VadeMecumCraftingNormal("Void Hoe", new ItemStack[] {
						null,
						new ItemStack(voidCraft.items.voidcrystal),
						new ItemStack(voidCraft.items.voidcrystal),
						null,
						new ItemStack(Blocks.OBSIDIAN),
						null,
						null,
						new ItemStack(Items.DIAMOND),
						null }, new ItemStack(voidCraft.tools.voidHoe))) };
	}

}