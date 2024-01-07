setup:
	@make build
	@make upd
build:
	docker-compose build

up:
	docker-compose up

upd:
	docker-compose up -d

down:
	docker-compose down

stop:
	docker-compose stop

status:
	docker-compose ps

start:
	docker exec -i debian_slim bash -c "mvn spring-boot:run"

clean:
	docker exec -i debian_slim bash -c "mvn clean"

stop:
	docker-compose stop

console:
	docker exec -it debian_slim /bin/bash