SD=src/
BD=bin/
CC=javac -d $(BD) -classpath $(SD)


all: $(BD)Speaker.class $(BD)PlayList.class $(BD)Player.class $(BD)InputHandler.class

$(BD)Speaker.class: $(SD)Speaker.java
	$(CC) $(SD)Speaker.java

$(BD)PlayList.class: $(SD)PlayList.java
	$(CC) $(SD)PlayList.java

$(BD)Player.class: $(SD)Player.java
	$(CC) $(SD)Player.java

$(BD)InputHandler.class: $(SD)InputHandler.java
	$(CC) $(SD)InputHandler.java

clean:
	rm bin/*

