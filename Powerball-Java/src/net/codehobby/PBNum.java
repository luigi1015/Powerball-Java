package net.codehobby;

public class PBNum {
	private int num;//The number drawn.
	private PowerballType type;//The type of number drawn. See the PowerballTypes enum for a list of types.
	private int month, day, year;//Date the number was drawn.
	
	public PBNum( int newNum, PowerballType newType, int newMonth, int newDay, int newYear )
	{
		setNumber( newNum );
		setType( newType );
		setMonth( newMonth );
		setDay( newDay );
		setYear( newYear );
	}
	
	public PBNum( int newNum, PowerballType newType, int newDate )
	{
		setNumber( newNum );
		setType( newType );
		setDate( newDate );
	}

	public int getNumber()
	{//Returns number.
		return num;
	}

	public void setNumber( int newNum )
	{//Set Number.
		if( (newNum < 1) || (newNum > 59) )
		{//The new number is out of bounds, throw exception.
			throw IllegalArgumentException;
		}
		num = newNum;
	}

	public PowerballType getType()
	{//Returns Type.
		return type;
	}

	public void setType( PowerballType newType )
	{//Set Type.
		type = newType;
	}

	public int getMonth()
	{//Returns month.
		return month;
	}

	public void setMonth( int newMonth )
	{//Set month.
		if( (newMonth < 1) || (newMonth > 12) )
		{//The new month is out of bounds, throw exception.
			throw IllegalArgumentException;
		}
		month = newMonth;
	}

	public int getDay()
	{//Returns day of the month.
		return day;
	}

	public void setDay( int newDay )
	{//Set day of the month.
		if( (newDay < 1) || (newDay > 31) )
		{//The new number is out of bounds, throw exception.
			throw IllegalArgumentException;
		}
		day = newDay;
	}

	public int getYear()
	{//Returns year.
		return year;
	}

	public void setYear( int newYear )
	{//Set year.
		if( (newYear < 1) || (newYear > 9999) )
		{//The new year is out of bounds, throw exception.
			throw IllegalArgumentException;
		}
		year = newYear;
	}

	public int getDate()
	{//Returns date in yyyymmdd format.
		return (year*10000) + month*100 + day;
	}

	public void setDate( int newDate )
	{//Sets the month, day and year from a date int in yyyymmdd format.
		day = newDate % 100;
		month = (newDate/100) % 100;
		year = newDate / 10000;
	}

}
