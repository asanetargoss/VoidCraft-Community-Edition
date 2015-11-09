package Tamaized.Voidcraft.world;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import Tamaized.Voidcraft.common.voidCraft;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGeneratorVoid implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {

	switch(world.provider.dimensionId){

	case 1 : generateEnd(world, random, chunkX*16, chunkZ*16);
	case -2 : generateVoid(world, random, chunkX*64, chunkZ*64);

	}

	}
	
	private void generateEnd(World world, Random random, int BlockX, int BlockZ) {
		for(int i = 0; i<6; i++) {
		int Xcoord = BlockX + random.nextInt(16);
		int Zcoord = BlockZ + random.nextInt(16);
		int Ycoord = random.nextInt(62);

		(new WorldGenMinable (voidCraft.oreVoidcrystal,5,Blocks.end_stone)).generate(world, random, Xcoord, Ycoord, Zcoord);
		}
		
		for(int i = 0; i<1; i++) {
			int Xcoord1 = BlockX + random.nextInt(16);
			  int Ycoord1 = random.nextInt(80);
			  int Zcoord1 = BlockZ + random.nextInt(16);
			   
			//  (new WorldGenTest()).generate(world, random, Xcoord1, Ycoord1, Zcoord1);
		}
		
		}
	
	private void generateVoid(World world, Random random, int BlockX, int BlockZ) {
		
		
		for(int i = 0; i<1; i++) {
		int Xcoord1 = BlockX + random.nextInt(64);
		  int Ycoord1 = random.nextInt(80);
		  int Zcoord1 = BlockZ + random.nextInt(64);
		   
		 // (new WorldGenTest()).generate(world, random, Xcoord1, Ycoord1, Zcoord1);
		  
		}
		}

}