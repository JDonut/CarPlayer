SD=src/
BD=bin/
TD=tests/
CC=javac -d $(BD)


#Project building

all: manifest $(BD)Speaker.class $(BD)PlayList.class $(BD)Player.class $(BD)InputHandler.class

jar: all
	cd bin; jar cfm ../CarPlayer.jar manifest.txt .

manifest: $(BD)manifest.txt
	echo "Main-Class: InputHandler" > bin/manifest.txt


#Tests

$(BD)MpgProxyTest.class: $(TD)MpgProxyTest.java $(SD)Mpg123Proxy.java
	javac -sourcepath $(SD) -d $(BD) $(TD)MpgProxyTest.java


#Individual class compiling

$(BD)Mpg123Proxy.class: $(SD)Mpg123Proxy.java
	$(CC) $(SD)Mpg123Proxy.java

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
