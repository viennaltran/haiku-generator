package haiku_generator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

//import java.lang.Object.*;
//
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;


public class Main{


	static WordBank defaultBank = new WordBank();
	static ArrayList<String[]> haiku = new ArrayList<String[]>();

	public static void main(String[] args) throws JSONException
	
	
	{
		//in button handler for custom haiku
		//get the input, save words into array
		//create a new wordbank with array instead of default 
		
	
		
//		
//		String[] firstLine= makeLine(5,defaultBank);
//
//		haiku.add(firstLine);
//
//		WordBank seededBank = new WordBank(firstLine);
//		String[] secondLine=makeLine(7,seededBank);
//
//		haiku.add(secondLine);
//
//
//		
//		seededBank.addToBank(secondLine);
//
//		String[] thirdLine= makeLine(5,seededBank);
//
//		haiku.add(thirdLine);
//
//		for(String[] l:haiku)
//		{
//			for(int i=0;i<l.length;i++)
//			{
//				System.out.print(l[i]+ " ");
//			}
//			System.out.println();
//
//		}

		//create an object
		HaikuGUI frame = new HaikuGUI();
		//set the size of the frame
		frame.setSize(300, 500); //these numbers are in pixels
		//set the default close operation
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//must make frame visible
		frame.setVisible(true);

	}

//	public static String[] makeLine(int lineSyls,WordBank bank)
//	{
//		int sylCount=0;
//		int runCount=0;
//
//
//		GrammarTree lineStructure= new GrammarTree();
//		lineStructure.generateTree(lineSyls);
//
//		ArrayList<String>  terminals =  lineStructure.terminalList();
//
//		String[] output= new String[terminals.size()];
//		int i=0;
//
//		while(sylCount != lineSyls)
//		{
//			// populate grammar structure with random words chosen from given bank
//			// if bank is empty, choose from the default bank
//			for(String s : terminals)
//			{
//
//				Word current;
//				current = bank.randomWord(s);
//				if(current==null)
//					current=defaultBank.randomWord(s);
//				sylCount=sylCount+current.getSyl();
//				output[i]=current.getText();
//				i++;
//
//			}
//			runCount++;
//
//
//			if(sylCount != lineSyls)
//			{
//				sylCount=0;
//				output = new String[terminals.size()];
//				i=0;
//			}
//			if(runCount>=15)
//			{//if grammar structure has too many words reset 
//				//System.out.println("!!!Reset!!!");
//				lineStructure.generateTree(lineSyls);
//				terminals= lineStructure.terminalList();
//				output= new String[terminals.size()];
//				i=0;
//				sylCount=0;
//				runCount=0;
//			}
//
//		} return output;
//
//	}






}

//	private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(Main.class.getName());
//	
//	private static HttpURLConnection conn;
//	public static void main(String[] args) {
//		System.out.println("Hi its mel");
//		
//		
//		BufferedReader reader;
//		String line;
//		StringBuilder responseContent = new StringBuilder();
//		try{
//			URL url = new URL("https://api.datamuse.com/words?rel_trg=wild&md=ps");
//			//URL url= new URL("https://api.datamuse.com/words?sp=wasteland&qe=sp&md=ps&max=1");
//			conn = (HttpURLConnection) url.openConnection();
//			
//			// Request setup
//			conn.setRequestMethod("GET");
//			conn.setConnectTimeout(5000);// 5000 milliseconds = 5 seconds
//			conn.setReadTimeout(5000);
//			
//			// Test if the response from the server is successful
//			int status = conn.getResponseCode();
//			
//			if (status >= 300) {
//				reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//				while ((line = reader.readLine()) != null) {
//					responseContent.append(line);
//				}
//				reader.close();
//			}
//			else {
//				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//				while ((line = reader.readLine()) != null) {
//					responseContent.append(line);
//				}
//				reader.close();
//			}
//			//log.info("response code: " + status);
//			System.out.println(responseContent.toString());
//			
//			JSONArray resultArray = new JSONArray(responseContent.toString());
//			for (int i = 0; i < resultArray.length(); i++) {
//				JSONObject resultObj = resultArray.getJSONObject(i);
//				String word = resultObj.getString("word");
//				int syllables = resultObj.getInt("numSyllables");
//				JSONArray tags = resultObj.getJSONArray("tags");
//				System.out.println(word + " has " + syllables + " syllables " +"POS : "+tags.get(tags.length()-1));
//			}
//			
//		}
//		catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} finally {
//			conn.disconnect();
//		}
////			
//		
//		
//		
//		
//	}
//	
////	public static String parse(String responseBody) throws JSONException {
////		JSONArray syl = new JSONArray(responseBody);
////		for (int i = 0 ; i < syl.length(); i++) {
////			JSONObject syll = syl.getJSONObject(i);			
////			String num_syl = syll.getString("numSyllables");
////			System.out.println(num_syl);
////		}
////		return null;
////	}
//firstLineStructure.generateTree(7);
//
//int sylCount=0;
//int runCount=0;
//ArrayList<String>  structure =  firstLineStructure.terminalList();
//
//while(sylCount!=7){
//	
//	for(String s : structure)
//	{
//		Word current;
//		current = newBank.randomWord(s);
//		sylCount=sylCount+current.getSyl();
//		
//		System.out.print(current.getText()+" ");
//		
//	}
//	
//	runCount+=1;	
//	System.out.println(" Syls:" +sylCount);
//	System.out.println("runCount:"+runCount);
//	
//	
//	
//	if(runCount>=15)
//	{//if grammar structure has too many words reset 
//		System.out.println("!!!Reset!!!");
//		firstLineStructure.generateTree(7);
//		structure= firstLineStructure.terminalList();
//		sylCount=0;
//		runCount=0;
//	}
//	if(sylCount !=7)
//	sylCount=0;
//} 

//for(int i=10;i<20;i++)
//{
//	System.out.println(newBank.randomWord("adj").getText()+" "+newBank.randomWord("n").getText()+" "+newBank.randomWord("v").getText());
//	System.out.println("the "+ newBank.randomWord("adj").getText()+" "+newBank.randomWord("n").getText());
//
//}
//
//}