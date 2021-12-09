import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
//import java.lang.Object.*;
//
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class Main{
	
	private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(Main.class.getName());
	
	private static HttpURLConnection conn;
	public static void main(String[] args) {
		System.out.println("Hi");
		
		
		BufferedReader reader;
		String line;
		StringBuilder responseContent = new StringBuilder();
		try{
			URL url = new URL("https://api.datamuse.com/words?rel_rhy=forgetful&md=s");
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
			System.out.println(responseContent.toString());
			
			JSONArray resultArray = new JSONArray(responseContent.toString());
			for (int i = 0; i < resultArray.length(); i++) {
				JSONObject resultObj = resultArray.getJSONObject(i);
				String word = resultObj.getString("word");
				int syllables = resultObj.getInt("numSyllables");
				System.out.println(word + " has " + syllables + " syllables.");
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
	}
	
//	public static String parse(String responseBody) throws JSONException {
//		JSONArray syl = new JSONArray(responseBody);
//		for (int i = 0 ; i < syl.length(); i++) {
//			JSONObject syll = syl.getJSONObject(i);			
//			String num_syl = syll.getString("numSyllables");
//			System.out.println(num_syl);
//		}
//		return null;
//	}

}