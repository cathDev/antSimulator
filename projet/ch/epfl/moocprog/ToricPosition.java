package ch.epfl.moocprog;

// importation des fichiers de configuration
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.*;
import java.util.ArrayList;

import ch.epfl.moocprog.utils.Vec2d;

public final class ToricPosition 
{
	
	private final Vec2d position;
	
	public ToricPosition(double abc, double ord)
	{
		position = clampedPosition(abc, ord);
		
	}
	
	public ToricPosition()
	{
		position = new Vec2d(0.0,0.0);
	
	}
	
	public ToricPosition(Vec2d vec)
	{
		position = clampedPosition(vec.getX(), vec.getY());
		
	}
	
	private static Vec2d clampedPosition(double x, double y)
	{
		while( x < 0 )
		{
			x = x + getConfig().getInt(WORLD_WIDTH);
		}
		
		while( x >= getConfig().getInt(WORLD_WIDTH))
		{
			x = x - getConfig().getInt(WORLD_WIDTH);
		}
		
		while( y < 0)
		{
			y = y + getConfig().getInt(WORLD_HEIGHT);
		}
		
		while( y >= getConfig().getInt(WORLD_HEIGHT))
		{
			y = y - getConfig().getInt(WORLD_HEIGHT);
		}
		
		return new Vec2d(x,y);
		
	}
	
	public ToricPosition add(ToricPosition that)
	{
				
		return new ToricPosition(this.position.add(that.toVec2d()));
		
	}
	
	
	public ToricPosition add(Vec2d vec)
	{
		
		return new ToricPosition(this.position.add(clampedPosition(vec.getX(), vec.getY())));
	}
	
	// methode qui donne accès à la valeur de la variable position (equivalent à getPosition)
	
	public Vec2d toVec2d()
	{
		return position;
	}
	
	public Vec2d toricVector(ToricPosition that)
	{ 
		ArrayList<Vec2d> point = new ArrayList<Vec2d>(); 
		ArrayList<Double> distances = new ArrayList<Double>();
		
		 
		
		
		point.add(that.toVec2d());
		
		point.add(that.toVec2d().add(new Vec2d(0, getConfig().getInt(WORLD_HEIGHT)))); 
		
		point.add(that.toVec2d().add(new Vec2d(0, -(getConfig().getInt(WORLD_HEIGHT)))));
		
		point.add(that.toVec2d().add(new Vec2d(getConfig().getInt(WORLD_WIDTH), 0)));
		
		point.add(that.toVec2d().add(new Vec2d((-getConfig().getInt(WORLD_WIDTH)), 0)));
		
		point.add(that.toVec2d().add(new Vec2d(getConfig().getInt(WORLD_WIDTH), getConfig().getInt(WORLD_HEIGHT))));
		
		point.add(that.toVec2d().add(new Vec2d(getConfig().getInt(WORLD_WIDTH), -(getConfig().getInt(WORLD_HEIGHT)))));
		
		point.add(that.toVec2d().add(new Vec2d(-(getConfig().getInt(WORLD_WIDTH)), getConfig().getInt(WORLD_HEIGHT))));
		
		point.add(that.toVec2d().add(new Vec2d(-(getConfig().getInt(WORLD_WIDTH)), -(getConfig().getInt(WORLD_HEIGHT)))));
		
		// calcul des distances de chacun de ces vecteurs à this
		for(Vec2d vector : point)
		{
			distances.add(this.toVec2d().distance(vector));
		}
		
		double min = distances.get(0);
		for(int i = 1; i < distances.size(); i++)
		{
			if(min < distances.get(i))
			{
				
			}
			else
			{
				min = distances.get(i);
			}
		}
		//point.indexOf(min);
		
		return point.get(distances.indexOf(min)).minus(this.toVec2d());
	}
	
	
	public double toricDistance(ToricPosition that)
	{
		Vec2d vec = this.toricVector(that);
		return vec.length();
	}
	
	
	 public String toString()
	 { 
		 return ("Position : "+ position.getX()+", "+ position.getY());
	 }
}



