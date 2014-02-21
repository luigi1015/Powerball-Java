package net.codehobby;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;

public class DownloadFromWebTask implements Callable<String> {
	
	private ArrayList<PropertyChangeListener> listeners;
	
	public DownloadFromWebTask( PropertyChangeListener newListener )
	{
		listeners.add( newListener );
	}
	
	public String call()
	{
		Calendar currentTime = Calendar.getInstance();
		SimpleDateFormat currentTimeFormatting = new SimpleDateFormat( "HH:mm:ss" );

		try {
			URL numbersURL = new URL( "http://www.powerball.com/powerball/winnums-text.txt" );
			BufferedReader numbersReader = new BufferedReader( new InputStreamReader(numbersURL.openStream()) );
			String numbersLine, drawDate;
			StringTokenizer lineTokenizer;
			PBNum newNum = new PBNum();
			
			while( (numbersLine = numbersReader.readLine()) != null )
			{//Go through each line and process them.
				lineTokenizer = new StringTokenizer( numbersLine, "  " );
				drawDate = lineTokenizer.nextToken();
				if( !(drawDate.startsWith("Draw")) )
				{//If it starts with "Draw", it's the first line and shouldn't be processed. If not, process the line.
					//pba.addPowerballLine( numbersLine );
					//System.out.println( numbersLine );
					//notifyListeners( this, "Add Line", "", numbersLine );
					
					for( int i = 0; i < 5; i++ )
					{//Go through each of the 5 White Ball numbers.
						//add( Integer.parseInt( lineTokenizer.nextToken() ), PowerballType.White, drawDate );
						//newNum = new PBNum( Integer.parseInt(lineTokenizer.nextToken()), PowerballType.White, drawDate );
						newNum.setNumber( Integer.parseInt(lineTokenizer.nextToken()) );
						newNum.setType( PowerballType.White );
						newNum.setDate( drawDate );
						notifyListeners( this, "Add Number", "", newNum.toString() );
					}
					
					//Process the PB number.
					//add( Integer.parseInt( lineTokenizer.nextToken() ), PowerballType.Powerball, drawDate );
					//newNum = new PBNum( Integer.parseInt(lineTokenizer.nextToken()), PowerballType.Powerball, drawDate );
					newNum.setNumber( Integer.parseInt(lineTokenizer.nextToken()) );
					newNum.setType( PowerballType.Powerball );
					newNum.setDate( drawDate );
					notifyListeners( this, "Add Number", "", newNum.toString() );
					
					if( lineTokenizer.hasMoreTokens() )
					{//If there's a Power Play number, process it.
						//add( Integer.parseInt( lineTokenizer.nextToken() ), PowerballType.PowerPlay, drawDate );
						//newNum = new PBNum( Integer.parseInt(lineTokenizer.nextToken()), PowerballType.PowerPlay, drawDate );
						newNum.setNumber( Integer.parseInt(lineTokenizer.nextToken()) );
						newNum.setType( PowerballType.PowerPlay );
						newNum.setDate( drawDate );
						notifyListeners( this, "Add Number", "", newNum.toString() );
					}
				}
			}
		}
		catch (Exception e) {
			System.out.println( "Error in downloadFromWeb(): " + e.getMessage() );
			e.printStackTrace();
		}
		finally
		{
			System.out.println( "Finished downloading from web. " + currentTimeFormatting.format(currentTime.getTime()) );
		}
		
		return "Finished downloading from web. " + currentTimeFormatting.format(currentTime.getTime());
	}
	
	private void notifyListeners( Object object, String property, String oldValue, String newValue )
	{
		for( PropertyChangeListener listener : listeners )
		{
			listener.propertyChange( new PropertyChangeEvent(object, property, oldValue, newValue) );
		}
	}

}
