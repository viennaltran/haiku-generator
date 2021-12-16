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
	private JButton btnGenerateRand;
	private JButton btnGenerateCustom;
	private JTextArea results;
	private JButton btnClear;
	private JLabel nounCounts;
	private JLabel verbCounts;
	private JLabel adjCounts;
	private JLabel conjCounts; 
	
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
		results = new JTextArea(10, 15);
		keyword = new JTextField(20); 
		btnGenerateCustom = new JButton("Generate Haiku");
		btnClear = new JButton("Clear");
		nounCounts = new JLabel();
		verbCounts = new JLabel();
		adjCounts = new JLabel();
		conjCounts = new JLabel();
		//add components to the panel
		panel.add(btnGenerateRand);
		panel.add(results);
		panel.add(new JLabel("Enter a keyword: "));
		panel.add(keyword);
		panel.add(btnGenerateCustom);
		panel.add(btnClear);
		panel.add(nounCounts);
		panel.add(verbCounts);
		panel.add(adjCounts);
		panel.add(conjCounts);
		//connect button to action
		btnGenerateRand.addActionListener(new ButtonHandler());
		btnGenerateCustom.addActionListener(new ButtonHandler());
		btnClear.addActionListener(new ButtonHandler());
	}//end buildPanel method
	//create inner class to handle button click

	static int nounCount= 0;
	static int verbCount = 0;
	static int adjCount = 0;
	static int conjCount = 0;
	
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
			case "Clear":
				results.setText("");
				nounCounts.setText("");
				verbCounts.setText("");
				adjCounts.setText("");
				conjCounts.setText("");
				nounCount= 0;
				verbCount = 0;
				adjCount = 0;
				conjCount = 0;
				
				break;
			case "Generate Random Haiku":
				try {
					generateRandomHaiku();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "Generate Haiku":
				try {
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

		haiku.clear();

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

		showStats();

	}

	private void generateCustomHaiku() throws JSONException {	

		haiku.clear();

		String keywordSave = keyword.getText();

		//making two requests
		JSONArray resultArray1= makeRequest("words?rel_trg="+keywordSave+"&md=sp");
		JSONArray resultArray2= makeRequest("words?ml="+keywordSave+"&md=sp"); 

		//System.out.println("result Array"+ resultArray.toString());
		//create a new wordbank with array
		String[] placeholder= {""};
		WordBank customBank = new WordBank(placeholder);

		if(resultArray1!=null && resultArray2!=null)
		{
			for (int i = 0; i < resultArray1.length()-1; i++) {
				JSONObject resultObj1 = resultArray1.getJSONObject(i);

				//System.out.println(resultObj1.toString()+ "object1");

				if(resultObj1.toString().contains("tags"))
				{
					String word1 = resultObj1.getString("word");
					int syllables1 = resultObj1.getInt("numSyllables");
					JSONArray tags1 = resultObj1.getJSONArray("tags");
					String pos="";

					if(tags1.length()>0) {
						pos=(String) tags1.get(tags1.length()-1);
					}

					if (pos.equals("n"))
					{
						customBank.nounBank.add(new Word(word1,syllables1,pos));
					}
					else if(pos.equals("adj"))
					{
						customBank.adjBank.add(new Word(word1,syllables1,pos));
					}
					else if(pos.equals("v"))
					{
						customBank.verbBank.add(new Word(word1,syllables1,pos));
					}

				}
			}// end of first for loop

			for (int j = 0; j < resultArray2.length()-1; j++) {
				JSONObject resultObj2 = resultArray2.getJSONObject(j);

				//System.out.println(resultObj2.toString()+ "object2");
				if(resultObj2.toString().contains("tags"))
				{

					String word2 = resultObj2.getString("word");
					int syllables2 = resultObj2.getInt("numSyllables");

					JSONArray tags2 = resultObj2.getJSONArray("tags");
					String pos="";

					if(tags2.length()>0) {
						pos=(String) tags2.get(tags2.length()-1);
					}

					if (pos.equals("n"))
					{
						customBank.nounBank.add(new Word(word2,syllables2,pos));
					}
					else if(pos.equals("adj"))
					{
						customBank.adjBank.add(new Word(word2,syllables2,pos));
					}
					else if(pos.equals("v"))
					{
						customBank.verbBank.add(new Word(word2,syllables2,pos));
					}
				}

			}//end of second for loop 
		}// end of if

		String[] firstKeywordLine= makeLine(5,customBank);



		haiku.add(firstKeywordLine);

		customBank.addToBank(firstKeywordLine);
		String[] secondLine=makeLine(7,customBank);

		haiku.add(secondLine);

		customBank.addToBank(secondLine);

		String[] thirdLine= makeLine(5,customBank);

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

		showStats();

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
				{
					current=defaultBank.randomWord(s);
				}
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

		}
		
		//finalized pos
		for(String t:terminals)
		{
			if(t.equals("n"))
			{
				nounCount++;
			}
			else if (t.equals("adj")) {
				adjCount++;
			}
			else if (t.equals("v")){
				verbCount++;
			}
			else {
				conjCount++;
			}
			System.out.println(t);

		}

		return output;

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
			return resultArray;

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

	private void showStats() {

		nounCounts.setText("The number of nouns: "+ nounCount);
		verbCounts.setText("The number of verbs: "+ verbCount);
		adjCounts.setText("The number of adjectives: "+ adjCount);
		conjCounts.setText("The number of conjugations: "+ conjCount);

	}


}
