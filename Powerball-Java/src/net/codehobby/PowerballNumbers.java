package net.codehobby;

import java.util.ArrayList;
//import java.util.List;

public class PowerballNumbers {
	
	private ArrayList<PBNum> pbNumbers;

	public PowerballNumbers()
	{
		pbNumbers = new ArrayList<PBNum>();
	}

	public void add( PBNum newNum )
	{//Add a Powerball number to the list.
		pbNumbers.add( newNum );
	}

	public void add(  int newNum, PowerballType newType, int newMonth, int newDay, int newYear  )
	{//Add and construct a Powerball number to the list.
		pbNumbers.add( new PBNum( newNum, newType, newMonth, newDay, newYear ) );
	}

	public void add(  int newNum, PowerballType newType, int newDate  )
	{//Add and construct a Powerball number to the list.
		pbNumbers.add( new PBNum( newNum, newType, newDate ) );
	}
	
	public void clear()
	{//Clears the list of numbers.
		pbNumbers.clear();
	}
	
	public int size()
	{//Returns the number of Powerball elements in the list.
		return pbNumbers.size();
	}
	
	public boolean isEmpty()
	{//Returns true if the list is empty, false otherwise.
		return pbNumbers.isEmpty();
	}
	
	public boolean remove( PBNum remNum )
	{//Removes the number for the list if it's present. Returns true if the element is present, false otherwise.
		return pbNumbers.remove( remNum );
	}
	
	public boolean hasNum( PBNum checkNum )
	{//Returns true if there's at least one PBNum equal to checkNum in the list, false otherwise.
		return pbNumbers.contains( checkNum );
	}
}
