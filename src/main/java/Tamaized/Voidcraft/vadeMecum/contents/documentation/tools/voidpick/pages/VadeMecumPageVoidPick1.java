package Tamaized.Voidcraft.vadeMecum.contents.documentation.tools.voidpick.pages;

import Tamaized.Voidcraft.voidCraft;
import Tamaized.Voidcraft.GUI.client.VadeMecumGUI;
import Tamaized.Voidcraft.vadeMecum.IVadeMecumCrafting;
import Tamaized.Voidcraft.vadeMecum.IVadeMecumPage;
import Tamaized.Voidcraft.vadeMecum.VadeMecumCraftingNormal;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class VadeMecumPageVoidPick1 implements IVadeMecumPage {

	private IVadeMecumCrafting crafting = new VadeMecumCraftingNormal("Void Pickaxe", new ItemStack[] {
			new ItemStack(voidCraft.items.voidcrystal),
			new ItemStack(voidCraft.items.voidcrystal),
			new ItemStack(voidCraft.items.voidcrystal),
			null,
			new ItemStack(Blocks.OBSIDIAN),
			null,
			null,
			new ItemStack(Items.DIAMOND),
			null }, new ItemStack(voidCraft.tools.voidPickaxe));

	@Override
	public void render(VadeMecumGUI gui, FontRenderer render, int x, int y, int mx, int my, int craftXoffset) {
		crafting.render(gui, render, x + craftXoffset, y, mx, my);
	}

}
