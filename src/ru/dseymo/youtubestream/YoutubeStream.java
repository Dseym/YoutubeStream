package ru.dseymo.youtubestream;

import java.util.Date;

import lombok.Getter;

@Getter
public class YoutubeStream {
	String name, description, liveChatId, videoId;
	int viewers;
	long timeStart;
	
	public int getDurationTimeInSec() {
		return (int)((new Date().getTime()-timeStart)/1000);
	}
	
}
