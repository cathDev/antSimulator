package ch.epfl.moocprog;

import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.*;

import ch.epfl.moocprog.random.UniformDistribution;
import ch.epfl.moocprog.utils.Time;

public final class Anthill extends Positionable
{
	private double foodQte;
	private Uid id;
	private double prob;
	private Time compteur;

	public Anthill(ToricPosition t)
	{
		super(t);
		id = Uid.createUid();
		prob = getConfig().getDouble(ANTHILL_WORKER_PROB_DEFAULT);
		foodQte = 0.0;
		compteur = Time.ZERO;
	}
	
	public Anthill(ToricPosition t, double proba)
	{
		super(t);
		id = Uid.createUid();
		prob = proba;
		foodQte = 0.0;
		compteur = Time.ZERO;
	}
	
	 public double getFoodQuantity()
	 {
		 return foodQte;
	 }
	 
	 public void dropFood(double toDrop)
	 {
		 if(toDrop <= 0)
		 {
			 throw new IllegalArgumentException();
		 }
		 else
		 {
			 foodQte += toDrop; 
		 }
		 
	 }
	 
	 public Uid getAnthillId()
	 {
		 return id;
	 }
	 
	 public void update(AnthillEnvironmentView env, Time dt)
	 {
		 Time timeDelay = getConfig().getTime(ANTHILL_SPAWN_DELAY);
		 double probRan;
		 
		 compteur = compteur.plus(dt); 
		 
		 //env.addAnthill()
		while(compteur.compareTo(timeDelay) >= 0)
		{
			compteur = compteur.minus(timeDelay);
			probRan = UniformDistribution.getValue(0, 1);
			
			if(probRan <= prob)
			{
				Ant ant = new AntWorker(this.getPosition(), this.getAnthillId());
				env.addAnt(ant);
			}
			else
			{
				Ant ant1 = new AntSoldier(this.getPosition(), this.getAnthillId());
				env.addAnt(ant1);
			}
		}
	 }
	 
	 public String toString()
	 {
		 return super.toString()+"\nQuantity : "+getFoodQuantity();
	 }
}
