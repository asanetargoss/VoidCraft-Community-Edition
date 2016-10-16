package Tamaized.Voidcraft.vadeMecum.contents.documentation.items.moltenvoidchain.pages;

import Tamaized.Voidcraft.voidCraft;
import Tamaized.Voidcraft.GUI.client.VadeMecumGUI;
import Tamaized.Voidcraft.vadeMecum.IVadeMecumCrafting;
import Tamaized.Voidcraft.vadeMecum.IVadeMecumPage;
import Tamaized.Voidcraft.vadeMecum.VadeMecumCraftingFurnace;
import Tamaized.Voidcraft.vadeMecum.VadeMecumCraftingNormal;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

public class VadeMecumPageMoltenChain2 implements IVadeMecumPage {

	private IVadeMecumCrafting crafting = new VadeMecumCraftingNormal("Recipe", new ItemStack[] {
			new ItemStack(voidCraft.items.burnBone),
			new ItemStack(voidCraft.items.MoltenvoidChainPart),
			new ItemStack(voidCraft.items.burnBone),
			new ItemStack(voidCraft.items.MoltenvoidChainPart),
			new ItemStack(voidCraft.items.burnBone),
			new ItemStack(voidCraft.items.MoltenvoidChainPart),
			new ItemStack(voidCraft.items.burnBone),
			new ItemStack(voidCraft.items.MoltenvoidChainPart),
			new ItemStack(voidCraft.items.burnBone) }, new ItemStack(voidCraft.items.MoltenvoidChain));

	@Override
	public void render(VadeMecumGUI gui, FontRenderer render, int x, int y, int mx, int my, int offset) {
		crafting.render(gui, render, x + offset, y, mx, my);
	}

}
