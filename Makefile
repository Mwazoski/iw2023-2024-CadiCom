setup:
	@make build
	@make up
build:
	sudo docker-compose build

up:
	sudo docker-compose up

upd:
	sudo docker-compose up -d

down:
	sudo docker-compose down

status:
	sudo docker-compose ps

start:
	sudo docker exec -i debian_slim bash -c "mvn spring-boot:run"

stop:
	sudo docker-compose stop

console:
	sudo docker exec -it debian_slim /bin/bash