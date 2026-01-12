# -------------------------------------------------------------------
# 1. Build Stage: 빌드 도구가 포함된 무거운 이미지
# -------------------------------------------------------------------
FROM gradle:8-jdk17 AS builder

USER root

WORKDIR /build

COPY build.gradle settings.gradle /build/
RUN gradle dependencies --no-daemon

COPY src /build/src
RUN gradle bootJar --no-daemon

# -------------------------------------------------------------------
# 2. Run Stage: 실행에 필요한 JRE만 포함된 가벼운 이미지
# -------------------------------------------------------------------
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /build/build/libs/*SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]