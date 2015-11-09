package Tamaized.Voidcraft.world.dim.TheVoid;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.NETHER_CAVE;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.FIRE;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.GLOWSTONE;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.NETHER_LAVA;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCavesHell;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenFire;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;
import net.minecraft.world.gen.feature.WorldGenGlowStone2;
import net.minecraft.world.gen.feature.WorldGenHellLava;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.structure.MapGenNetherBridge;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import Tamaized.Voidcraft.common.voidCraft;
import Tamaized.Voidcraft.structures.MapGenTestStart;

public class ChunkProviderVoid implements IChunkProvider
{
    private Random hellRNG;
    
    private boolean hasXiaGen = false;
    
    private BiomeGenBase[] biomesForGeneration;

    /** A NoiseGeneratorOctaves used in generating nether terrain */
    private NoiseGeneratorOctaves netherNoiseGen1;
    private NoiseGeneratorOctaves netherNoiseGen2;
    private NoiseGeneratorOctaves netherNoiseGen3;

    /** Determines whether slowsand or gravel can be generated at a location */
    private NoiseGeneratorOctaves slowsandGravelNoiseGen;

    /**
     * Determines whether something other than nettherack can be generated at a location
     */
    private NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
    public NoiseGeneratorOctaves netherNoiseGen6;
    public NoiseGeneratorOctaves netherNoiseGen7;

    /** Is the world that the nether is getting generated. */
    private World worldObj;
    private double[] noiseField;
    public MapGenNetherBridge genNetherBridge = new MapGenNetherBridge();
    public MapGenTestStart genTest= new MapGenTestStart();

    /**
     * Holds the noise used to determine whether slowsand can be generated at a location
     */
    private double[] slowsandNoise = new double[256];
    private double[] gravelNoise = new double[256];

    /**
     * Holds the noise used to determine whether something other than netherrack can be generated at a location
     */
    private double[] netherrackExclusivityNoise = new double[256];
    private MapGenBase netherCaveGenerator = new MapGenCavesHell();
    double[] noiseData1;
    double[] noiseData2;
    double[] noiseData3;
    double[] noiseData4;
    double[] noiseData5;

    {
        //genNetherBridge = (MapGenNetherBridge) TerrainGen.getModdedMapGen(genNetherBridge, NETHER_BRIDGE);
    	//genTest = TerrainGen.getModdedMapGen(genTest, TEST);
        netherCaveGenerator = TerrainGen.getModdedMapGen(netherCaveGenerator, NETHER_CAVE);
    }
    
    int[][] field_73203_h = new int[32][32];
    private static final String __OBFID = "CL_00000397";

    public ChunkProviderVoid(World par1World, long par2)
    {
    	//biomesForGeneration = voidCraft.biomeVoid;
        this.worldObj = par1World;
        this.hellRNG = new Random(par2);
        this.netherNoiseGen1 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.netherNoiseGen2 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.netherNoiseGen3 = new NoiseGeneratorOctaves(this.hellRNG, 8);
        this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.netherNoiseGen6 = new NoiseGeneratorOctaves(this.hellRNG, 10);
        this.netherNoiseGen7 = new NoiseGeneratorOctaves(this.hellRNG, 16);

        NoiseGeneratorOctaves[] noiseGens = {netherNoiseGen1, netherNoiseGen2, netherNoiseGen3, slowsandGravelNoiseGen, netherrackExculsivityNoiseGen, netherNoiseGen6, netherNoiseGen7};
        noiseGens = (NoiseGeneratorOctaves[]) TerrainGen.getModdedNoiseGenerators(par1World, this.hellRNG, noiseGens);
        this.netherNoiseGen1 = noiseGens[0];
        this.netherNoiseGen2 = noiseGens[1];
        this.netherNoiseGen3 = noiseGens[2];
        this.slowsandGravelNoiseGen = noiseGens[3];
        this.netherrackExculsivityNoiseGen = noiseGens[4];
        this.netherNoiseGen6 = noiseGens[5];
        this.netherNoiseGen7 = noiseGens[6];
        
        
    }

    

