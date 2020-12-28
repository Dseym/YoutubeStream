package ru.dseymo.youtubeStream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class YouTube {

	private String videoID;
	private String liveChatID;
	private String urlVideo = "https://www.googleapis.com/youtube/v3/videos";
	private String urlChat = "https://www.googleapis.com/youtube/v3/liveChat/messages";
	private String nameStream;
	private String api;
	private char prefCommand;
	private Timer timer;
	private IMessagesListener listener;
	
	public YouTube(String videoID, String api, char prefCommand, IMessagesListener listener) {
		
		this.videoID = videoID;
		this.api = api;
		this.prefCommand = prefCommand;
		this.listener = listener;
		
	}
	
	public Result connect() {
		
		if(timer != null) return Result.ALREADY_CONNECTED;
		
		try {
			
			StringBuffer content = new StringBuffer();

			URL url = new URL(urlVideo + "?part=liveStreamingDetails&part=snippet&id=" + videoID + "&key=" + api);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");
			
			if(con.getResponseMessage().equalsIgnoreCase("Bad Request"))
				return Result.WRONG_API;
			else if(con.getResponseMessage().equalsIgnoreCase("Quota Exceeded"))
				return Result.QUOTA;
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			in.close();
			
			try {
				
				nameStream = content.toString().split("\"title\": \"")[1].split("\",")[0];
				liveChatID = content.toString().split("\"activeLiveChatId\": \"")[1].split("\"")[0];
				
			} catch (Exception e) {return Result.NOT_FOUND_STREAM;}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return Result.ERROR;
			
		}
		
		startRead();
		
		return Result.SUCCESS;
		
	}
    
    public String getNameStream() {
    	
    	return nameStream;
    	
    }
    
    public String getVideoID() {
    	
    	return videoID;
    	
    }
	
	public void disconnect() {
    	
		if(timer != null)
			timer.cancel();
    	timer = null;
    	
    }
    
	private void startRead() {
		
		TimerTask task = new TimerTask() {
			
			String pageToken;
			
			@Override
			public void run() {
				
				StringBuffer content = new StringBuffer();
				
				try {
				
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
					
				} catch (Exception e) {
					
					e.printStackTrace();
					disconnect();
					connect();
					return;
					
				}
				
				String[] messages = content.toString().split("\"displayMessage\": \"");
				for(int i = 1; i < messages.length; i++) {
					
					String nick = content.toString().split("\"displayName\": \"")[i].split("\",")[0];
					String mess = messages[i].split("\",")[0];
					if(mess.charAt(0) == prefCommand)
						listener.onCommand(nick, mess.substring(1).split(" ")[0], Arrays.copyOfRange(mess.split(" "), 1, mess.split(" ").length));
					else
						listener.onMessage(nick, mess);
					
				}
				
			}
			
		};
		
		timer = new Timer();
		
		timer.scheduleAtFixedRate(task, 1000, 5000);
		
	}
	
	@Override
	public void finalize() {
		
		disconnect();
		
	}
	
}
