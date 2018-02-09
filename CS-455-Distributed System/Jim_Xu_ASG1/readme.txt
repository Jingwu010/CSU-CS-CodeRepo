
Jim Xu - 831156383
	
acceptable commands are listed here:

—————————————————————————————————————MessagingNode:——————————————————————————————
register		//#register the node
deregister		//#deregister the node
show-connection		//#show connection built by overlay
print-shortest-path	//#print shortest path calculated by registry
exit-overlay		//#deregister and terminate


———————————————————————————————————————Registry:————————————————————————————————————
list-sockets			//#display all the socket connected with registry
list-messaging nodes		//#display all the nodes registered in registry list
list-weights			//#display the node-to-node distance in given format
setup-overlay <number>		//#create overlay and send overlay to MessagingNode
send-overlay-link-weights	//#randomly create weight to the overlay map
start <number>			//#start n rounds
pull_traffic_summary		//#print traffic summary informations


The program run perfectly in any orders and any machines,
so if you encounter any problems, please email me back by jimx@rams.colostate.edu

Thank you for testing, thank you for the work.
Gook luck to all.