	/**
     * Generates the shape of the terrain in the nether.
     */
    public void generateTerrain(int p_147420_1_, int p_147420_2_, Block[] p_147420_3_)
    {
    	
    	
        byte b0 = 4;
        byte b1 = 32;
        int k = b0 + 1;
        byte b2 = 17;
        int l = b0 + 1;
        this.noiseField = this.initializeNoiseField(this.noiseField, p_147420_1_ * b0, 0, p_147420_2_ * b0, k, b2, l);

        for (int i1 = 0; i1 < b0; ++i1)
        {
            for (int j1 = 0; j1 < b0; ++j1)
            {
                for (int k1 = 0; k1 < 16; ++k1)
                {
                    double d0 = 0.125D;
                    double d1 = this.noiseField[((i1 + 0) * l + j1 + 0) * b2 + k1 + 0];
                    double d2 = this.noiseField[((i1 + 0) * l + j1 + 1) * b2 + k1 + 0];
                    double d3 = this.noiseField[((i1 + 1) * l + j1 + 0) * b2 + k1 + 0];
                    double d4 = this.noiseField[((i1 + 1) * l + j1 + 1) * b2 + k1 + 0];
                    double d5 = (this.noiseField[((i1 + 0) * l + j1 + 0) * b2 + k1 + 1] - d1) * d0;
                    double d6 = (this.noiseField[((i1 + 0) * l + j1 + 1) * b2 + k1 + 1] - d2) * d0;
                    double d7 = (this.noiseField[((i1 + 1) * l + j1 + 0) * b2 + k1 + 1] - d3) * d0;
                    double d8 = (this.noiseField[((i1 + 1) * l + j1 + 1) * b2 + k1 + 1] - d4) * d0;

                    for (int l1 = 0; l1 < 8; ++l1)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i2 = 0; i2 < 4; ++i2)
                        {
                            int j2 = i2 + i1 * 4 << 11 | 0 + j1 * 4 << 7 | k1 * 8 + l1;
                            short short1 = 128;
                            //i2 -= short1;
                            double d14 = 0.25D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;

                            for (int k2 = 0; k2 < 4; ++k2)
                            {
                                Block l2 = Blocks.air;

                                if (k1 * 8 + l1 < b1)
                                {
                                	double rand4Fluid = Math.round(Math.random()*600);
                                	
                                	if(rand4Fluid == 400){
                                    l2 = voidCraft.blockVoidFluid; //lavaStill
                                	//}else if(rand4Fluid > 200 && rand4Fluid < 400){
                                	//	l2 = voidCraft.blockFakeBedrock;
                                	}
                                	else{
                                		//double randy = Math.round(Math.random()*4);
                                		//if(randy == 1) l2 = Blocks.air;
                                		//else if(randy == 2) l2 = Blocks.end_stone;
                                		//else if(randy == 3) l2 = Blocks.skull;
                                		//else l2 = voidCraft.blockFakeBedrock; //TODO not implementing this change yet cuz I wanna think on it some more.
                                		l2 = voidCraft.blockFakeBedrock;
                                	}
                                }

                                if (d15 > 0.0D)
                                {
                                    l2 = voidCraft.blockFakeBedrock; //netherrack
                                }
                                
                                p_147420_3_[j2] = (Block)l2;
                                j2 += short1;
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    /**
     * name based on ChunkProviderGenerate
     */
    public void replaceBlocksForBiome(int p_147421_1_, int p_147421_2_, Block[] p_147421_3_, byte[] byteArray, BiomeGenBase[] biomesForGeneration2)//replaceBlocksForBiome(int par1, int par2, Block[] par3ArrayOfByte)
    {
    	
    	ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, p_147421_1_, p_147421_2_, p_147421_3_, byteArray, biomesForGeneration2, this.worldObj);
    	MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Result.DENY) return;

        byte b0 = 64;
        double d0 = 0.03125D;
        this.slowsandNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.slowsandNoise, p_147421_1_ * 16, p_147421_2_ * 16, 0, 16, 16, 1, d0, d0, 1.0D);
        this.gravelNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.gravelNoise, p_147421_1_ * 16, 109, p_147421_2_ * 16, 16, 1, 16, d0, 1.0D, d0);
        this.netherrackExclusivityNoise = this.netherrackExculsivityNoiseGen.generateNoiseOctaves(this.netherrackExclusivityNoise, p_147421_1_ * 16, p_147421_2_ * 16, 0, 16, 16, 1, d0 * 2.0D, d0 * 2.0D, d0 * 2.0D);

        for (int k = 0; k < 16; ++k)
        {
            for (int l = 0; l < 16; ++l)
            {
                boolean flag = this.slowsandNoise[k + l * 16] + this.hellRNG.nextDouble() * 0.2D > 0.0D;
                boolean flag1 = this.gravelNoise[k + l * 16] + this.hellRNG.nextDouble() * 0.2D > 0.0D;
                int i1 = (int)(this.netherrackExclusivityNoise[k + l * 16] / 3.0D + 3.0D + this.hellRNG.nextDouble() * 0.25D);
                int j1 = -1;
                Block b1 = voidCraft.blockFakeBedrock; //netherrack
                Block b2 = voidCraft.blockFakeBedrock; //netherrack

                for (int k1 = 127; k1 >= 0; --k1)
                {
                    int l1 = (l * 16 + k) * 128 + k1;

                    if (k1 < 127 - this.hellRNG.nextInt(5) && k1 > 0 + this.hellRNG.nextInt(5))
                    {
                        Block b3 = p_147421_3_[l1];

                        if (b3 == Blocks.air || b3.getMaterial() == Material.air)
                        {
                        	   j1 = -1;
                        }
                        else if (b3 == voidCraft.blockFakeBedrock) //netherrack
                        {
                            if (j1 == -1)
                            {
                                if (i1 <= 0)
                                {
                                    b1 = voidCraft.blockFakeBedrock;
                                    b2 = voidCraft.blockFakeBedrock; //netherrack
                                }
                                else if (k1 >= b0 - 4 && k1 <= b0 + 1)
                                {
                                    b1 = voidCraft.blockFakeBedrock; //netherrack
                                    b2 = voidCraft.blockFakeBedrock; //netherrack

                                    if (flag1)
                                    {
                                        b1 = voidCraft.blockFakeBedrock; //gravel
                                    }

                                    if (flag1)
                                    {
                                        b2 = voidCraft.blockFakeBedrock; //netherrack
                                    }

                                    if (flag)
                                    {
                                        b1 = voidCraft.blockFakeBedrock; //slowSand
                                    }

                                    if (flag)
                                    {
                                        b2 = voidCraft.blockFakeBedrock; // slowSand
                                    }
                                }

                                if (k1 < b0 && b1 == Blocks.air)
                                {
                                    b1 = voidCraft.blockFakeBedrock; //lavaStill
                                }

                                j1 = i1;

                                if (k1 >= b0 - 1)
                                {
                                	p_147421_3_[l1] = b1;
                                }
                                else
                                {
                                	p_147421_3_[l1] = b2;
                                }
                            }
                            else if (j1 > 0)
                            {
                                --j1;
                                p_147421_3_[l1] = b2;
                            }
                        }
                    }
                    else
                    {
                    	p_147421_3_[l1] = voidCraft.blockFakeBedrock; //bedrock
                    }
                }
            }
        }
    }

    /**
     * loads or generates the chunk at the chunk location specified
     */
    
    public Chunk loadChunk(int par1, int par2)
    {
        return this.provideChunk(par1, par2);
    }

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    
    public Chunk provideChunk(int par1, int par2)
    {
    	
        this.hellRNG.setSeed((long)par1 * 341873128712L + (long)par2 * 132897987541L);
        Block[] abyte = new Block[32768];
        byte[] byteArray = new byte[32768];
        //abyte[0] = voidCraft.blockFakeBedrock;
        this.generateTerrain(par1, par2, abyte);
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, par1*16, par2*16, 16, 16);
        this.replaceBlocksForBiome(par1, par2, abyte, byteArray, this.biomesForGeneration);
       // this.netherCaveGenerator.generate(this, this.worldObj, par1, par2, abyte);
       // this.genNetherBridge.generate(this, this.worldObj, par1, par2, abyte);
        this.genTest.func_151539_a(this, this.worldObj, par1, par2, abyte);
        Chunk chunk = new Chunk(this.worldObj, abyte, par1, par2);
        BiomeGenBase[] abiomegenbase = this.worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[])null, par1 * 16, par2 * 16, 16, 16);
        byte[] abyte1 = chunk.getBiomeArray();

        for (int k = 0; k < abyte1.length; k++)
        {
            abyte1[k] = (byte)abiomegenbase[k].biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    /**
     * generates a subset of the level's terrain data. Takes 7 arguments: the [empty] noise array, the position, and the
     * size.
     */
    
    private double[] initializeNoiseField(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7)
    {
    	
        ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, par1ArrayOfDouble, par2, par3, par4, par5, par6, par7);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Result.DENY) return event.noisefield;
        if (par1ArrayOfDouble == null)
        {
            par1ArrayOfDouble = new double[par5 * par6 * par7];
        }

        double d0 = 684.412D;
        double d1 = 2053.236D;
        this.noiseData4 = this.netherNoiseGen6.generateNoiseOctaves(this.noiseData4, par2, par3, par4, par5, 1, par7, 1.0D, 0.0D, 1.0D);
        this.noiseData5 = this.netherNoiseGen7.generateNoiseOctaves(this.noiseData5, par2, par3, par4, par5, 1, par7, 100.0D, 0.0D, 100.0D);
        this.noiseData1 = this.netherNoiseGen3.generateNoiseOctaves(this.noiseData1, par2, par3, par4, par5, par6, par7, d0 / 80.0D, d1 / 60.0D, d0 / 80.0D);
        this.noiseData2 = this.netherNoiseGen1.generateNoiseOctaves(this.noiseData2, par2, par3, par4, par5, par6, par7, d0, d1, d0);
        this.noiseData3 = this.netherNoiseGen2.generateNoiseOctaves(this.noiseData3, par2, par3, par4, par5, par6, par7, d0, d1, d0);
        int k1 = 0;
        int l1 = 0;
        double[] adouble1 = new double[par6];
        int i2;

        for (i2 = 0; i2 < par6; ++i2)
        {
            adouble1[i2] = Math.cos((double)i2 * Math.PI * 6.0D / (double)par6) * 2.0D;
            double d2 = (double)i2;

            if (i2 > par6 / 2)
            {
                d2 = (double)(par6 - 1 - i2);
            }

            if (d2 < 4.0D)
            {
                d2 = 4.0D - d2;
                adouble1[i2] -= d2 * d2 * d2 * 10.0D;
            }
        }

        for (i2 = 0; i2 < par5; ++i2)
        {
            for (int j2 = 0; j2 < par7; ++j2)
            {
                double d3 = (this.noiseData4[l1] + 256.0D) / 512.0D;

                if (d3 > 1.0D)
                {
                    d3 = 1.0D;
                }

                double d4 = 0.0D;
                double d5 = this.noiseData5[l1] / 8000.0D;

                if (d5 < 0.0D)
                {
                    d5 = -d5;
                }

                d5 = d5 * 3.0D - 3.0D;

                if (d5 < 0.0D)
                {
                    d5 /= 2.0D;

                    if (d5 < -1.0D)
                    {
                        d5 = -1.0D;
                    }

                    d5 /= 1.4D;
                    d5 /= 2.0D;
                    d3 = 0.0D;
                }
                else
                {
                    if (d5 > 1.0D)
                    {
                        d5 = 1.0D;
                    }

                    d5 /= 6.0D;
                }

                d3 += 0.5D;
                d5 = d5 * (double)par6 / 16.0D;
                ++l1;

                for (int k2 = 0; k2 < par6; ++k2)
                {
                    double d6 = 0.0D;
                    double d7 = adouble1[k2];
                    double d8 = this.noiseData2[k1] / 512.0D;
                    double d9 = this.noiseData3[k1] / 512.0D;
                    double d10 = (this.noiseData1[k1] / 10.0D + 1.0D) / 2.0D;

                    if (d10 < 0.0D)
                    {
                        d6 = d8;
                    }
                    else if (d10 > 1.0D)
                    {
                        d6 = d9;
                    }
                    else
                    {
                        d6 = d8 + (d9 - d8) * d10;
                    }

                    d6 -= d7;
                    double d11;

                    if (k2 > par6 - 4)
                    {
                        d11 = (double)((float)(k2 - (par6 - 4)) / 3.0F);
                        d6 = d6 * (1.0D - d11) + -10.0D * d11;
                    }

                    if ((double)k2 < d4)
                    {
                        d11 = (d4 - (double)k2) / 4.0D;

                        if (d11 < 0.0D)
                        {
                            d11 = 0.0D;
                        }

                        if (d11 > 1.0D)
                        {
                            d11 = 1.0D;
                        }

                        d6 = d6 * (1.0D - d11) + -10.0D * d11;
                    }

                    par1ArrayOfDouble[k1] = d6;
                    ++k1;
                }
            }
        }

        return par1ArrayOfDouble;
    }

    /**
     * Checks to see if a chunk exists at x, y
     */
    
    public boolean chunkExists(int par1, int par2)
    {
        return true;
    }

    /**
     * Populates chunk with ores etc etc
     */
    
    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
    {
    	
        BlockSand.fallInstantly = true;

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(par1IChunkProvider, worldObj, hellRNG, par2, par3, false));

        int k = par2 * 16;
        int l = par3 * 16;
      //  this.genNetherBridge.generateStructuresInChunk(this.worldObj, this.hellRNG, par2, par3);
        this.genTest.generateStructuresInChunk(this.worldObj, this.hellRNG, par2, par3);
        int i1;
        int j1;
        int k1;
        int l1;

        boolean doGen = TerrainGen.populate(par1IChunkProvider, worldObj, hellRNG, par2, par3, false, NETHER_LAVA);
        for (i1 = 0; doGen && i1 < 8; ++i1)
        {
            j1 = k + this.hellRNG.nextInt(16) + 8;
            k1 = this.hellRNG.nextInt(120) + 4;
            l1 = l + this.hellRNG.nextInt(16) + 8;
            (new WorldGenHellLava(Blocks.lava, false)).generate(this.worldObj, this.hellRNG, j1, k1, l1);
        }

        i1 = this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1) + 1;
        int i2;

        doGen = TerrainGen.populate(par1IChunkProvider, worldObj, hellRNG, par2, par3, false, FIRE);
        for (j1 = 0; doGen && j1 < i1; ++j1)
        {
            k1 = k + this.hellRNG.nextInt(16) + 8;
            l1 = this.hellRNG.nextInt(120) + 4;
            i2 = l + this.hellRNG.nextInt(16) + 8;
            (new WorldGenFire()).generate(this.worldObj, this.hellRNG, k1, l1, i2);
        }

        i1 = this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1);

        doGen = TerrainGen.populate(par1IChunkProvider, worldObj, hellRNG, par2, par3, false, GLOWSTONE);
        for (j1 = 0; doGen && j1 < i1; ++j1)
        {
            k1 = k + this.hellRNG.nextInt(16) + 8;
            l1 = this.hellRNG.nextInt(120) + 4;
            i2 = l + this.hellRNG.nextInt(16) + 8;
            (new WorldGenGlowStone1()).generate(this.worldObj, this.hellRNG, k1, l1, i2);
        }

        for (j1 = 0; doGen && j1 < 10; ++j1)
        {
            k1 = k + this.hellRNG.nextInt(16) + 8;
            l1 = this.hellRNG.nextInt(128);
            i2 = l + this.hellRNG.nextInt(16) + 8;
            (new WorldGenGlowStone2()).generate(this.worldObj, this.hellRNG, k1, l1, i2);
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(worldObj, hellRNG, k, l));
        
       // doGen = TerrainGen.decorate(worldObj, hellRNG, k, l, SHROOM);
        if (doGen && this.hellRNG.nextInt(1) == 0)
        {
            j1 = k + this.hellRNG.nextInt(16) + 8;
            k1 = this.hellRNG.nextInt(128);
            l1 = l + this.hellRNG.nextInt(16) + 8;
      //      (new WorldGenFlowers(Block.mushroomBrown.blockID)).generate(this.worldObj, this.hellRNG, j1, k1, l1);
        }

        if (doGen && this.hellRNG.nextInt(1) == 0)
        {
            j1 = k + this.hellRNG.nextInt(16) + 8;
            k1 = this.hellRNG.nextInt(128);
            l1 = l + this.hellRNG.nextInt(16) + 8;
      //      (new WorldGenFlowers(Block.mushroomRed.blockID)).generate(this.worldObj, this.hellRNG, j1, k1, l1);
        }

        WorldGenMinable worldgenminable = new WorldGenMinable(voidCraft.oreVoidcrystal, 5, voidCraft.blockFakeBedrock);
        int j2;

        for (k1 = 0; k1 < 16; ++k1)
        {
            l1 = k + this.hellRNG.nextInt(16);
            i2 = this.hellRNG.nextInt(108) + 10;
            j2 = l + this.hellRNG.nextInt(16);
            worldgenminable.generate(this.worldObj, this.hellRNG, l1, i2, j2);
        }

        for (k1 = 0; k1 < 16; ++k1)
        {
            l1 = k + this.hellRNG.nextInt(16);
            i2 = this.hellRNG.nextInt(108) + 10;
            j2 = l + this.hellRNG.nextInt(16);
            (new WorldGenHellLava(Blocks.lava, true)).generate(this.worldObj, this.hellRNG, l1, i2, j2);
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(worldObj, hellRNG, k, l));
        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(par1IChunkProvider, worldObj, hellRNG, par2, par3, false));

        BlockSand.fallInstantly = false;
    }

    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
    {
        return true;
    }

    /**
     * Save extra data not associated with any Chunk.  Not saved during autosave, only during world unload.  Currently
     * unimplemented.
     */
    public void saveExtraData() {}

    /**
     * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every such chunk.
     */
    public boolean unloadQueuedChunks()
    {
        return false;
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    public boolean canSave()
    {
        return true;
    }

    /**
     * Converts the instance data to a readable string.
     */
    public String makeString()
    {
        return "TheVoid";
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
    {
        if (par1EnumCreatureType == EnumCreatureType.monster)
        {
           /* if (this.genNetherBridge.hasStructureAt(par2, par3, par4))
            {
                return this.genNetherBridge.getSpawnList();
            }

            if (this.genNetherBridge.func_142038_b(par2, par3, par4) && this.worldObj.getBlockId(par2, par3 - 1, par4) == Block.netherBrick.blockID)
            {
                return this.genNetherBridge.getSpawnList();
            }*/
        	
        	if(this.genTest != null){
        	if (this.genTest.hasStructureAt(par2, par3, par4))
            {
                return this.genTest.getSpawnList();
            }
        	}

            if (this.genTest.func_142038_b(par2, par3, par4) && this.worldObj.getBlock(par2, par3 - 1, par4) == voidCraft.blockVoidbrick)
            {
                return this.genTest.getSpawnList();
            }
            
        }

        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(par2, par4);
        return biomegenbase == null ? null : biomegenbase.getSpawnableList(par1EnumCreatureType);
    }

    /**
     * Returns the location of the closest structure of the specified type. If not found returns null.
     */
    public ChunkPosition findClosestStructure(World par1World, String par2Str, int par3, int par4, int par5)
    {
        return null;
    }

    public int getLoadedChunkCount()
    {
        return 0;
    }

    public void recreateStructures(int par1, int par2)
    {
        //this.genNetherBridge.generate(this, this.worldObj, par1, par2, (byte[])null);
       this.genTest.func_151539_a(this, this.worldObj, par1, par2, (Block[])null);
    }



	@Override
	public ChunkPosition func_147416_a(World arg0, String arg1, int arg2,
			int arg3, int arg4) {
		return null;
	}
}