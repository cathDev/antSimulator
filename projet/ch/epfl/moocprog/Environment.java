package ch.epfl.moocprog;

import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.gfx.EnvironmentRenderer;
import ch.epfl.moocprog.utils.Time;
//import ch.epfl.moocprog.utils.Utils;
import ch.epfl.moocprog.utils.Utils;

public final class Environment implements FoodGeneratorEnvironmentView, AnimalEnvironmentView, AnthillEnvironmentView, AntEnvironmentView, AntWorkerEnvironmentView  
{
	private FoodGenerator Fg;
	private List<Food> foodSet;
	private List<Animal> animals;
	private List<Anthill> anthills;
	
	public Environment()
	{
		Fg = new FoodGenerator();
		foodSet = new LinkedList<Food>();
		animals = new LinkedList<Animal>();
		anthills = new LinkedList<Anthill>();
		
	}
	
	
	public void addFood(Food food)
	{
		if(food == null)
		{
			throw new IllegalArgumentException("la quantite de nourriture et la position ne peuvent etre null");
		}
		else
		{
			foodSet.add(food);
		}
	}
	
	public List<Double> getFoodQuantities()
	{
		List<Double> l1 = new ArrayList<Double>();
		
		for(int i = 0; i < foodSet.size(); i++)
		{
			l1.add(foodSet.get(i).getQuantity());
		}
		
		return l1;
	}
	
	public void update(Time dt)
	{
		Iterator<Animal> i = animals.iterator();
		Fg.update(this, dt);
		
		while(i.hasNext())
		{
			Animal anima = i.next();
			if(anima.isDead()== true)
			{
				i.remove();
			}
			else
			{
				anima.update(this, dt);
			}
		}
		
		for(Anthill anthill: anthills)
		{
			anthill.update(this, dt);
		}
		
		foodSet.removeIf(food -> food.getQuantity() <= 0);
	}
	
	public void renderEntities(EnvironmentRenderer environmentRenderer)
	{
		foodSet.forEach(environmentRenderer::renderFood);
		animals.forEach(environmentRenderer::renderAnimal);
		anthills.forEach(environmentRenderer::renderAnthill);
	}
	
	public void addAnthill(Anthill anthill)
	{
		if(anthill == null)
		{
			throw new IllegalArgumentException();
		}
		else
		{
			anthills.add(anthill);
		}
	}
	
	
	public void addAnimal(Animal animal)throws IllegalArgumentException
	{
		if(animal == null)
		{
			throw new IllegalArgumentException();
		}
		else
		{
			animals.add(animal);
		}
	}
	
	public int getWidth()
	{
		return Context.getConfig().getInt(WORLD_WIDTH);
	}
	
	public int getHeight()
	{
		return Context.getConfig().getInt(WORLD_HEIGHT);
	}
	
	public List<ToricPosition> getAnimalsPosition()
	{
		List<ToricPosition> position = new LinkedList<ToricPosition>();
		for(Animal anima : animals)
		{
			position.add(anima.getPosition());
		}
		return position;
	}
	
	public void addAnt(Ant ant)
	{
		this.addAnimal(ant);
	}
	
	public Food getClosestFoodForAnt(AntWorker antWorker)
	{
		Food foods;
		foods = Utils.closestFromPoint(antWorker, foodSet);
		if(((antWorker.getPosition()).toricDistance(foods.getPosition())) > (getConfig().getDouble(ANT_MAX_PERCEPTION_DISTANCE)))
		{
			return null;
		}
		else
		{
			return foods;
		}
	}
	
	
	public boolean dropFood(AntWorker antWorker)
	{
		Anthill anth ;
		anth = Utils.closestFromPoint(antWorker, anthills);
		
		if((antWorker.getPosition()).toricDistance(anth.getPosition()) > (getConfig().getDouble(ANT_MAX_PERCEPTION_DISTANCE)))
		{
			return false;
		}
		else
		{
			return true;
		}
		
		
	}
	
	public List<Uid> getAnthillUid()
	{
		List<Uid> uid = new LinkedList<Uid>();
		for(Anthill anthill : anthills)
		{
			uid.add(anthill.getAnthillId());
		}
		return uid;
	}
	
	@Override
	public void selectSpecificBehaviorDispatch(AntWorker antWorker, Time dt)
	{
		
		antWorker.seekForFood(this, dt);
		
		
	}
	
	@Override
	public void selectSpecificBehaviorDispatch(AntSoldier antSoldier, Time dt)
	{
		antSoldier.seekForEnemies(this, dt);
	}
	
}
