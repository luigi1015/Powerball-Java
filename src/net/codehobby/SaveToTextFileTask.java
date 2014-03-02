package net.codehobby;

//import java.beans.PropertyChangeEvent;
//import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//import java.util.ArrayList;
import java.util.TreeSet;
import java.util.concurrent.Callable;

import com.google.gson.Gson;

public class SaveToTextFileTask implements Callable<String> {
	
	//private ArrayList<PropertyChangeListener> listeners;
	private String filename;
	TreeSet<PBNum> data;
	
	public SaveToTextFileTask( String newFilename, TreeSet<PBNum> newData )
	{
		//listeners = new ArrayList<PropertyChangeListener>();
		//listeners.add( newListener );
		
		filename = newFilename;
		data = newData;
	}
	
	public String call()
	{
		File fl;
		PrintWriter fileOutput = null;
		Gson jsonConverter = new Gson();
		
		try
		{
			fl = new File( filename );
			fileOutput = new PrintWriter( new FileWriter(fl) );
			
			fileOutput.println( jsonConverter.toJson(data) );
			
			/*
			for( String line : data )
			{
				fileOutput.println( line );
			}
			*/
			
			//fileOutput.close();
		}
		catch( IOException e )
		{
			System.err.println( "Error trying to save text data for filename \"" + filename + "\": " + e.getMessage() );
			e.printStackTrace();
		}
		finally
		{
			if( fileOutput != null )
			{
				fileOutput.close();
			}
		}
		
		System.out.println( "Finished Saving the Text data!" );
		
		return "Finished";
	}

}
