package haiku_generator;

import java.util.Random;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class WordBank
{

	
	public WordBank() 
	{
		try {
			implementSeeds(seedList);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Word and = new Word("and",1,"conj");
		Word or  = new Word("or",1,"conj");
		conjBank.add(and);
		conjBank.add(or);
	}
	
	
	public WordBank(String[] wordList) throws JSONException
	{
		implementSeeds(wordList);
		Word and = new Word("and",1,"conj");
		Word or  = new Word("or",1,"conj");
		conjBank.add(and);
		conjBank.add(or);
	}

	
	private String[] seedList= new String[]{"people","love","earth", "wind","wild","landscape","magical","forever",  "feel", "water","bird","fly","life","grow","natural","Fertile","emotional" , "Forest","raining","stars","blue","green","believe"}; 
	
	public  ArrayList<Word> nounBank = new ArrayList<Word>();
	public ArrayList<Word> verbBank = new ArrayList<Word>();
	public ArrayList<Word> adjBank = new ArrayList<Word>();
	public ArrayList<Word> conjBank= new ArrayList<Word>();
		
	
	 public Word randomWord(String pos)
	 {
		Random rand= new Random();
		int bound;
		 if(pos.equals("n")&&!nounBank.isEmpty()) {
			 bound = nounBank.size();
			 return nounBank.get(rand.nextInt(bound));
			 
		 }else if(pos.equals("v")&&!verbBank.isEmpty())
		 {
			 bound = verbBank.size();
			 return verbBank.get(rand.nextInt(bound));
		 }
		 else if(pos.equals("adj")&&!adjBank.isEmpty())
		 {
			 bound = adjBank.size();
			 return adjBank.get(rand.nextInt(bound));
		 }else if(pos.equals("conj"))
		 {
			 return conjBank.get(rand.nextInt(2));
		 }else return null;
		 
	 }
	
		
	 public void addToBank(String[] words) throws JSONException {
		 
		 implementSeeds(words);
	 }
	 
	 
	 private void implementSeeds( String[]seeds) throws JSONException
	 {
		 //fills bank with words related to all the words in the array
		 
		 
		for (int s=0; s<seeds.length;s++)
		{
			
			String request= seeds[s].replaceAll("\s+","+");
			
			JSONArray resultArray= makeRequest("words?ml="+request+"&md=sp");
			//System.out.println("result Array"+ resultArray.toString());
			 
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
						nounBank.add(new Word(word,syllables,pos));
					}
					else if(pos.equals("adj"))
					{
						adjBank.add(new Word(word,syllables,pos));
					}
					else if(pos.equals("v"))
					{
						verbBank.add(new Word(word,syllables,pos));
				 	}
			 	
					}
		
			
				 }
			 }
	

		}
		
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
	 
	
	
	 
	 
	
//	 for (int i = 0; i < resultArray.length(); i++) {
//			JSONObject resultObj = resultArray.getJSONObject(i);
//			String word = resultObj.getString("word");
//			int syllables = resultObj.getInt("numSyllables");
//			JSONArray tags = resultObj.getJSONArray("tags");
//			System.out.println(word + " has " + syllables + " syllables " +"POS : "+tags.get(tags.length()-1));
//		}
	 
	 
	 
		

	
}

