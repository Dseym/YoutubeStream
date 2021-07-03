package ru.dseymo.youtubestream;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Result {
	
	THIS_NOT_STREAM("This not stream, check streamID"),
	NOTHING_FOUND("Nothing found, check streamID"),
	SUCCESS("Success"),
	QUOTA("Quota exceeded"),
	WRONG_API("Wrong API, check your googleAPI"),
	ALREADY_CONNECTED("The connection has already been made"),
	ERROR("An unknown error has occurred");
	
	private String mess;
	
	public String getMessage() {
		return mess;
	}
	
}
