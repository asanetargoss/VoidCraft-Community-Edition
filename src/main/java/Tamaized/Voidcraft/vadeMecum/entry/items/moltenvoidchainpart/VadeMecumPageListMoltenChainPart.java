package Tamaized.Voidcraft.vadeMecum.entry.items.moltenvoidchainpart;

import Tamaized.Voidcraft.vadeMecum.IVadeMecumPage;
import Tamaized.Voidcraft.vadeMecum.entry.items.moltenvoidchainpart.pages.VadeMecumPageMoltenChainPart1;
import Tamaized.Voidcraft.vadeMecum.entry.items.moltenvoidchainpart.pages.VadeMecumPageMoltenChainPart2;

public class VadeMecumPageListMoltenChainPart {

	public static final IVadeMecumPage[] getPageList() {
		return new IVadeMecumPage[] {
				new VadeMecumPageMoltenChainPart1(),
				new VadeMecumPageMoltenChainPart2()};
	}

}