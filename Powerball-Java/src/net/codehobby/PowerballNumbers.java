package net.codehobby;

import java.io.File;
import java.util.ArrayList;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

public class PowerballNumbers {
	//Table Creation SQL Statements:
	//CREATE TABLE IF NOT EXISTS PowerballNums ( Number INTEGER NOT NULL, Date INTEGER NOT NULL, Type TEXT NOT NULL);
	//Valid Type values for PowerballNums: "White" for white balls, "PB" for PowerBall balls, "PP" for PowerPlay values.
	//CREATE TABLE IF NOT EXISTS AppData( AppName TEXT, AppVersion TEXT, Comment TEXT);
	
	private ArrayList<PBNum> pbNumbers;
	private String defaultDBFilename = "powerball.db";

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
	
	public void saveToDatabase( String dbFileName )
	{//Save the data to a SQLite database indicated by file name in dbFileName.
		SQLiteConnection db = new SQLiteConnection( new File(dbFileName) );
		//SQLiteStatement st;
		String sqlStatement;
		String appVersion = "0.0.0";

		try {
			db.open( true );//Open the database with the option to allow its creation if it doesn't exist yet.
			
			//Create the tables.
			db.exec( "CREATE TABLE IF NOT EXISTS PowerballNums ( Number INTEGER NOT NULL, Date INTEGER NOT NULL, Type TEXT NOT NULL);" );
			db.exec( "CREATE TABLE IF NOT EXISTS AppData( AppName TEXT, AppVersion TEXT, Comment TEXT);" );
			
			//Go through each of the numbers in pbNumbers and add them to the database.
			for( PBNum powerballNumber : pbNumbers )
			{
				sqlStatement = "INSERT OR IGNORE INTO PowerballNums (Number, Date, Type) VALUES (" + powerballNumber.getNumber() + " , " + powerballNumber.getDate() + " , ";
				
				//Add the appropriate type of value into the SQL statement.
				switch( powerballNumber.getType() )
				{
					case White:
						sqlStatement += "White );";
						break;
						
					case Powerball:
						sqlStatement += "PB );";
						break;
						
					case PowerPlay:
						sqlStatement += "PP );";
						break;
				}
				
				db.exec( sqlStatement );//Now that the statement should be fully created, execute it.
			}
			
			//TODO: add the App info to AppData.
		} catch (SQLiteException e) {
			System.out.println( "Error in saveToDatabase( String dbFileName ): " + e.getMessage() );
			e.printStackTrace();
		}
		finally
		{
			db.dispose();
		}
		
	}
	
	public void saveToDatabase()
	{//Saves the database to the default file name indicated by defaultDBFileName variable.
		saveToDatabase( defaultDBFilename );
	}
	
	public void openDatabase( String filename )
	{//Reads data from database at filename to memory.
		//TODO: finish this.
	}
	
	public void openDatabase()
	{//Reads data from database at default filename to memory.
		openDatabase( defaultDBFilename );
	}
}
