package vibrantjourneys.entities.passive;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityFly extends EntityAmbientCreature
{
    private BlockPos spawnPosition;
    
    public EntityFly(World worldIn)
    {
        super(worldIn);
        this.setSize(0.1F, 0.1F);
    }
    
	@Override
    public boolean canBePushed()
    {
        return false;
    }
    
    //Override parent for no collision/no fall damage
    //----------------------------------------
    @Override
    protected void collideWithEntity(Entity entityIn){}

    @Override
    protected void collideWithNearbyEntities(){}
    
    @Override
    public void fall(float distance, float damageMultiplier){}
    //----------------------------------------
    
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0D);
    }
    
	@Override
    public void onUpdate()
    {
        super.onUpdate();
        this.motionY *= 0.6000000238418579D;

        if(!this.world.isRemote)
        {
	        if(this.world.isDaytime() && this instanceof EntityFirefly)
	        {
	        	if(this.getRNG().nextInt(500) == 0)
	        	{
	        		this.world.removeEntity(this);
	        	}
	        }
        }

    }
	
	@Override
    protected int getExperiencePoints(EntityPlayer player)
    {
        return 0;
    }
    
    //Taken from EntityBat
	@Override
    protected void updateAITasks()
    {
        super.updateAITasks();

		if (this.spawnPosition != null && (!this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1))
		{
			this.spawnPosition = null;
		}

		if (this.spawnPosition == null || this.rand.nextInt(30) == 0
				|| this.spawnPosition.distanceSq((double) ((int) this.posX), (double) ((int) this.posY),
						(double) ((int) this.posZ)) < 4.0D)
		{
			this.spawnPosition = new BlockPos((int) this.posX + this.rand.nextInt(7) - this.rand.nextInt(7),
					(int) this.posY + this.rand.nextInt(6) - 2,
					(int) this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
		}
		
		if(!this.isInWater())
		{
			double d0 = (double) this.spawnPosition.getX() + 0.5D - this.posX;
			double d1 = (double) this.spawnPosition.getY() + 0.1D - this.posY;
			double d2 = (double) this.spawnPosition.getZ() + 0.5D - this.posZ;
			this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.10000000149011612D;
			this.motionY += (Math.signum(d1) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
			this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.10000000149011612D;
			float f = (float) (MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
			float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
			this.moveForward = 0.5F;
			this.rotationYaw += f1;
		}
		else
		{
        	this.motionX = (this.rand.nextDouble() - this.rand.nextDouble()) / 100.0;
        	this.motionY = 0;
        	this.motionZ = (this.rand.nextDouble() - this.rand.nextDouble()) / 100.0;
        	
        	if(this.getRNG().nextInt(2000) == 0)
        	{
        		world.removeEntity(this);
        	}
		}
    }
    
	@Override
    protected boolean canTriggerWalking()
    {
        return false;
    }
    
	@Override
    public boolean doesEntityNotTriggerPressurePlate()
    {
        return true;
    }
	
    //Forces client to render the firefly over large distances
    @SideOnly(Side.CLIENT)
    @Override
    public boolean isInRangeToRenderDist(double distance)
    {
    	return true;
    }
}
