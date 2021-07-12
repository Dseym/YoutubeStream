package ru.dseymo.youtubestream;

import java.util.Arrays;
import java.util.TimerTask;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import ru.dseymo.youtubestream.Author.AuthorRole;

public class YoutubeTask extends TimerTask {
	private YouTube youtube;
	private String pageToken, liveChatID, api;
	
	public YoutubeTask(YouTube youtube, String liveChatID, String api) {
		this.youtube = youtube;
		this.liveChatID = liveChatID;
		this.api = api;
	}
	
	@Override
	public void run() {
		try {
			Connection c = Jsoup.connect(YouTube.API_URL_CHAT + "?liveChatId=" + liveChatID + (pageToken != null ? "&pageToken=" + pageToken : "") + "&part=snippet&part=authorDetails&key=" + api);
			
			c.userAgent("Chrome");
			c.header("Content-Type", "application/json");
			
			JsonObject json = new JsonParser().parse(c.ignoreContentType(true).execute().body()).getAsJsonObject();
			JsonPrimitive token = json.getAsJsonPrimitive("nextPageToken");
			
			if(token != null)
				pageToken = token.getAsString();
			
			for(IMessagesListener list: youtube.listeners)
				list.onUpdate();
			
			for(JsonElement item: json.getAsJsonArray("items")) {
				JsonObject obj = item.getAsJsonObject();
				JsonObject info = obj.getAsJsonObject("snippet");
				JsonObject infoAuthor = obj.getAsJsonObject("authorDetails");
				String text = info.getAsJsonPrimitive("displayMessage").getAsString();
				String typeMess = info.getAsJsonPrimitive("type").getAsString();
				Author author = new Author();
				
				author.channelId = infoAuthor.getAsJsonPrimitive("channelId").getAsString();
				author.name = infoAuthor.getAsJsonPrimitive("displayName").getAsString();
				author.verified = infoAuthor.getAsJsonPrimitive("isVerified").getAsBoolean();
				author.sponsor = infoAuthor.getAsJsonPrimitive("isChatSponsor").getAsBoolean();
				
				if(infoAuthor.getAsJsonPrimitive("isChatOwner").getAsBoolean())
					author.role = AuthorRole.OWNER;
				else if(infoAuthor.getAsJsonPrimitive("isChatModerator").getAsBoolean())
					author.role = AuthorRole.MODERATOR;
				else
					author.role = AuthorRole.VIEWER;
				
				if(text.charAt(0) == youtube.prefixCommand && typeMess.equalsIgnoreCase("textMessageEvent")) {
					Command cmd = new Command();
					
					cmd.author = author;
					cmd.command = text.substring(1).split(" ")[0];
					cmd.args = Arrays.copyOfRange(text.split(" "), 1, text.split(" ").length);
					
					for(IMessagesListener list: youtube.listeners)
						list.onCommand(cmd);
				} else if(typeMess.equalsIgnoreCase("superChatEvent")) {
					JsonObject superChat = info.getAsJsonObject("superChatDetails");
					SuperMessage mess = new SuperMessage();
					
					mess.author = author;
					mess.text = superChat.getAsJsonPrimitive("userComment").getAsString();
					mess.amount = superChat.getAsJsonPrimitive("amountMicros").getAsInt();
					mess.amountString = superChat.getAsJsonPrimitive("amountDisplayString").getAsString();
					mess.currency = superChat.getAsJsonPrimitive("currency").getAsString();
					mess.tier = superChat.getAsJsonPrimitive("tier").getAsInt();
					
					for(IMessagesListener list: youtube.listeners)
						list.onSuperChatMessage(mess);
				} else if(typeMess.equalsIgnoreCase("newSponsorEvent")) {
					for(IMessagesListener list: youtube.listeners)
						list.onNewSponsor(author);
				} else {
					Message mess = new Message();
					
					mess.author = author;
					mess.text = text;
					
					for(IMessagesListener list: youtube.listeners)
						list.onMessage(mess);
				}
			}
		} catch (HttpStatusException e) {
			e.printStackTrace();
			
			youtube.disconnect();
			
			int code = e.getStatusCode();
			Result result = Result.ERROR;
			
			if(code == 403)
				result = Result.QUOTA;
			
			for(IMessagesListener listener: youtube.listeners)
				listener.onError(result, e);
			
			return;
		} catch (Exception e) {
			e.printStackTrace();
			
			youtube.disconnect();
			
			for(IMessagesListener listener: youtube.listeners)
				listener.onError(Result.ERROR, e);
			
			return;
		}
	}
	
}
