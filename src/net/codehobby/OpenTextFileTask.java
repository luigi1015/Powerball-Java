package net.codehobby;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
//import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.Iterator;
import java.util.concurrent.Callable;

import com.google.gson.Gson;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
//import com.google.gson.stream.JsonReader;

public class OpenTextFileTask implements Callable<String> {
	
	private ArrayList<PropertyChangeListener> listeners;
	private String filename, message;
	
	public OpenTextFileTask( PropertyChangeListener newListener, String newFilename, String newMessage )
	{
		filename = newFilename;
		message = newMessage;
		listeners = new ArrayList<PropertyChangeListener>();
		listeners.add( newListener );
	}
	
	public String call() throws Exception
	{
		//BufferedReader bufferedInput = null;
		//JsonReader reader = null;
		//String currentLine, jsonName;
		//PBNum newNum = new PBNum();
		
		try
		{
			//PBData
			ArrayList<PBData> newData = new Gson().fromJson( new FileReader(filename), new TypeToken<ArrayList<PBData>>(){}.getType());
			
			for( PBData data : newData )
			{
				//notifyListeners( this, message, "", data.Month + "/" + data.Day + "/" + data.Year + " " + data.Type + " " + data.Number );
				notifyListeners( this, message, "", data.month + "/" + data.day + "/" + data.year + " " + data.type + " " + data.num );
			}
			/*
			reader = new JsonReader( new FileReader(filename) );
			reader.beginArray();
			while( reader.hasNext() )
			{
				jsonName = reader.nextName();
				
				if( jsonName.equals("Number") )
				{
					newNum.setNumber( reader.nextInt() );
				}
				else if( jsonName.equals("Type") )
				{
					newNum.setType( reader.nextString() );
				}
				else if( jsonName.equals("Month") )
				{
					newNum.setMonth( reader.nextInt() );
				}
				else if( jsonName.equals("Day") )
				{
					newNum.setDay( reader.nextInt() );
				}
				else if( jsonName.equals("Year") )
				{
					newNum.setYear( reader.nextInt() );
				}
				else
				{
					System.err.println( "Unidentified JSON name: " + jsonName );
					reader.skipValue();
				}
				
				//System.out.println( newNum.toString() );
			}
			System.out.println( newNum.toString() );
			reader.endArray();
			*/
			/*
			bufferedInput = new BufferedReader( new FileReader(filename) );
			
			while( (currentLine = bufferedInput.readLine()) != null )
			{
				notifyListeners( this, message, "", currentLine );
			}
			*/
		}
		catch( IOException e )
		{
			System.err.println( "Error trying to read from file \"" + filename + "\": " + e.getMessage() );
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println( "Error trying to read from file \"" + filename + "\": " + e.getMessage() );
			e.printStackTrace();
			throw e;
		}
		finally
		{
			//reader.close();
			/*
			if( bufferedInput != null )
			{
				bufferedInput.close();
			}
			*/
		}
		return "done";
	}
	
	private void notifyListeners( Object object, String property, String oldValue, String newValue )
	{
		for( PropertyChangeListener listener : listeners )
		{
			listener.propertyChange( new PropertyChangeEvent(object, property, oldValue, newValue) );
		}
	}

}

class PBData
{
	int num;
	String type;
	int month;
	int day;
	int year;
}
/*
class PBData
{
	int Number;
	String Type;
	int Month;
	int Day;
	int Year;
}
*/
