package streamInfoYoutube;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class YouTube {

	protected static String api = "";
	private String videoID;
	private String liveChatID;
	private String urlVideo = "https://www.googleapis.com/youtube/v3/videos";
	private String urlChat = "https://www.googleapis.com/youtube/v3/liveChat/messages";
	private String pageToken;
	private String nameStream;
	
	public YouTube(String videoID) {
		
		this.videoID = videoID;
		
		if(api == "")
			Main.plugin.getLogger().warning("ERROR: Укажите API в конфиге");
		
	}
	
	public String getChat() throws Exception {
		
		StringBuffer content = new StringBuffer();

		URL url = new URL(urlVideo + "?part=liveStreamingDetails&part=snippet&id=" + videoID + "&key=" + api);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");
		
		BufferedReader in = new BufferedReader(
		  new InputStreamReader(con.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
		    content.append(inputLine);
		}
		in.close();

		nameStream = content.toString().split("\"title\": \"")[1].split("\",")[0];
		Main.plugin.getLogger().info("Подключен: " + nameStream);
		liveChatID = content.toString().split("\"activeLiveChatId\": \"")[1].split("\"")[0];

		return liveChatID;
		
	}
	
    public void setVideoID(String videoID) {
    	
    	this.videoID = videoID;
    	
    }
    
    public String getNameStream() {
    	
    	return nameStream;
    	
    }
    
    public String getVideoID() {
    	
    	return videoID;
    	
    }
	
	public Map<String, String> getMessages() throws Exception {
		
		Map<String, String> messages = new HashMap<String, String>();
		
		StringBuffer content = new StringBuffer();
		
		if(liveChatID == null)
			return null;

		URL url = new URL(urlChat + "?liveChatId=" + liveChatID + (pageToken != null ? "&pageToken=" + pageToken : "") + "&part=snippet&part=authorDetails&key=" + api);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");
		
		BufferedReader in = new BufferedReader(
		  new InputStreamReader(con.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
		    content.append(inputLine);
		}
		in.close();
		pageToken = content.toString().split("\"nextPageToken\": \"")[1].split("\"")[0];
		
		
		String[] mess = content.toString().split("\"messageText\": \"");
		for(int i = 1; i < mess.length; i++)
			messages.put(content.toString().split("\"displayName\": \"")[i].split("\"")[0], mess[i].split("\"")[0]);
		
		return messages;
		
	}
	
}
