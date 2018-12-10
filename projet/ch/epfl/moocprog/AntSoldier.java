package ch.epfl.moocprog;

import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.*;

import ch.epfl.moocprog.utils.Time;

public class AntSoldier extends Ant
{
	public AntSoldier( ToricPosition position, Uid anthil)
	{
		super(position, getConfig().getInt(ANT_SOLDIER_HP), getConfig().getTime(ANT_SOLDIER_LIFESPAN), anthil);
	}
	
	public void accept(AnimalVisitor visitor, RenderingMedia s)
	{
		visitor.visit(this, s);
	}
	
	public double getSpeed()
	{
		return getConfig().getDouble(ANT_SOLDIER_SPEED);
	}
	
	protected void seekForEnemies(AntEnvironmentView env, Time dt)
	{
		this.move(dt);
	}
	
	@Override
	protected void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt)
	{
		env.selectSpecificBehaviorDispatch(this, dt);
	}
}
