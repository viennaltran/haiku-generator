package haiku_generator;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class HaikuGUI extends JFrame {

	//declare GUI objects
	private JPanel panel;
	private JTextField txtTest;
	private JLabel lblMessage;
	private JButton btnGenerate;
	private JTextArea results;
	//constructor
	public HaikuGUI()
	{
		//give frame a title
		setTitle("Haiku Generator");
		//call method to "build" the components on the panel
		buildPanel();
		//add the panel to the frame
		add(panel);
	}
	private void buildPanel()
	{
		//create GUI objects
		panel = new JPanel();
		panel.setBackground(Color.PINK);
		txtTest = new JTextField(20);
		btnGenerate = new JButton("Generate Haiku");
		results = new JTextArea(20, 20);
		//add components to the panel
		panel.add(new JLabel("Enter key word(s): "));
		panel.add(txtTest);
		panel.add(btnGenerate);
		panel.add(results);
		//connect button to action
		btnGenerate.addActionListener(new ButtonHandler());
	}//end buildPanel method
	//create inner class to handle button click

	private class ButtonHandler implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			//which button clicked?
			System.out.println(e.getActionCommand());
			//returns the text on the button
			// test if button is connected
			//System.out.println("Button clicked");
			switch(e.getActionCommand())
			{
			case "Generate Haiku":
				//get name from txtName textbox
				String test = txtTest.getText();
				//print for testing
				System.out.println("test is " + test);
				break;
			}//end switch

		}//end actionPerformed
	}//end HelloButtonHandler class
}
