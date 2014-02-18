package net.codehobby;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class PowerballApp extends JFrame implements ActionListener, PropertyChangeListener
{
	private static final long serialVersionUID = 893436983339796805L;//Automatically generated serial version ID.
	
	private JPanel panel;
	private DefaultListModel<String> powerballNumListModel;
	private JList<String> powerballNumList;
	private JScrollPane powerballNumScrollPane;
	private JButton populateButton;
	private JMenuBar mainMenuBar;
	private JMenu fileMenu;
	private JMenuItem populateMenuItem, exitMenuItem;
	
	private PowerballNumbers nums;

	public PowerballApp( PowerballNumbers newNums )
	{
		newNums.addChangeListener( this );
		nums = newNums;
		//nums = new PowerballNumbers();
		//nums = new PowerballNumbers( this );
		initUI();
	}

	private void initUI()
	{
		//Set up the panel.
		panel = new JPanel();
		panel.setLayout( null );
		getContentPane().add( panel );

		//Set up the list of Powerball numbers.
		//First set up the list model.
		powerballNumListModel = new DefaultListModel<String>();
		//powerballNumListModel.addElement( "Test1" );
		//powerballNumListModel.addElement( "Test2" );
		//Then set up the JList
		powerballNumList = new JList<String>( powerballNumListModel );
		powerballNumList.setSelectedIndex( 0 );
		//powerballNumList.addListSelectionListener( this );
		powerballNumList.setVisibleRowCount( 5 );
		//Then set up the scroll pane
		powerballNumScrollPane = new JScrollPane( powerballNumList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		powerballNumScrollPane.setBounds( 50, 50, 270, 120);
		panel.add( powerballNumScrollPane );
		//Set up the Populate button.
		populateButton = new JButton( "Populate from the web" );
		populateButton.setActionCommand( "Populate" );
		populateButton.addActionListener( this );
		populateButton.setBounds( 50, 180, 210, 30 );
		panel.add( populateButton );
		
		//Set up the file menu.
		mainMenuBar = new JMenuBar();
		//File menu
		fileMenu = new JMenu( "File" );
		mainMenuBar.add( fileMenu );
		populateMenuItem = new JMenuItem( "Popuate" );
		populateMenuItem.getAccessibleContext().setAccessibleDescription( "Populate the numbers from the Web." );
		fileMenu.add( populateMenuItem );
		fileMenu.addSeparator();
		exitMenuItem = new JMenuItem( "Exit" );
		exitMenuItem.getAccessibleContext().setAccessibleDescription( "Exit the program." );
		fileMenu.add( exitMenuItem );
		setJMenuBar(mainMenuBar);

		setTitle( "PowerBall" );
		setSize( 600, 600 );
		setLocationRelativeTo( null );
		setDefaultCloseOperation( EXIT_ON_CLOSE );//Set it to shut down once the close button is pressed.
	}
	
	public void actionPerformed( ActionEvent e )
	{
		if( e.getActionCommand().equals("Populate") )
		{
			//TODO: Finish this.
			//System.out.println( "This is a placeholder for the real Populate code." );
			nums.downloadFromWeb();
		}
	}
	
	public void addPowerballLine( String newLine )
	{
		powerballNumListModel.addElement( newLine );
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0)
	{
		if( ((String)arg0.getPropertyName()).equals("Add Line") )
		{
			addPowerballLine( (String)arg0.getNewValue() );
		}
		
	}

	
	public static void main(String[] args) {
		//The invokeLater puts the application on the Swing Event Queue to make sure all UI updates are concurrency safe.
		SwingUtilities.invokeLater( new Runnable() {
			public void run() {
				PowerballNumbers nums = new PowerballNumbers();
				PowerballApp pba = new PowerballApp( nums );
				pba.setVisible( true );
			}
		});

	}

}