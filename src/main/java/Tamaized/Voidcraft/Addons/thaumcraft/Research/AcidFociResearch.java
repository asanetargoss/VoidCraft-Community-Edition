package Tamaized.Voidcraft.Addons.thaumcraft.Research;

import net.minecraft.util.ResourceLocation;
import Tamaized.Voidcraft.Addons.thaumcraft.VoidCraftThaumRecipes;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

public class AcidFociResearch extends ResearchItem {
	
	private final static AspectList aspects = new AspectList()
	.add(Aspect.TAINT, 1)
	.add(Aspect.VOID, 1)
	.add(Aspect.ENTROPY, 1)
	.add(Aspect.EARTH, 1);

	public AcidFociResearch(VoidCraftThaumRecipes recipes) {
		
		super("vc.FociAcid", "VoidCraft", aspects, 4, 2, 3, new ResourceLocation("VoidCraft:textures/items/Thaumcraft/Spells/Acid.png")); 
		
		this.setParents("vc.VoidCrystal");
		
		ResearchPage[] pages = {
				new ResearchPage(
						"Apparently a Void Crystal is the perfect catalyst for an acid based Wand Foci, other metals and gems simply just melt away.\n\n"
						+ "This Wand Foci is able to convert Terra Vis from your wand into clumps of Acid which shoot out towards your enemies. "),
				new ResearchPage(recipes.listShaped.get("vc.FociAcid"))
		};
		
		this.setPages(pages);
	}

}
