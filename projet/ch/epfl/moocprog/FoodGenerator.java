package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Time;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.*;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;
import ch.epfl.moocprog.random.UniformDistribution;
import ch.epfl.moocprog.random.NormalDistribution;

public final class FoodGenerator 
{
	private Time compteur;
	
	public FoodGenerator()
	{
		compteur = Time.ZERO;
	}
	
	public void update(FoodGeneratorEnvironmentView env, Time dt)
	{
		final double qteMin = getConfig().getDouble(NEW_FOOD_QUANTITY_MIN);
		final double qteMax = getConfig().getDouble(NEW_FOOD_QUANTITY_MAX);
		final int width = Context.getConfig().getInt(WORLD_WIDTH);
		final int height = Context.getConfig().getInt(WORLD_HEIGHT);
		final Time timeDelay = Context.getConfig().getTime(Config.FOOD_GENERATOR_DELAY);
		
		double qte;
		ToricPosition tp;
		Food foods;
		
		compteur = compteur.plus(dt);
		while(compteur.compareTo(timeDelay)>= 0)
		{
			compteur = compteur.minus(timeDelay);
			qte = UniformDistribution.getValue(qteMin, qteMax);
			tp = new ToricPosition((NormalDistribution.getValue((width/2.0), ((width * width)/16.0))), (NormalDistribution.getValue((height/2.0), ((height*height) / 16.0))));
			
			foods = new Food(tp, qte);
			env.addFood(foods);
		}
	}
	
	public Time getCompteur()
	{
		return compteur;
	}
}
