# Krok 1: Dockerfile – build a run (krok za krokem)

Přesný postup pro sestavení a spuštění image aplikace **tool-rental** v Dockeru.

---

## Co je v projektu

- **`Dockerfile`** (kořen projektu) – dvoustupňový build: Maven sestaví JAR, runtime image jen JRE + JAR, běh pod ne-root uživatelem, HEALTHCHECK na `/api/users`.
- **`.dockerignore`** – zmenší kontext buildu (vynechá `target/`, `.git`, docs atd.).

---

## Předpoklady

- Nainstalovaný **Docker** (Docker Desktop na Windows, nebo Docker Engine).
- V terminálu ověř: `docker --version` a `docker run hello-world` (volitelně).

---

## Krok A: Build image

1. Otevři terminál a přejdi do **kořene projektu** (tam, kde je `pom.xml` a `Dockerfile`):

   ```powershell
   cd "C:\Users\Levur\BTDD\KST-BTDD---Programov-n-zen-testy"
   ```

2. Spusť build image (tečka na konci = aktuální adresář jako kontext):

   ```powershell
   docker build -t tool-rental:latest .
   ```

   - `-t tool-rental:latest` = název a tag image.
   - První běh stáhne base image a závislosti Maven, může trvat několik minut.

3. Ověř, že image existuje:

   ```powershell
   docker images tool-rental
   ```

   Měl bys vidět řádek s `tool-rental`, `latest` a velikostí.

---

## Krok B: Spuštění kontejneru

1. Spusť kontejner z image `tool-rental:latest`:

   ```powershell
   docker run --rm -p 8080:8080 --name tool-rental-app tool-rental:latest
   ```

   - `--rm` = po zastavení kontejner smaže.
   - `-p 8080:8080` = mapování portu (host 8080 → kontejner 8080).
   - `--name tool-rental-app` = jméno kontejneru (pro přehled).

2. Počkej, až v logu uvidíš něco jako „Started ToolRentalApplication“ (Spring Boot naběhl).

3. V prohlížeči nebo v druhém terminálu otestuj API:

   ```powershell
   curl http://localhost:8080/api/users
   ```

   Nebo v prohlížeči: **http://localhost:8080/api/users**  
   Měl bys dostat JSON s uživateli (z `data.sql`).

4. Kontejner zastavíš: v terminálu, kde běží, stiskni **Ctrl+C**.

---

## Krok C: Ověření healthchecku

1. Spusť kontejner na pozadí:

   ```powershell
   docker run -d -p 8080:8080 --name tool-rental-app tool-rental:latest
   ```

2. Po chvíli (až aplikace naběhne) zkontroluj stav včetně health:

   ```powershell
   docker ps
   docker inspect --format='{{.State.Health.Status}}' tool-rental-app
   ```

   Po startu by měl být stav **health** nejdřív `starting`, pak **healthy**.

3. Zastavení a odstranění kontejneru:

   ```powershell
   docker stop tool-rental-app
   docker rm tool-rental-app
   ```

   (Pokud jsi předtím použil `docker run --rm`, kontejner se po `docker stop` sám smaže.)

---

## Krok D: Běh pod ne-root uživatelem (ověření)

V Dockerfile je nastavený uživatel `app` (uid 1000). Ověř to v běžícím kontejneru:

```powershell
docker run --rm -p 8080:8080 --name tool-rental-app -d tool-rental:latest
docker exec tool-rental-app whoami
docker exec tool-rental-app id
docker stop tool-rental-app
```

Výstup by měl být `app` a uid 1000 (ne root).

---

## Shrnutí příkazů

| Akce | Příkaz |
|------|--------|
| Build image | `docker build -t tool-rental:latest .` |
| Seznam image | `docker images tool-rental` |
| Spustit (foreground) | `docker run --rm -p 8080:8080 --name tool-rental-app tool-rental:latest` |
| Spustit (na pozadí) | `docker run -d -p 8080:8080 --name tool-rental-app tool-rental:latest` |
| Zastavit kontejner | `docker stop tool-rental-app` |
| Stav + health | `docker ps` a `docker inspect --format='{{.State.Health.Status}}' tool-rental-app` |

---

## Časté problémy

- **Port 8080 už použit:** Zavři jinou aplikaci na 8080 nebo použij jiný port:  
  `docker run --rm -p 9090:8080 --name tool-rental-app tool-rental:latest`  
  Pak testuj na **http://localhost:9090/api/users**.

- **Build padá na Maven:** Zkontroluj, že v adresáři je `pom.xml` a složka `src/`. `.dockerignore` nesmí vynechávat `pom.xml` ani `src/`.

- **Aplikace v kontejneru neodpovídá:** Počkej cca 30–60 s po startu (Spring Boot + H2). Pak zkus znovu `curl http://localhost:8080/api/users`.

Po úspěšném buildu a běhu máš Krok 1 hotový. Další je **Krok 2: docker-compose** (aplikace + DB v jednom příkazu).
