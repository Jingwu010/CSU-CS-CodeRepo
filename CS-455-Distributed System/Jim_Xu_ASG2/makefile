all: compile
	@echo -e '[INFO] Done!'
clean:
	@echo -e '[INFO] Cleaning Up..'	
	@-rm -rf cs455/**/**/*.class

compile: 
	@echo -e '[INFO] Compiling the Source..'
	@javac cs455/**/**/*.java -Xlint:unchecked
open:
	@open cs455/scaling/**/*.java
server:
	@java cs455.scaling.server.Server 1234 10
client:
	
