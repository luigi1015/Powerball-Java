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





import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
//import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class OpenTextFileTask implements Callable<String> {
	
	private ArrayList<PropertyChangeListener> listeners;
	private String filename, fileType, message;
	
	public OpenTextFileTask( PropertyChangeListener newListener, String newFilename, String newFileType, String newMessage )
	{
		filename = newFilename;
		fileType = newFileType;
		message = newMessage;
		listeners = new ArrayList<PropertyChangeListener>();
		listeners.add( newListener );
	}
	
	public String call() throws Exception
	{
		if( fileType.equals("json") )
		{
			openJsonFile();
		}
		else if( fileType.equals("xml") )
		{
			openXmlFile();
		}
		return "done";
	}
	
	private void openJsonFile() throws Exception
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
	}
	
	private void openXmlFile()
	{
		try {
			File xmlFile = new File( filename );
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document xmlDoc = docBuilder.parse( xmlFile );
			NodeList pbNumbers = xmlDoc.getElementsByTagName( "PowerballNumber" );
			
			for( int i = 0; i < pbNumbers.getLength(); i++ )
			{//Go through the nodes and process them.
				Node newNode = pbNumbers.item( i );
				
				if( newNode.getNodeType() == Node.ELEMENT_NODE )
				{
					Element newElement = (Element) newNode;
					PBNum newNum = new PBNum();
					
					newNum.setNumber( Integer.valueOf(newElement.getElementsByTagName("number").item(0).getTextContent()).intValue() );
					newNum.setType( newElement.getElementsByTagName("type").item(0).getTextContent() );
					newNum.setMonth( Integer.valueOf(newElement.getElementsByTagName("month").item(0).getTextContent()).intValue() );
					newNum.setDay( Integer.valueOf(newElement.getElementsByTagName("day").item(0).getTextContent()).intValue() );
					newNum.setYear( Integer.valueOf(newElement.getElementsByTagName("year").item(0).getTextContent()).intValue() );
					
					notifyListeners( this, "Add Number", "", newNum.toString() );
				}
			}
		} catch (ParserConfigurationException e) {
			System.err.println( "Error trying to open XML file \"" + filename + "\": " + e.getMessage() );
			e.printStackTrace();
		} catch (SAXException e) {
			System.err.println( "Error trying to open XML file \"" + filename + "\": " + e.getMessage() );
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println( "Error trying to open XML file \"" + filename + "\": " + e.getMessage() );
			e.printStackTrace();
		} catch (DOMException e) {
			System.err.println( "Error trying to open XML file \"" + filename + "\": " + e.getMessage() );
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println( "Error trying to open XML file \"" + filename + "\": " + e.getMessage() );
			e.printStackTrace();
		}
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
