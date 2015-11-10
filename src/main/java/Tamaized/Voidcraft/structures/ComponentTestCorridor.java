package Tamaized.Voidcraft.structures;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import Tamaized.Voidcraft.common.voidCraft;

public class ComponentTestCorridor extends ComponentTestPiece{
	
	private boolean field_111021_b;

    public ComponentTestCorridor() {}

    public ComponentTestCorridor(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        this.coordBaseMode = par4;
        this.boundingBox = par3StructureBoundingBox;
        this.field_111021_b = par2Random.nextInt(3) == 0;
    }

    protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
    {
        super.func_143011_b(par1NBTTagCompound);
        this.field_111021_b = par1NBTTagCompound.getBoolean("Chest");
    }

    protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
    {
        super.func_143012_a(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Chest", this.field_111021_b);
    }

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
    {
        this.getNextComponentX((ComponentTestStartPiece)par1StructureComponent, par2List, par3Random, 0, 1, true);
    }

    /**
     * Creates and returns a new component piece. Or null if it could not find enough room to place it.
     */
    public static ComponentTestCorridor createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, 0, 0, 5, 7, 5, par5);
        return isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(par0List, structureboundingbox) == null ? new ComponentTestCorridor(par6, par1Random, structureboundingbox, par5) : null;
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 1, 4, voidCraft.blockVoidbrick, voidCraft.blockVoidbrick, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.air, Blocks.air, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 4, 2, 0, 4, 5, 4, voidCraft.blockVoidbrick, voidCraft.blockVoidbrick, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 4, 3, 1, 4, 4, 1, voidCraft.blockVoidfence, voidCraft.blockVoidfence, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 4, 3, 3, 4, 4, 3, voidCraft.blockVoidfence, voidCraft.blockVoidfence, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 5, 0, voidCraft.blockVoidbrick, voidCraft.blockVoidbrick, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 4, 3, 5, 4, voidCraft.blockVoidbrick, voidCraft.blockVoidbrick, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 3, 4, 1, 4, 4, voidCraft.blockVoidfence, voidCraft.blockVoidbrick, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 3, 3, 4, 3, 4, 4, voidCraft.blockVoidfence, voidCraft.blockVoidbrick, false);
        int i;
        int j;

        if (this.field_111021_b)
        {
            i = this.getYWithOffset(2);
            j = this.getXWithOffset(3, 3);
            int k = this.getZWithOffset(3, 3);

            if (par3StructureBoundingBox.isVecInside(j, i, k))
            {
                this.field_111021_b = false;
                this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 3, 2, 3, field_111019_a, 2 + par2Random.nextInt(4));
            }
        }

        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 6, 0, 4, 6, 4, voidCraft.blockVoidbrick, voidCraft.blockVoidbrick, false);

        for (i = 0; i <= 4; ++i)
        {
            for (j = 0; j <= 4; ++j)
            {
                //this.fillCurrentPositionBlocksDownwards(par1World, voidCraft.blockVoidbrick, 0, i, -1, j, par3StructureBoundingBox);
            }
        }

        return true;
    }

}
