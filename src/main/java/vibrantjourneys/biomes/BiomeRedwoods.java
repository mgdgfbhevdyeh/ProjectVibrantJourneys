package vibrantjourneys.biomes;

import java.util.Random;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vibrantjourneys.worldgen.WorldGenRedwoodLarge;
import vibrantjourneys.worldgen.WorldGenRedwoodSmall;

public class BiomeRedwoods extends Biome
{    
	public BiomeRedwoods()
	{
		super(new BiomeProperties("Redwoods").setBaseHeight(0.2F)
				.setHeightVariation(0.3F)
				.setTemperature(0.23F)
				.setRainfall(0.7F));
		
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityWolf.class, 8, 4, 4));
		
        this.decorator.treesPerChunk = 9;
        this.decorator.grassPerChunk = 9;
        this.decorator.flowersPerChunk = 2;
        this.decorator.mushroomsPerChunk = 4;
	}
	
	@Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
	    WorldGenRedwoodSmall REDWOOD_LARGE = new WorldGenRedwoodSmall();
	    WorldGenRedwoodLarge REDWOOD_SMALL = new WorldGenRedwoodLarge(false, 30, 20);
	    
        return rand.nextInt(14) < 11 ? REDWOOD_LARGE : REDWOOD_SMALL;
    }
	
	@Override
    public WorldGenerator getRandomWorldGenForGrass(Random rand)
    {
        return rand.nextInt(5) > 0 ? new WorldGenTallGrass(BlockTallGrass.EnumType.FERN) : new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
    }
	
	@Override
    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
	    WorldGenBlockBlob FOREST_ROCK_GENERATOR = new WorldGenBlockBlob(Blocks.MOSSY_COBBLESTONE, 0);
		int i = rand.nextInt(2);

		for (int j = 0; j < i; ++j)
		{
			int k = rand.nextInt(16) + 8;
			int l = rand.nextInt(16) + 8;
			BlockPos blockpos = worldIn.getHeight(pos.add(k, 0, l));
			FOREST_ROCK_GENERATOR.generate(worldIn, rand, blockpos);
		}

        DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.FERN);

        if(TerrainGen.decorate(worldIn, rand, new ChunkPos(pos), EventType.FLOWERS))
        for (int i1 = 0; i1 < 7; ++i1)
        {
            int j1 = rand.nextInt(16) + 8;
            int k1 = rand.nextInt(16) + 8;
            int l1 = rand.nextInt(worldIn.getHeight(pos.add(j1, 0, k1)).getY() + 32);
            DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j1, l1, k1));
        }

        super.decorate(worldIn, rand, pos);
    }

	@Override
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimer, int x, int z, double noiseVal)
    {
    	this.topBlock = Blocks.GRASS.getDefaultState();
    	this.fillerBlock = Blocks.DIRT.getDefaultState();

    	if (noiseVal > 1.75D)
    	{
    		this.topBlock = Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
    	}
    	else if(noiseVal <= 1.75D && noiseVal > 1.35D)
    	{
        	this.topBlock = Blocks.STONE.getDefaultState();
        	this.fillerBlock = Blocks.STONE.getDefaultState();
    	}
    	else if (noiseVal > -0.90D)
    	{
    		this.topBlock = Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);
    	}

        this.generateBiomeTerrain(worldIn, rand, chunkPrimer, x, z, noiseVal);
    }
	
    @SideOnly(Side.CLIENT)
    @Override
    public int getGrassColorAtPos(BlockPos pos)
    {
    	return 0x379332;
    }
    @SideOnly(Side.CLIENT)
    @Override
    public int getFoliageColorAtPos(BlockPos pos)
    {
    	return 0x379C32;
    }
}
