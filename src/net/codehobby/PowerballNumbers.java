package net.codehobby;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
//import java.io.IOException;
import java.io.InputStreamReader;
//import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeSet;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

public class PowerballNumbers {
	//Table Creation SQL Statements:
	//CREATE TABLE IF NOT EXISTS PowerballNums ( Number INTEGER NOT NULL, Date INTEGER NOT NULL, Type TEXT NOT NULL);
	//Valid Type values for PowerballNums: "White" for white balls, "PB" for PowerBall balls, "PP" for PowerPlay values.
	//CREATE TABLE IF NOT EXISTS AppData( AppName TEXT, AppVersion TEXT, Comment TEXT);
	
	private TreeSet<PBNum> pbNumbers;
	//private ArrayList<PBNum> pbNumbers;
	private String defaultDBFilename = "powerball.db";
	private String appVersion = "0.0.0";//Official version of the app.
	private String appName = "Powerball";
	private String appComment = "Still writing the code.";
	
	private ArrayList<PropertyChangeListener> listeners;
	//private PowerballApp pba;
/*
	public PowerballNumbers( PowerballApp newPba )
	{
		pbNumbers = new ArrayList<PBNum>();
		//listeners = new ArrayList<PropertyChangeListener>();
		pba = newPba;
	}
*/
	public PowerballNumbers()
	{
		pbNumbers = new TreeSet<PBNum>();
		listeners = new ArrayList<PropertyChangeListener>();
	}

	public boolean add( PBNum newNum )
	{//Add a Powerball number to the list. Since no duplicates are allowed, won't add a second version of an element. Returns true if the element was added, false if not.
		return pbNumbers.add( newNum );
/*
		if( newNum.getType().compareTo(PowerballType.White) == 0 )
		{
			System.out.println( "White ball: " + newNum.getMonth() + "/" + newNum.getDay() + "/" + newNum.getYear() + " num: " + newNum.getNumber() );
		}
		else if( newNum.getType().compareTo(PowerballType.Powerball) == 0 )
		{
			System.out.println( "Powerball: " + newNum.getMonth() + "/" + newNum.getDay() + "/" + newNum.getYear() + " num: " + newNum.getNumber() );
		}
		else if( newNum.getType().compareTo(PowerballType.PowerPlay) == 0 )
		{
			System.out.println( "Power Play: " + newNum.getMonth() + "/" + newNum.getDay() + "/" + newNum.getYear() + " num: " + newNum.getNumber() );
		}
*/
	}

	public boolean add(  int newNum, PowerballType newType, int newMonth, int newDay, int newYear  )
	{//Add and construct a Powerball number to the list. Since no duplicates are allowed, won't add a second version of an element. Returns true if the element was added, false if not.
		return pbNumbers.add( new PBNum( newNum, newType, newMonth, newDay, newYear ) );
/*
		if( newType.compareTo(PowerballType.White) == 0 )
		{
			System.out.println( "White ball: " + newMonth + "/" + newDay + "/" + newYear + " num: " + newNum );
		}
		else if( newType.compareTo(PowerballType.Powerball) == 0 )
		{
			System.out.println( "Powerball: " + newMonth + "/" + newDay + "/" + newYear + " num: " + newNum );
		}
		else if( newType.compareTo(PowerballType.PowerPlay) == 0 )
		{
			System.out.println( "Power Play: " + newMonth + "/" + newDay + "/" + newYear + " num: " + newNum );
		}
*/
	}

	public boolean add(  int newNum, PowerballType newType, int newDate  )
	{//Add and construct a Powerball number to the list. Since no duplicates are allowed, won't add a second version of an element. Returns true if the element was added, false if not.
		return pbNumbers.add( new PBNum( newNum, newType, newDate ) );
/*
		if( newType.compareTo(PowerballType.White) == 0 )
		{
			System.out.println( "White ball: " + newDate + " num: " + newNum );
		}
		else if( newType.compareTo(PowerballType.Powerball) == 0 )
		{
			System.out.println( "Powerball: " + newDate + " num: " + newNum );
		}
		else if( newType.compareTo(PowerballType.PowerPlay) == 0 )
		{
			System.out.println( "Power Play: " + newDate + " num: " + newNum );
		}
*/
	}

