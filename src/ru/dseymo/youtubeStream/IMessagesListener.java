package ru.dseymo.youtubeStream;

public interface IMessagesListener {
	
	public void onMessage(String nick, String message);
	
	public void onCommand(String nick, String command, String[] args);
	
}
