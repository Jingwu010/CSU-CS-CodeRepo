<<COMMENT
Note:
- This script is useful to students who are testing their code from machines outside of CS department in order to terminate all Client processes.

Usage:
- ./stop-clients.sh
- This script must be places at <project_root_directory>

How it works:
- This script is used to stop all Client processes. 
- This script depends on following things
   - 'machine_list' file
- This script will remotely login to machines in 'machine_list' and kill all java processes using 'killall java'
- PLEASE NOTE: THIS WILL KILL ALL JAVA PROCESSES STARTED BY USER WHO IS RUNNING THIS COMMAND. It might kill your IDE (Eclipse/Netbeans) also.
So please don't include the machine on which you are working into 'machine_list'

COMMENT
for i in `cat machine_list`
do
	ssh $i "killall -u $USER java"
done
