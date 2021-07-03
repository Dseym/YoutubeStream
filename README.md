# YoutubeStream
This library allows you to monitor the stream and chat of the stream on YouTube.

# ENG
## Using
You can use this library in your project using Maven.
pom.xml
```xml
<repositories>
  <repository>
  	<id>Dseymo-Repo</id>
  	<url>http://f0461095.xsph.ru</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
	<groupId>ru.dseymo.youtubestream</groupId>
	<artifactId>YoutubeStream</artifactId>
	<version>1.0</version>
  </dependency>
</dependencies>
```

## Getting googleAPI
1. Log in or create an account at https://developers.google.com/.
2. Follow this link https://console.developers.google.com/project and click the CREATE PROJECT button
3. Specify a title and click Create
4. On the top left, click on the 3 dashes and select API and Services, then select ENABLE APIS AND SERVICES
5. Scroll down and find Youtube Data API v3, click on it and click the Enable button, wait
6. Go to Credentials and click the Create Credentials, API Key button
7. In the line your API key is your googleAPI

## API
```java
IMessagesListener listener = new IMessagesListener() {
	
	@Override
	public void onSuperChatMessage(SuperMessage message) {
		System.out.println("New SuperChat Message");
	}
	
	@Override
	public void onNewSponsor(Author sponsor) {
		System.out.println("New Sponsor");
	}
	
	@Override
	public void onMessage(Message message) {
		System.out.println("New Message");
	}
	
	@Override
	public void onCommand(Command command) {
		System.out.println("New Command");
	}
	
};

YouTube youtube = new YouTube();

youtube.connect(videoID, googleAPI);

youtube.addListener(listener);
```

# RUS
## Использование
Вы можете использовать эту библиотеку в своем проекте с помощью Maven.
pom.xml
```xml
<repositories>
  <repository>
  	<id>Dseymo-Repo</id>
  	<url>http://f0461095.xsph.ru</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
	<groupId>ru.dseymo.youtubestream</groupId>
	<artifactId>YoutubeStream</artifactId>
	<version>1.0</version>
  </dependency>
</dependencies>
```

## Получение googleAPI
1. Войти в систему или создать учетную запись на https://developers.google.com/.
2. Перейдите по этой ссылке https://console.developers.google.com/project и нажмите кнопку СОЗДАЙТЕ ПРОЕКТ
3. Укажите название и нажмите кнопку Создать
4. Слева сверху нажмите на 3 тире и выберите API и Сервисы, после выберите ENABLE APIS AND SERVICES
5. Полестайте вниз и найдите Youtube Data API v3, нажмите на него и нажмите кнопку Включить, ждите
6. Перейдите в Учетные данные и нажмите кнопку Создать Учетный данные, ключ API
7. В строке Ваш ключ API это и есть ваш googleAPI

## API
```java
IMessagesListener listener = new IMessagesListener() {
	
	@Override
	public void onSuperChatMessage(SuperMessage message) {
		System.out.println("New SuperChat Message");
	}
	
	@Override
	public void onNewSponsor(Author sponsor) {
		System.out.println("New Sponsor");
	}
	
	@Override
	public void onMessage(Message message) {
		System.out.println("New Message");
	}
	
	@Override
	public void onCommand(Command command) {
		System.out.println("New Command");
	}
	
};

YouTube youtube = new YouTube();

youtube.connect(videoID, googleAPI);

youtube.addListener(listener);
```
