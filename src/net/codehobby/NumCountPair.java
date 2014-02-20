package net.codehobby;

public class NumCountPair implements Comparable<NumCountPair>
{
	private int num, count;

	public int compareTo(NumCountPair arg0)
	{//Returns a negative number (defined by lessThan) if this object is less than object arg0, 0 if the two are equal, or a positive number (defined by greaterThan) if this object is greater than object arg0.
		int lessThan = -1;
		int greaterThan = 1;
		
		if( count < arg0.getCount() )
		{
			return lessThan;
		}
		else if( count > arg0.getCount() )
		{
			return greaterThan;
		}
		else
		{//Counts are equal, go to the numbers.
			if( num < arg0.getNum() )
			{
				return lessThan;
			}
			else if( num > arg0.getNum() )
			{
				return greaterThan;
			}
			else
			{//For the important values for sorting, the two are equal.
				return 0;
			}
		}
	}
	
	public NumCountPair( int newNum, int newCount )
	{
		num = newNum;
		count = newCount;
	}
	
	public int getNum()
	{
		return num;
	}
	
	public void setNum( int newNum )
	{
		num = newNum;
	}
	
	public int getCount()
	{
		return count;
	}
	
	public void setCount( int newCount )
	{
		count = newCount;
	}
	
	public void addToCount( int additionalValue )
	{
		count += additionalValue;
	}
	
	public String toString()
	{
		return num + ": " + count;
	}

}
