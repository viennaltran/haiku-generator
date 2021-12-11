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

	public class Word {
		
		String text;
		int syllables;
		String pos;


		
		private Word(String txt, int syls, String p)
		{	
		text=txt;
		syllables=syls;
		pos=p;
	
		}
		
		public String getText()
		{
			return text;
		}
		
		public int getSyl()
		{
			return syllables;
		}
		
		public String getPos()
		{
			return pos;
		}
		
		
	}
	


	public WordBank() throws JSONException
	{
		implementSeeds(seedList);
		
	}
	
	private String[] seedList= new String[]{"earth", "wind","wild","landscape","magical","forever",  "feel", "water","bird","fly","life","grow","rock","Fertile", "Fierce", "Foliage", "Forest"}; 
	
	public  ArrayList<Word> nounBank = new ArrayList<Word>();
	public ArrayList<Word> verbBank = new ArrayList<Word>();
	public ArrayList<Word> adjBank = new ArrayList<Word>();

		
			
	 private void implementSeeds( String[]seeds) throws JSONException
	 {
		 //fills bank with words related to all the words in the array
		 
		 
		for (int s=0; s<seeds.length;s++)
		{
		JSONArray resultArray= makeRequest("words?ml="+seeds[s]+"&md=sp");
			 
			 if(resultArray.length()>0)
			 {
				 for (int i = 0; i < resultArray.length()-1; i++) {
					JSONObject resultObj = resultArray.getJSONObject(i);
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
					
					if (status >= 300) {
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
	 
	 
	
	 public Word randomWord(String pos)
	 {
		Random rand= new Random();
		int bound;
		 if(pos.equals("n")) {
			 bound = nounBank.size();
			 return nounBank.get(rand.nextInt(bound));
			 
		 }else if(pos.equals("v"))
		 {
			 bound = verbBank.size();
			 return verbBank.get(rand.nextInt(bound));
		 }
		 else if(pos.equals("adj"))
		 {
			 bound = adjBank.size();
			 return adjBank.get(rand.nextInt(bound));
		 }else return null;
		 
	 }
	 
//	 for (int i = 0; i < resultArray.length(); i++) {
//			JSONObject resultObj = resultArray.getJSONObject(i);
//			String word = resultObj.getString("word");
//			int syllables = resultObj.getInt("numSyllables");
//			JSONArray tags = resultObj.getJSONArray("tags");
//			System.out.println(word + " has " + syllables + " syllables " +"POS : "+tags.get(tags.length()-1));
//		}
	 
	 
	 
		

	
}

