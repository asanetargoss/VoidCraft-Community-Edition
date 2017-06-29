package tamaized.voidcraft.common.vademecum.contents.documentation.blocks.voidbrick;

import tamaized.voidcraft.VoidCraft;
import tamaized.voidcraft.common.capabilities.vadeMecum.IVadeMecumCapability;
import tamaized.voidcraft.common.vademecum.IVadeMecumPage;
import tamaized.voidcraft.common.vademecum.IVadeMecumPageProvider;
import tamaized.voidcraft.common.vademecum.VadeMecumCraftingNormal;
import tamaized.voidcraft.common.vademecum.VadeMecumPage;
import tamaized.voidcraft.common.vademecum.VadeMecumPageCrafting;
import net.minecraft.item.ItemStack;

public class VadeMecumPageListVoidBrick implements IVadeMecumPageProvider {

	public IVadeMecumPage[] getPageList(IVadeMecumCapability cap) {
		return new IVadeMecumPage[] {
				new VadeMecumPage(new ItemStack(VoidCraft.blocks.blockVoidbrick).getDisplayName(), VoidCraft.modid+".VadeMecum.docs.desc.blockVoidbrick.pg1"),
				new VadeMecumPage("", VoidCraft.modid+".VadeMecum.docs.desc.blockVoidbrick.pg2"),
				new VadeMecumPageCrafting(new VadeMecumCraftingNormal(VoidCraft.modid+".VadeMecum.recipe.normal", new ItemStack(VoidCraft.blocks.blockVoidbrick, 1))),
				new VadeMecumPageCrafting(new VadeMecumCraftingNormal(new ItemStack(VoidCraft.blocks.blockVoidstairs).getDisplayName(), new ItemStack(VoidCraft.blocks.blockVoidstairs, 6))),
				new VadeMecumPageCrafting(new VadeMecumCraftingNormal(new ItemStack(VoidCraft.blocks.blockVoidfence).getDisplayName(), new ItemStack(VoidCraft.blocks.blockVoidfence, 6))),
				new VadeMecumPageCrafting(new VadeMecumCraftingNormal(new ItemStack(VoidCraft.blocks.blockVoidBrickHalfSlab).getDisplayName(), new ItemStack(VoidCraft.blocks.blockVoidBrickHalfSlab, 6)))
		};
	}

}