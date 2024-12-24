
start-db:
	sudo sc start MySQL80
	sudo sc start MongoDB

stop-db:
	sudo sc stop MySQL80
	sudo sc stop MongoDB