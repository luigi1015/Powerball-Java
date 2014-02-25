package net.codehobby;

//import java.beans.PropertyChangeEvent;
//import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class SaveToTextFileTask implements Callable<String> {
	
	//private ArrayList<PropertyChangeListener> listeners;
	private String filename;
	ArrayList<String> data;
	
	public SaveToTextFileTask( String newFilename, ArrayList<String> newData )
	{
		//listeners = new ArrayList<PropertyChangeListener>();
		//listeners.add( newListener );
		
		filename = newFilename;
		data = newData;
	}
	
	public String call()
	{
		try
		{
			File fl = new File( filename );
			PrintWriter fileOutput = new PrintWriter( new FileWriter(fl) );
			
			for( String line : data )
			{
				fileOutput.println( line );
			}
			
			fileOutput.close();
		}
		catch( IOException e )
		{
			System.err.println( "Error trying to save JSON data for filename \"" + filename + "\": " + e.getMessage() );
			e.printStackTrace();
		}
		
		System.out.println( "Finished Saving the Text data!" );
		
		return "Finished";
	}

}
