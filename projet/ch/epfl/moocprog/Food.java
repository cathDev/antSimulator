package ch.epfl.moocprog;

public final class Food extends Positionable
{
	
	private double quantity ;
	
	public Food(ToricPosition tp, double q)
	{
		super(tp);
		if(q < 0)
		{
			quantity = 0.0;
		}
		else
		{
			quantity = q;
		}
	}
	
	public double getQuantity()
	{
		return quantity;
	}
	
	public double takeQuantity( double quantite) throws IllegalArgumentException
	{
		if(quantite > quantity)
		{
			double qte = quantity;
			quantity -= quantity;
			
			return qte;
		}
		else
			if(quantite < 0)
			{
				 
				throw new IllegalArgumentException("la quantité à prélever ne peut etre négative");
			}
		else
		{
			quantity -= quantite;
			
			return quantite;
		}
	}
	
	public String toString()
	{
		
		return super.toString()+"\n"+String.format("Quantity : %.2f", getQuantity());
	}
}
