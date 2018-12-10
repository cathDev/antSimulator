package ch.epfl.moocprog;

public class RotationProbability 
{
	private double[] angles;
	private double[] probabilites;
	
	public RotationProbability(double []ang, double []prob)
	{
		if((ang != null && prob != null) && (ang.length == prob.length))
		{
			angles = ang.clone();
			probabilites = prob.clone();
		}
		else
		{
			throw new IllegalArgumentException();
		}
	}
	
	public double[] getAngles()
	{
		return angles.clone();
	}
	
	public double[] getProbabilities()
	{
		return probabilites.clone();
	}
}
