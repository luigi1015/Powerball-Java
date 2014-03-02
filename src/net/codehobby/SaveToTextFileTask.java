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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


//import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.Gson;

public class SaveToTextFileTask implements Callable<String> {
	
	//private ArrayList<PropertyChangeListener> listeners;
	private String filename;
	private TreeSet<PBNum> data;
	private String filetype;
	
	public SaveToTextFileTask( String newFilename, String newFiletype, TreeSet<PBNum> newData )
	{
		//listeners = new ArrayList<PropertyChangeListener>();
		//listeners.add( newListener );
		
		filename = newFilename;
		data = newData;
		filetype = newFiletype;
	}
	
	public String call()
	{
		if( filetype.equals("json") )
		{
			saveJSON();
		}
		else if( filetype.equals("xml") )
		{
			saveXML();
		}
		
		System.out.println( "Finished Saving the Text data!" );
		
		return "Finished";
	}
	
	private void saveJSON()
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
	}
	
	private void saveXML()
	{
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document xmlDoc = docBuilder.newDocument();
			Element numbersElement = xmlDoc.createElement( "Numbers" );//Root element
			
			xmlDoc.appendChild( numbersElement );
			
			for( PBNum num : data )
			{//Go through each of the numbers and add them to the XML document.
				Element number = xmlDoc.createElement( "PowerballNumber" );
				numbersElement.appendChild( number );
				
				Element pbNumber = xmlDoc.createElement( "number" );
				pbNumber.appendChild( xmlDoc.createTextNode(num.getNumberInteger().toString()) );
				number.appendChild( pbNumber );
				
				Element pbType = xmlDoc.createElement( "type" );
				pbType.appendChild( xmlDoc.createTextNode(num.getTypeString()) );
				number.appendChild( pbType );
				
				Element pbMonth = xmlDoc.createElement( "month" );
				pbMonth.appendChild( xmlDoc.createTextNode(num.getMonthInteger().toString()) );
				number.appendChild( pbMonth );
				
				Element pbDay = xmlDoc.createElement( "day" );
				pbDay.appendChild( xmlDoc.createTextNode(num.getDayInteger().toString()) );
				number.appendChild( pbDay );
				
				Element pbYear = xmlDoc.createElement( "year" );
				pbYear.appendChild( xmlDoc.createTextNode(num.getYearInteger().toString()) );
				number.appendChild( pbYear );
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			//transformer.setOutputProperty("indent-amount", "2");
			DOMSource domSource = new DOMSource( xmlDoc );
			StreamResult streamResult = new StreamResult( new File(filename) );
			
			transformer.transform( domSource, streamResult );
		} catch (ParserConfigurationException e) {
			System.err.println( "Error saving XML file \"" + filename + "\": " + e.getMessage() );
			e.printStackTrace();
		} catch (DOMException e) {
			System.err.println( "Error saving XML file \"" + filename + "\": " + e.getMessage() );
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println( "Error saving XML file \"" + filename + "\": " + e.getMessage() );
			e.printStackTrace();
		}
	}

}
