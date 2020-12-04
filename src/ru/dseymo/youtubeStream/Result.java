package ru.dseymo.youtubeStream;

public enum Result {
	
	NOT_FOUND_STREAM("Stream not found, check streamID"),
	SUCCESS("Success"),
	QUOTA("Quota exceeded"),
	WRONG_API("Wrong API, check your API"),
	ALREADY_CONNECTED("The connection has already been made"),
	ERROR("An unknown error has occurred");
	
	String mess;
	
	Result(String mess) {
		
		this.mess = mess;
		
	}
	
}
