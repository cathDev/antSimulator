package ch.epfl.moocprog;

import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.*;

import ch.epfl.moocprog.utils.Time;

public final class AntWorker extends Ant
{
	
	//quantité de nourriture transportée
	private double foodQuantity;
	
	public AntWorker( ToricPosition position, Uid anthill)
	{
		super(position, getConfig().getInt(ANT_WORKER_HP), getConfig().getTime(ANT_WORKER_LIFESPAN), anthill);
		foodQuantity = 0.0;
	}
	
	public void accept(AnimalVisitor visitor, RenderingMedia s)
	{
		visitor.visit(this, s);
	}
	
	public double getSpeed()
	{
		return getConfig().getDouble(ANT_WORKER_SPEED);
	}
	
	public double getFoodQuantity()
	{
		 return foodQuantity;
	}
	
	public String toString()
	{
		return super.toString()+"\nQuantity : "+getFoodQuantity();
	}
	
	protected void seekForFood(AntWorkerEnvironmentView env, Time dt)
	{
		
		if(this.getFoodQuantity() == 0)
		{
			Food food = env.getClosestFoodForAnt(this);
			if(food != null)
			{
				this.foodQuantity = food.takeQuantity(getConfig().getDouble(ANT_MAX_FOOD));
				this.setDirection(this.getDirection()+(Math.PI/2));
			}
			
		}
		else
		{
			if(env.dropFood(this) == true)
			{
				
				this.foodQuantity = 0;
				this.setDirection(this.getDirection()+(Math.PI/2));
			}
		}
		this.move(dt);
	}
	
	@Override
	protected void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt)
	{
		env.selectSpecificBehaviorDispatch(this, dt);
	}
}
