package haiku_generator;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HaikuGUI extends JFrame {

	//declare GUI objects
	private JPanel panel;
	private JTextField keyword;
	private JLabel lblMessage;
	private JButton btnGenerateRand;
	private JButton btnGenerateCustom;
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
		keyword = new JTextField(20);
		btnGenerateRand = new JButton("Generate Random Haiku");
		results = new JTextArea(20, 20);
		btnGenerateCustom = new JButton("Generate Haiku");
		//add components to the panel
		panel.add(new JLabel("Enter key word(s): "));
		panel.add(keyword);
		panel.add(btnGenerateRand);
		panel.add(results);
		panel.add(btnGenerateCustom);
		//connect button to action
		btnGenerateRand.addActionListener(new ButtonHandler());
	}//end buildPanel method
	//create inner class to handle button click

	
	
	private class ButtonHandler implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{

			String keywordSave = keyword.getText();
			System.out.println("keyword is " + keywordSave);
			JSONArray resultArray= makeRequest("words?ml="+keywordSave+"&md=sp");

		}

	}//end actionPerformed


	private JSONArray makeRequest(String argument)
	{

		final java.util.logging.Logger log = java.util.logging.Logger.getLogger(WordBank.class.getName());

		HttpURLConnection conn = null;

		BufferedReader reader;
		String line;
		StringBuilder responseContent = new StringBuilder();
		try{
			URL url = new URL("https://api.datamuse.com/" + argument);
			//URL url= new URL("https://api.datamuse.com/words?sp=wasteland&qe=sp&md=ps&max=1");
			conn = (HttpURLConnection) url.openConnection();

			// Request setup
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);// 5000 milliseconds = 5 seconds
			conn.setReadTimeout(5000);

			// Test if the response from the server is successful
			int status = conn.getResponseCode();

			if (status >= 600) {
				reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}
			else {
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}
			//log.info("response code: " + status);
			//System.out.println(responseContent.toString());

			JSONArray resultArray = new JSONArray(responseContent.toString());
			for (int i = 0; i < resultArray.length(); i++) {
				JSONObject resultObj = resultArray.getJSONObject(i);
				String word = resultObj.getString("word");
				int syllables = resultObj.getInt("numSyllables");
				JSONArray tags = resultObj.getJSONArray("tags");
				System.out.println(word + " has " + syllables + " syllables " +"POS : "+tags.get(tags.length()-1));
			}

		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}
		//					
		return null;
	}


}
