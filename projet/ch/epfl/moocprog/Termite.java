package ch.epfl.moocprog;


import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.*;

import ch.epfl.moocprog.utils.Time;


public final class Termite extends Animal
{
	public Termite( ToricPosition position)
	{
		super(position, getConfig().getInt(TERMITE_HP), getConfig().getTime(TERMITE_LIFESPAN));
	}
	
	public void accept(AnimalVisitor visitor, RenderingMedia s)
	{
		visitor.visit(this, s);
	}
	
	public double getSpeed()
	{
		return getConfig().getDouble(TERMITE_SPEED);
	}
	
	@Override
	protected void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt)
	{
		
	}
}
