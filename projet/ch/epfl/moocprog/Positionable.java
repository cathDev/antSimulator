
	
	package ch.epfl.moocprog;

	public class Positionable 
	{
		private ToricPosition positionne;
		
		public Positionable()
		{
			positionne = new ToricPosition();
		}
		
		public Positionable(ToricPosition pos)
		{
			positionne = pos;
		}
		
		public ToricPosition getPosition()
		{
			return positionne;
		}
		
		protected final void setPosition(ToricPosition position)
		{
			positionne = position;
		}
		
		
		 public String toString()
		 {
			return ""+getPosition();
			
		 }
	}


