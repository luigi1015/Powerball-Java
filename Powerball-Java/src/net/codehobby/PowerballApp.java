package net.codehobby;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class PowerballApp extends JFrame
{
	private static final long serialVersionUID = 893436983339796805L;//Automatically generated serial version ID.
	private JPanel panel;
	private DefaultListModel<String> powerballNumListModel;
	private JList<String> powerballNumList;
	private JScrollPane powerballNumScrollPane;

	public PowerballApp()
	{
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
		powerballNumListModel.addElement( "Test1" );
		powerballNumListModel.addElement( "Test2" );
		//Then set up the JList
		powerballNumList = new JList<String>( powerballNumListModel );
		powerballNumList.setSelectedIndex( 0 );
		//powerballNumList.addListSelectionListener( this );
		powerballNumList.setVisibleRowCount( 5 );
		//Then set up the scroll pane
		powerballNumScrollPane = new JScrollPane( powerballNumList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		powerballNumScrollPane.setBounds( 50, 50, 120, 120);
		panel.add( powerballNumScrollPane );

		setTitle( "PowerBall" );
		setSize( 600, 600 );
		setLocationRelativeTo( null );
		setDefaultCloseOperation( EXIT_ON_CLOSE );//Set it to shut down once the close button is pressed.
	}
	public static void main(String[] args) {
		//The invokeLater puts the application on the Swing Event Queue to make sure all UI updates are concurrency safe.
		SwingUtilities.invokeLater( new Runnable() {
			public void run() {
				PowerballApp pba = new PowerballApp();
				pba.setVisible( true );
			}
		});

	}

}
