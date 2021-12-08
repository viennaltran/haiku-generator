import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.lang.Object.*;
//import org.json.JSONObject;



public class Main{
	private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(Main.class.getName());
	
	private static HttpURLConnection conn;
	public static void main(String[] args) {
		
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
			
//			JSONObject myResponse = new JSONObject(responseContent.toString());
			
			
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			conn.disconnect();
		}
			
	}
	
//	public static String parse(String responseBody) {
//		JSONArray syl = new JSONArray(responseBody);
//		for (int i = 0 ; i < albums.length(); i++) {
//			JSONObject album = albums.getJSONObject(i);			
//			int userId = album.getInt("userId");
//			int id = album.getInt("id");
//			String title = album.getString("title");
//			System.out.println(id+" "+title+" "+userId);
//		}
//		return null;
//	}

}