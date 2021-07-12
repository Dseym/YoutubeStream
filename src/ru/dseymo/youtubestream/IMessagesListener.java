package ru.dseymo.youtubestream;

public interface IMessagesListener {
	
	public void onConnect();
	
	public void onDisconnect();
	
	public void onError(Result result, Exception exception);
	
	public void onUpdate();
	
	public void onMessage(Message message);
	
	public void onSuperChatMessage(SuperMessage message);
	
	public void onNewSponsor(Author sponsor);
	
	public void onCommand(Command command);
	
}
