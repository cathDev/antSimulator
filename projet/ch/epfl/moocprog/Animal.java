package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Utils;
import ch.epfl.moocprog.utils.Vec2d;

import ch.epfl.moocprog.random.UniformDistribution;

import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.*;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;

public abstract class Animal extends Positionable 
{
	private double angleDirection;
	private int hitpoints;
	private Time lifespan;
	private Time rotationDelay;
	
	public Animal(ToricPosition pos, int point, Time dureeVie)
	{
		super(pos);
		hitpoints = point;
		lifespan = dureeVie;
		angleDirection = UniformDistribution.getValue(0, (2*Math.PI));
		rotationDelay = Time.ZERO;
	}
	
	public final double getDirection()
	{
		return angleDirection;
	}
	
	
	public final int getHitpoints() 
	{
		return hitpoints;
	}

	public final Time getLifespan() 
	{
		return lifespan;
	}
	
	public final void setDirection(double angle) 
	{
		angleDirection = angle;
	}
	
	public final boolean isDead()
	{
		if(hitpoints <= 0 || !(lifespan.isPositive()))
		{
			return true;
		}
		else
			return false;
		
	}

	public abstract void accept(AnimalVisitor visitor, RenderingMedia s);
	
	public abstract double getSpeed();
	
	public String toString()
	{
		return super.toString()+"\nSpeed : "+getSpeed()+"\nHitPoints : "+getHitpoints()+"\nLifeSpan : "+getLifespan();
	}
	
	public final void update(AnimalEnvironmentView env, Time dt)
	{
		Time temp = dt.times(getConfig().getDouble(ANIMAL_LIFESPAN_DECREASE_FACTOR));
		lifespan = lifespan.minus(temp);
		
		if(this.isDead() == false)
		{
			this.specificBehaviorDispatch(env, dt);
		}
		
		
	}
	
	protected final void move(Time dt)
	{
		
		final Time rotateDelay = Context.getConfig().getTime(Config.ANIMAL_NEXT_ROTATION_DELAY);
		double nouvelAng;
		
		rotationDelay = rotationDelay.plus(dt);
		
		while(rotationDelay.compareTo(rotateDelay) >= 0)
		{
			// on décremente le compteur de temps pour la rotation
			rotationDelay = rotationDelay.minus(rotateDelay);
			
			//on tire au harsard un angle en passant le tableau des angles et des probabilités retourné par la computeRotationProbs
			nouvelAng = Utils.pickValue(computeRotationProbs().getAngles(), computeRotationProbs().getProbabilities());
			
			this.setDirection((this.getDirection() + nouvelAng));
			
		}
		
		Vec2d ve = (Vec2d.fromAngle(getDirection())).scalarProduct((dt.toSeconds() * getSpeed()));
		
		this.setPosition(this.getPosition().add(ve));
		
	}
	
	
	protected RotationProbability computeRotationProbs()
	{
		double[] angles = {-180, -100, -55, -25, -10, 0, 10, 25, 55, 100, 180};
		double[] probs = {0.0000, 0.0000, 0.0005, 0.0010, 0.0050, 0.9870, 0.0050, 0.0010, 0.0005, 0.0000, 0.0000};
		
		return new RotationProbability(toRadian(angles), probs);
	}
	
	private double[] toRadian(double[] ang)
	{
		for(int i = 0; i < ang.length; i++)
		{
			ang[i] = Math.toRadians(ang[i]); 
		}
		return ang;
	}
	
	protected abstract void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt);
	

}
