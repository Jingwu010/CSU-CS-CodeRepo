<<COMMENT
Note:
- Placeholders are denoted by []; they should be replaced by according values.
- This script is useful to students who are testing their code from any of the CS deapartment machines.
- This script must be placed at <project_root_directory>

Usage:
- ./test-scalability.sh <number_of_window>

How it works:
- This script is used to start multiple Client processes. 
- This script depends on following 2 things
   - 'machine_list' file
   - Command-line argument given when running this script.
- This script starts (number_of_machines_in_machine_list * number_of_window) Client processes.
- For ex, your 'machine_list' file has 20 hosts and when you run './test-scalability.sh 5', this script starts 20*5 = 100 Client processes.
   - There will be <number_of_window> windows
   - Each window will have <number_of_machines_in_machine_list> tabs
- Every Client process will be started by remotely logging into a machine and running 'java' command
- If you close a window/tab, associated java process will automatically gets killed.
COMMENT

CLASSES=Desktop/cs455
SCRIPT="cd $CLASSES;
java -cp . cs455.scaling.client.Client $2 1234 5 $3"

#$1 is the command-line argument
for ((j=1; j<=$1; j++));
do
	COMMAND='gnome-terminal'
	for i in `cat machine_list`
	do
		echo 'logging into '$i
		OPTION='--tab -e "ssh -t '$i' '$SCRIPT'"'
		COMMAND+=" $OPTION"
	done
	eval $COMMAND &
done
