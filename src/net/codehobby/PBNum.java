package net.codehobby;

import java.util.StringTokenizer;

public class PBNum implements Comparable<PBNum> {
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
	
	public PBNum( int newNum, PowerballType newType, String newDate )
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
			throw new IllegalArgumentException( "setNumber argument, " + newNum + ", should be between 1 and 59 inclusive." );
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
			throw new IllegalArgumentException( "setMonth argument, " + newMonth + ", should be between 1 and 12 inclusive." );
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
			throw new IllegalArgumentException( "setDay argument, " + newDay + ", should be between 1 and 31 inclusive." );
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
			throw new IllegalArgumentException( "setYear argument, " + newYear + ", should be between 1 and 9999 inclusive." );
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

	public void setDate( String newDate )
	{//Sets the month, day and year from a date String in mm/dd/yyyy format.
		StringTokenizer dateTokenizer = new StringTokenizer( newDate, "/" );
		setMonth( Integer.parseInt( dateTokenizer.nextToken() ) );
		setDay( Integer.parseInt( dateTokenizer.nextToken() ) );
		setYear( Integer.parseInt( dateTokenizer.nextToken() ) );
	}

	@Override
	public int compareTo(PBNum o)
	{//Returns a negative number (defined by lessThan) if object o is less than this object, 0 if the two are equal, or a positive number (defined by greaterThan) if the object o is greater than this object.
		int lessThan = -1;
		int greaterThan = 1;
		
		if( getDate() < o.getDate() )
		{//If this object's date is less than o's date, return the less than value.
			return lessThan;
		}
		else if( getDate() > o.getDate() )
		{//If this object's date is more than o's date, return the greater than value.
			return greaterThan;
		}
		else
		{//The dates are equal, so start comparing the powerball types and numbers.
			if( getType().ordinal() < o.getType().ordinal() )
			{//If this object's type is less than o's type, return the less than value.
				return lessThan;
			}
			else if( getType().ordinal() > o.getType().ordinal() )
			{//If this object's type is more than o's type, return the greater than value.
				return greaterThan;
			}
			else
			{//The rest of the values are equal, so compare the numbers themselves.
				if( getNumber() < o.getNumber() )
				{//If this object's number is less than o's number, return the less than value.
					return lessThan;
				}
				else if( getNumber() > o.getNumber() )
				{//If this object's number is more than o's number, return the greater than value.
					return greaterThan;
				}
				else
				{//All values important to sorting are equal, return 0.
					return 0;
				}
			}
		}
	}

}
