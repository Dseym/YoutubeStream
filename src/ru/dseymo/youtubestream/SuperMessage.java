package ru.dseymo.youtubestream;

import lombok.Getter;

@Getter
public class SuperMessage extends Message {
	int amount, tier;
	String currency, amountString;
}
