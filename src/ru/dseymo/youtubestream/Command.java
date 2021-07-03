package ru.dseymo.youtubestream;

import lombok.Getter;

@Getter
public class Command {
	Author author;
	String command;
	String[] args;
}
