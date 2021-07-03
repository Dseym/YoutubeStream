package ru.dseymo.youtubestream;

public interface IMessagesListener {
	
	public void onMessage(Message message);
	
	public void onSuperChatMessage(SuperMessage message);
	
	public void onNewSponsor(Author sponsor);
	
	public void onCommand(Command command);
	
}
