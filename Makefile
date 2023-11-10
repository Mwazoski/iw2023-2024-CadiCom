build:
	sudo docker-compose build
	@make up

up:
	sudo docker-compose up

down:
	sudo docker-compose down

status:
	sudo docker-compose ps

console:
	sudo docker exec -it debian_slim /bin/bash