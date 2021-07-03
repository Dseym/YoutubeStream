package ru.dseymo.youtubestream;

import lombok.Getter;

@Getter
public class Author {
	public static enum AuthorRole {
		VIEWER, MODERATOR, OWNER;
	}
	
	
	String channelId, name;
	boolean verified, sponsor;
	AuthorRole role;
}
