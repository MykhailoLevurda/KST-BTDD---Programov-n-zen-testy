# ========== Stage 1: Build ==========
# Image s Mavenem + JDK 17 (eclipse-temurin má jen Javu, ne Maven)
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Zkopírovat POM a stáhnout závislosti (cache vrstva)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Zkopírovat zdrojáky a sestavit JAR (bez testů – testy běží v CI)
COPY src ./src
RUN mvn package -DskipTests -B

# ========== Stage 2: Runtime ==========
FROM eclipse-temurin:17-jre-alpine
LABEL maintainer="tool-rental"

# wget pro HEALTHCHECK (Alpine JRE image ho nemá)
RUN apk add --no-cache wget

# Ne-root uživatel (požadavek zadání)
RUN addgroup -g 1000 appgroup && \
    adduser -u 1000 -G appgroup -s /bin/sh -D app

WORKDIR /app

# Zkopírovat JAR z build stage
COPY --from=build /app/target/tool-rental-*.jar app.jar
RUN chown -R app:appgroup /app

USER app

EXPOSE 8080

# Healthcheck: GET /api/users (aplikace odpovídá 200)
HEALTHCHECK --interval=30s --timeout=3s --start-period=45s --retries=3 \
  CMD wget -q -O /dev/null http://127.0.0.1:8080/api/users || exit 1

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
