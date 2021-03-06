package vibrantjourneys.worldgen;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PVJDecorateEventHandler
{
	@SubscribeEvent
	public void onDecorate(Decorate event)
	{
		Biome biome = event.getWorld().getBiomeForCoordsBody(event.getChunkPos().getBlock(0, 0, 0));
		if(BiomeDictionary.hasType(biome, Type.SWAMP))
		{
			if(event.getType() == Decorate.EventType.TREE)
			{
				if(event.getRand().nextInt(50) < 45)
					event.setResult(Result.DENY);
			}
		}
	}
}
