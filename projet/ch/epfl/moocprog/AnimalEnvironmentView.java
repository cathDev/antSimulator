package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Time;

public interface AnimalEnvironmentView 
{
	public void selectSpecificBehaviorDispatch(AntWorker antWorker, Time dt);
	
	public void selectSpecificBehaviorDispatch(AntSoldier antSoldier, Time dt);
}
