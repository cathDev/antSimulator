package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Time;

public abstract class Ant extends Animal
{
	private Uid anthillId;
	
	public Ant(ToricPosition position, int point, Time dureeVie, Uid anthill)
	{
		super(position, point, dureeVie);
		anthillId = anthill;
	}
	
	public final Uid getAnthillId()
	{
		return anthillId;
	}
}
