# streamInfoYouTube
Плагин позволяет связать майнкрафт и стрим на ютубе, имеет API / The plugin allows you to link Minecraft and YouTube stream, has an API

## ENG
# Compile
1. Download the source code and upload it to Eclipse for example.
2. Add External JARs: [Server Core](https://getbukkit.org/download/craftbukkit) 1.12-1.14.
3. Now you have the code that you can edit!

# Install for Server
1. Download the compiled [StreamInfoYouTube](https://github.com/Dseym/streamInfoYouTube/releases/download/streamInfoYouTube/streamInfoYouTube.jar) and upload it to your server.
2. Have fun!

# Instruction
1. Get your [googleAPI](https://console.developers.google.com/apis), [instructions](https://rapidapi.com/blog/how-to-get-youtube-api-key/)
2. Create a folder in plugins called StreamInfoYoutube
3. Create a config.yml file there
4. In the file, write - api: googleAPI

# API
Instantiate the class - new YouTube(videoID)
videoID - is the ID of the YouTube stream watch?v=ID (MVXja46rOOg)
Class Methods:
setVideoID (ID) / getVideoID () - specify a new ID for the stream / get the stream ID
getNameStream () - get the name of the current stream
getChat () - initializes the chat by the current ID (if necessary, returns the chat ID)
getMessages () - gets new messages from the current chat

## RUS
# Компиляция
1. Скачайте исходный код и загрузите, к примеру, в Eclipse.
2. Добавьте External Jars в проект: [Серверное ядро](https://getbukkit.org/download/craftbukkit) 1.12-1.14.
3. Теперь у Вас есть код для редактирования!

# Установка на сервер
1. Скачайте скомпилированный [StreamInfoYouTube](https://github.com/Dseym/streamInfoYouTube/releases/download/streamInfoYouTube/streamInfoYouTube.jar) и загрузите на свой сервер.
2. Веселитесь!

# Инструкции
1. Получите свой [googleAPI](https://console.developers.google.com/apis), [инструкции](https://rapidapi.com/blog/how-to-get-youtube-api-key/)
2. Создайте папку в plugins с названием StreamInfoYoutube
3. Создайте файл config.yml в папке
4. И напишите в файл - api: googleAPI

# API
Создайте экземпляр класса YouTube(videoID)
videoID - это ID стрим с ютуба watch?v=ID (MVXja46rOOg)
Методы класса:
setVideoID(ID) / getVideoID() - указать новое ID для стрима / получить ID стрима
getNameStream() - получить название текущего стрима
getChat() - инициализирует чат по текущему ID (если понадобится, возвращает ID чата)
getMessages() - получает новые сообщения из текущего чата

Для свободного использование.