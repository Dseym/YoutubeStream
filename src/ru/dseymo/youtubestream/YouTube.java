package ru.dseymo.youtubestream;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Timer;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.Getter;
import lombok.Setter;

public class YouTube {
	static final String API_URL_VIDEO = "https://www.googleapis.com/youtube/v3/videos";
	static final String API_URL_CHAT = "https://www.googleapis.com/youtube/v3/liveChat/messages";
	
	
	@Getter
	private YoutubeStream stream;
	@Getter @Setter
	char prefixCommand = '!';
	private Timer timer;
	ArrayList<IMessagesListener> listeners = new ArrayList<>();
	@Setter
	private int timeUpdateChatInSec = 7;
	
	public Result connect(String videoID, String googleAPI) {
		if(isConnected())
			return Result.ALREADY_CONNECTED;
		
		stream = new YoutubeStream();
		
		stream.videoId = videoID;
		
		Result result = update(googleAPI);
		
		if(result == Result.SUCCESS) {
			timer = new Timer();
			
			timer.scheduleAtFixedRate(new YoutubeTask(this, stream.liveChatId, googleAPI), 1000, timeUpdateChatInSec*1000);
			
			for(IMessagesListener listener: listeners)
				listener.onConnect();
		} else
			stream = null;
		
		return result;
	}
	
	public Result update(String googleAPI) {
		if(googleAPI == null || googleAPI.isEmpty())
			return Result.WRONG_API;
		
		try {
			Connection c = Jsoup.connect(API_URL_VIDEO + "?part=liveStreamingDetails&part=snippet&id=" + stream.videoId + "&key=" + googleAPI);
			
			c.userAgent("Chrome");
			c.header("Content-Type", "application/json");
			c.ignoreContentType(true);
			
			JsonObject resp = new JsonParser().parse(c.execute().body()).getAsJsonObject();
			JsonArray items = resp.getAsJsonArray("items");
			
			if(items.size() == 0)
				return Result.NOTHING_FOUND;
			
			JsonObject video = items.get(0).getAsJsonObject();
			JsonObject info = video.getAsJsonObject("snippet");
			
			if(info.getAsJsonPrimitive("liveBroadcastContent").getAsString().equalsIgnoreCase("none"))
				return Result.THIS_NOT_STREAM;
			
			JsonObject infoStream = video.getAsJsonObject("liveStreamingDetails");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			
			stream.name = info.getAsJsonPrimitive("title").getAsString();
			stream.description = info.getAsJsonPrimitive("description").getAsString();
			stream.viewers = infoStream.getAsJsonPrimitive("concurrentViewers").getAsInt();
			stream.liveChatId = infoStream.getAsJsonPrimitive("activeLiveChatId").getAsString();
			stream.timeStart = format.parse(infoStream.getAsJsonPrimitive("actualStartTime").getAsString()).getTime();
		} catch (HttpStatusException e) {
			int code = e.getStatusCode();
			Result result = Result.ERROR;
			
			if(code == 400)
				result = Result.WRONG_API;
			else if(code == 403)
				result = Result.QUOTA;
			
			for(IMessagesListener listener: listeners)
				listener.onError(result, e);
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			
			for(IMessagesListener listener: listeners)
				listener.onError(Result.ERROR, e);
			
			return Result.ERROR;
		}
		
		return Result.SUCCESS;
	}
	
	public boolean isConnected() {
		return stream != null;
	}
	
	public void disconnect() {
		if(isConnected()) {
			timer.cancel();
			
			for(IMessagesListener listener: listeners)
				listener.onDisconnect();
		}
		
    	timer = null;
    	stream = null;
    }
	
	public void addListener(IMessagesListener listener) {
		if(!listeners.contains(listener))
			listeners.add(listener);
	}
	
	public void removeListener(IMessagesListener listener) {
		if(listeners.contains(listener))
			listeners.remove(listener);
	}
	
	@Override
	public void finalize() {
		disconnect();
	}
	
}
