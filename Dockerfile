FROM openjdk:17-alpine AS builder

WORKDIR /src/app/tweet-fetch-service
COPY target/tweet-fetch-service.jar tweet-fetch-service.jar

# multi build to reduce down the image size.
#FROM alpine
#WORKDIR /src/app
#COPY --from=builder /src/app/users-mysql/ /src/app/

CMD ["java", "-jar", "tweet-fetch-service.jar"]
