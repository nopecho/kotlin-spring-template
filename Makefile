BUILD_PATH ?= $(CURDIR)/bootstrap
LOCAL_DOCKER_COMPOSE := $(CURDIR)/local/docker-compose.yml

up:
	docker-compose -f $(LOCAL_DOCKER_COMPOSE) up -d

down:
	docker-compose -f $(LOCAL_DOCKER_COMPOSE) down

build:
	$(CURDIR)/gradlew clean :bootstrap:build

docker: build
	cd $(BUILD_PATH) && \
    	docker build \
    	--tag $(tag) \
    	.

.PHONY: build docker
default: up