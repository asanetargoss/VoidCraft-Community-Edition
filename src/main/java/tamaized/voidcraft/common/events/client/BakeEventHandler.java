package tamaized.voidcraft.common.events.client;

import tamaized.voidcraft.VoidCraft;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BakeEventHandler {
	
	public static final BakeEventHandler instance = new BakeEventHandler();

    private BakeEventHandler() {};

    @SubscribeEvent
    public void onModelBakeEvent(ModelBakeEvent event){
    	VoidCraft.instance.logger.info("Baking TESR Blocks");
        event.getModelManager().getBlockModelShapes().registerBuiltInBlocks(VoidCraft.blocks.Heimdall);
        event.getModelManager().getBlockModelShapes().registerBuiltInBlocks(VoidCraft.blocks.blockNoBreak);
        event.getModelManager().getBlockModelShapes().registerBuiltInBlocks(VoidCraft.blocks.voidicCharger);
    }

}