	public boolean add(  int newNum, PowerballType newType, String newDate  )
	{//Add and construct a Powerball number to the list. Since no duplicates are allowed, won't add a second version of an element. Returns true if the element was added, false if not.
		return pbNumbers.add( new PBNum( newNum, newType, newDate ) );
/*
		if( newType.compareTo(PowerballType.White) == 0 )
		{
			System.out.println( "White ball: " + newDate + " num: " + newNum );
		}
		else if( newType.compareTo(PowerballType.Powerball) == 0 )
		{
			System.out.println( "Powerball: " + newDate + " num: " + newNum );
		}
		else if( newType.compareTo(PowerballType.PowerPlay) == 0 )
		{
			System.out.println( "Power Play: " + newDate + " num: " + newNum );
		}
*/
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
		
		//TODO: Make this multithreaded so the GUI isn't waiting around for the data to save to the database.
		
		SQLiteConnection db = new SQLiteConnection( new File(defaultDBFilename) );
		SQLiteStatement st;
		String sqlStatement;

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
			
			st = db.prepare( "INSERT OR IGNORE INTO AppData (AppName, AppVersion, Comment) VALUES (?, ?, ? )" );
			try
			{
				st.bind( 1, appName );
				st.bind( 2, appVersion );
				st.bind( 3, appComment );
				st.step();
			}
			finally
			{
				st.dispose();
			}
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
		//Table Creation SQL Statements:
		//CREATE TABLE IF NOT EXISTS PowerballNums ( Number INTEGER NOT NULL, Date INTEGER NOT NULL, Type TEXT NOT NULL);
		//Valid Type values for PowerballNums: "White" for white balls, "PB" for PowerBall balls, "PP" for PowerPlay values.
		//CREATE TABLE IF NOT EXISTS AppData( AppName TEXT, AppVersion TEXT, Comment TEXT);
		
		//TODO: Make this multithreaded so the GUI isn't waiting around for the database to open.
		
		//TODO: finish this.
		SQLiteConnection db = new SQLiteConnection( new File(defaultDBFilename) );
		SQLiteStatement st;
		//String sqlStatement;
		
		pbNumbers.clear();//Make sure to delete the numbers to avoid duplicates. **Save to the database first to avoid data loss.**
		
		try
		{
			db.open( true );//Open the database with the option to allow its creation if it doesn't exist yet.
			
			st = db.prepare( "SELECT Number, Date, Type FROM PowerballNums;");
			while( st.step() )
			{
				add( st.columnInt(0), PowerballType.valueOf(st.columnString(2)), st.columnInt(1) );
			}
		} catch (SQLiteException e) {
			System.out.println( "Error in openDatabase( String dbFileName ): " + e.getMessage() );
			e.printStackTrace();
		}
		finally
		{
			db.dispose();
		}
	}
	
	public void openDatabase()
	{//Reads data from database at default filename to memory.
		openDatabase( defaultDBFilename );
	}
	
	public void downloadFromWeb()
	{//Downloads the data from the web to memory.
		//TODO: add notifyListeners calls for PowerballApp.
		
		pbNumbers.clear();//Make sure to delete the numbers to avoid duplicates.

		try {
			URL numbersURL = new URL( "http://www.powerball.com/powerball/winnums-text.txt" );
			BufferedReader numbersReader = new BufferedReader( new InputStreamReader(numbersURL.openStream()) );
			String numbersLine, drawDate;
			StringTokenizer lineTokenizer;
			
			while( (numbersLine = numbersReader.readLine()) != null )
			{//Go through each line and process them.
				lineTokenizer = new StringTokenizer( numbersLine, "  " );
				drawDate = lineTokenizer.nextToken();
				if( !(drawDate.startsWith("Draw")) )
				{//If it starts with "Draw", it's the first line and shouldn't be processed. If not, process the line.
					//pba.addPowerballLine( numbersLine );
					//System.out.println( numbersLine );
					notifyListeners( this, "Add Line", "", numbersLine );
					
					for( int i = 0; i < 5; i++ )
					{//Go through each of the 5 White Ball numbers.
						add( Integer.parseInt( lineTokenizer.nextToken() ), PowerballType.White, drawDate );
					}
					
					//Process the PB number.
					add( Integer.parseInt( lineTokenizer.nextToken() ), PowerballType.Powerball, drawDate );
					
					if( lineTokenizer.hasMoreTokens() )
					{//If there's a Power Play number, process it.
						add( Integer.parseInt( lineTokenizer.nextToken() ), PowerballType.PowerPlay, drawDate );
					}
				}
			}
		} catch (Exception e) {
			System.out.println( "Error in downloadFromWeb(): " + e.getMessage() );
			e.printStackTrace();
		}
	}
	
	public void addChangeListener( PropertyChangeListener newListener )
	{//Add the listner to the list so that the class can notify it.
		listeners.add( newListener );
	}
	
	private void notifyListeners( Object object, String property, String oldValue, String newValue )
	{
		for( PropertyChangeListener listener : listeners )
		{
			listener.propertyChange( new PropertyChangeEvent(object, property, oldValue, newValue) );
		}
	}
	
	public ArrayList<String> getDrawings()
	{//Return an array of strings representing the drawings in the format "mm/dd/yyyy WB1 WB2 WB3 WB4 WB4 PB PP" or "mm/dd/yyyy WB1 WB2 WB3 WB4 WB4 PB".
		ArrayList<String> drawings = new ArrayList<String>();
		
		//TODO: Finish this.
		
		return drawings;
	}
	
	public Map<Integer, Integer> getNumberCounts()
	{//Returns a map of the different numbers (the keys) with their counts (the values).
		Map<Integer, Integer> numCounts = new HashMap<Integer, Integer>();
		
		//TODO: Finish this.
		
		return numCounts;
	}
}
