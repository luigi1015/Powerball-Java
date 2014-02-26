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
	
	public PBNum( String newInput )
	{//Takes a string in the format "mm/dd/yyyy type value" and inputs the values using inputString( String newString ).
		inputString( newInput );
	}
	
	public PBNum()
	{
		num = 0;
		type = PowerballType.White;
		month = 0;
		day = 0;
		year = 0;
	}

	public int getNumber()
	{//Returns number.
		return num;
	}

	public Integer getNumberInteger()
	{//Returns number.
		return new Integer( num );
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

	public String getTypeString() throws Exception
	{//Returns Type.
		if( getType().compareTo(PowerballType.Powerball) == 0 )
		{
			return "Powerball";
		}
		else if( getType().compareTo(PowerballType.PowerPlay) == 0 )
		{
			return "Power Play";
		}
		else if( getType().compareTo(PowerballType.White) == 0 )
		{
			return "White";
		}
		else
		{
			throw new Exception( "PowerballType " + getType() + " isn't valid in getTypeString()." );
		}
	}

	public void setType( String newType ) throws Exception
	{//Returns Type.
		if( newType.equals("Powerball") )
		{
			setType( PowerballType.Powerball );
		}
		else if( newType.equals("Power Play") )
		{
			setType( PowerballType.PowerPlay );
		}
		else if( newType.equals("White") )
		{
			setType( PowerballType.White );
		}
		else
		{
			throw new Exception( "Type " + newType + " for the PowerballType in setTypeString( String newType ) isn't valid." );
		}
	}

	public void setType( PowerballType newType )
	{//Set Type.
		type = newType;
	}

	public int getMonth()
	{//Returns month.
		return month;
	}

	public Integer getMonthInteger()
	{//Returns month.
		return new Integer( month );
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

	public Integer getDayInteger()
	{//Returns day of the month.
		return new Integer( day );
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

	public Integer getYearInteger()
	{//Returns year.
		return new Integer( year );
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

	public Integer getDateInteger()
	{//Returns date in yyyymmdd format.
		return new Integer( (year*10000) + month*100 + day );
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
	{//Returns a negative number (defined by lessThan) if this object is less than object o, 0 if the two are equal, or a positive number (defined by greaterThan) if this object is greater than object o.
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
	
	public String toString()
	{//Returns the value in the format "mm/dd/yyyy type value".
		String pbnumString;
		
		//Get the date.
		pbnumString = getMonth() + "/" + getDay() + "/" + getYear() + " ";
		
		//Get the type.
		if( getType().compareTo(PowerballType.Powerball) == 0 )
		{//Powerball type.
			pbnumString += "Powerball ";
		}
		else if( getType().compareTo(PowerballType.PowerPlay) == 0 )
		{//Power Play type.
			pbnumString += "PowerPlay ";
		}
		else if( getType().compareTo(PowerballType.White) == 0 )
		{//White ball type.
			pbnumString += "White ";
		}
		
		//Get the value.
		pbnumString += getNumber();
		
		return pbnumString;
	}
	
	public void inputString( String newString )
	{//Takes a string in the format "mm/dd/yyyy type value" and inputs the values.
		StringTokenizer inputTokenizer = new StringTokenizer( newString, " " );
		String typeString;
		
		setDate( inputTokenizer.nextToken() );
		
		typeString = inputTokenizer.nextToken();
		if( typeString.equals("Powerball") )
		{
			setType( PowerballType.Powerball );
		}
		else if( typeString.equals("PowerPlay") )
		{
			setType( PowerballType.PowerPlay );
		}
		else if( typeString.equals("White") )
		{
			setType( PowerballType.White );
		}
		
		setNumber( Integer.parseInt(inputTokenizer.nextToken()) );
	}

}
