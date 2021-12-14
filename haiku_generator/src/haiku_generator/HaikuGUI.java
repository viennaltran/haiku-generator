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
		System.setProperty("myColor", "#89CFF0");
		panel.setBackground(Color.getColor("myColor"));
		btnGenerateRand = new JButton("Generate Random Haiku");
		results = new JTextArea(20, 20);
		keyword = new JTextField(20); 
		btnGenerateCustom = new JButton("Generate Haiku");
		//add components to the panel
		panel.add(btnGenerateRand);
		panel.add(results);
		panel.add(new JLabel("Enter key word(s): "));
		panel.add(keyword);
		panel.add(btnGenerateCustom);
		//connect button to action
		btnGenerateRand.addActionListener(new ButtonHandler());
		btnGenerateCustom.addActionListener(new ButtonHandler());
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
			case "Generate Random Haiku":
				try {
					results.setText("");
					generateRandomHaiku();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


				break;
			case "Generate Haiku":
				try {
					results.setText("");
					generateCustomHaiku();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			}//end switch

		}//end actionPerformed

	}//end actionPerformed


	static ArrayList<String[]> haiku = new ArrayList<String[]>();

	private void generateRandomHaiku() throws JSONException {	

		String[] firstLine= makeLine(5,defaultBank);

		haiku.add(firstLine);

		WordBank seededBank = new WordBank(firstLine);
		String[] secondLine=makeLine(7,seededBank);

		haiku.add(secondLine);

		seededBank.addToBank(secondLine);

		String[] thirdLine= makeLine(5,seededBank);

		haiku.add(thirdLine);

		for(String[] l:haiku)
		{
			for(int i=0;i<l.length;i++)
			{
				System.out.print(l[i]+ " ");
				results.append(l[i] + " ");

			}
			System.out.println();
			results.append("\n");

		}

	}


	private void generateCustomHaiku() throws JSONException {	

		String keywordSave = keyword.getText();
		JSONArray resultArray= makeRequest("words?rel_trg="+keywordSave+"&md=sp");

		//create a new wordbank with array
		WordBank customBank = new WordBank();

		if(resultArray!=null)
		{
			for (int i = 0; i < resultArray.length()-1; i++) {
				JSONObject resultObj = resultArray.getJSONObject(i);
				if(resultObj.toString().contains("tags"))
				{
					String word = resultObj.getString("word");
					int syllables = resultObj.getInt("numSyllables");

					JSONArray tags = resultObj.getJSONArray("tags");
					String pos="";

					if(tags.length()>0) {
						pos=(String) tags.get(tags.length()-1);
					}

					if (pos.equals("n"))
					{
						customBank.nounBank.add(new Word(word,syllables,pos));
					}
					else if(pos.equals("adj"))
					{
						customBank.adjBank.add(new Word(word,syllables,pos));
					}
					else if(pos.equals("v"))
					{
						customBank.verbBank.add(new Word(word,syllables,pos));
					}

				}


			}
		}

		String[] firstKeywordLine= makeLine(5,customBank);

		haiku.add(firstKeywordLine);

		WordBank seededBank = new WordBank(firstKeywordLine);
		String[] secondLine=makeLine(7,seededBank);

		haiku.add(secondLine);

		seededBank.addToBank(secondLine);

		String[] thirdLine= makeLine(5,seededBank);

		haiku.add(thirdLine);

		for(String[] l:haiku)
		{
			for(int i=0;i<l.length;i++)
			{
				System.out.print(l[i]+ " ");
				results.append(l[i] + " ");

			}
			System.out.println();
			results.append("\n");

		}

	}

	static WordBank defaultBank = new WordBank();

	public static String[] makeLine(int lineSyls,WordBank bank)
	{
		int sylCount=0;
		int runCount=0;


		GrammarTree lineStructure= new GrammarTree();
		lineStructure.generateTree(lineSyls);

		ArrayList<String>  terminals =  lineStructure.terminalList();

		String[] output= new String[terminals.size()];
		int i=0;

		while(sylCount != lineSyls)
		{
			// populate grammar structure with random words chosen from given bank
			// if bank is empty, choose from the default bank
			for(String s : terminals)
			{

				Word current;
				current = bank.randomWord(s);
				if(current==null)
					current=defaultBank.randomWord(s);
				sylCount=sylCount+current.getSyl();
				output[i]=current.getText();
				i++;
			}

			runCount++;

			if(sylCount != lineSyls)
			{
				sylCount=0;
				output = new String[terminals.size()];
				i=0;
			}
			if(runCount>=15)
			{//if grammar structure has too many words reset 
				//System.out.println("!!!Reset!!!");
				lineStructure.generateTree(lineSyls);
				terminals= lineStructure.terminalList();
				output= new String[terminals.size()];
				i=0;
				sylCount=0;
				runCount=0;
			}

		} return output;

	}




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
			//			for (int i = 0; i < resultArray.length(); i++) {
			//				JSONObject resultObj = resultArray.getJSONObject(i);
			//				String word = resultObj.getString("word");
			//				int syllables = resultObj.getInt("numSyllables");
			//				JSONArray tags = resultObj.getJSONArray("tags");
			//				System.out.println(word + " has " + syllables + " syllables " +"POS : "+tags.get(tags.length()-1));
			//			}

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
