# Krok 2: Docker Compose – aplikace + databáze

Aplikace a PostgreSQL se spouštějí jedním příkazem. Heslo k DB není v repozitáři – nastavíš ho v souboru `.env`.

---

## Co je v projektu

- **`docker-compose.yml`** – služby `db` (PostgreSQL 15) a `app` (build z Dockerfile). Aplikace čeká na zdravou DB a připojí se přes proměnné prostředí.
- **`.env.example`** – vzor pro `.env` (heslo). Zkopíruj na `.env` a doplň hodnotu.
- **`.env`** – v `.gitignore`, do repozitáře se necommituje (bezpečnost).
- **`pom.xml`** – přidaný runtime driver **PostgreSQL** (kromě H2).

---

## Příprava před prvním spuštěním

1. V kořeni projektu vytvoř `.env` z příkladu:

   ```powershell
   copy .env.example .env
   ```

2. Otevři `.env` a nastav heslo (pro lokální vývoj stačí např. `devpassword`):

   ```
   POSTGRES_PASSWORD=devpassword
   ```

---

## Spuštění

V kořeni projektu (tam, kde je `docker-compose.yml`):

```powershell
docker compose up --build
```

- `--build` – před startem znovu sestaví image aplikace.
- Logy z obou služeb jdou do jednoho terminálu. Aplikace naběhne po zdravé DB (řádky typu „Started ToolRentalApplication“).

Spuštění na pozadí:

```powershell
docker compose up -d --build
```

---

## Ověření

1. **API:** v prohlížeči nebo curl:
   ```powershell
   curl http://localhost:8080/api/users
   ```
   Měl bys dostat JSON s uživateli (z `data.sql`).

2. **Stav kontejnerů:**
   ```powershell
   docker compose ps
   ```

---

## Zastavení

```powershell
docker compose down
```

Data v PostgreSQL zůstávají v Docker volume `postgres_data`. Při dalším `docker compose up` se DB znovu použije (aplikace má `create-drop`, takže při každém startu app se schéma znovu vytvoří a načte `data.sql`).

Smazání včetně dat v DB:

```powershell
docker compose down -v
```

---

## Shrnutí příkazů

| Akce | Příkaz |
|------|--------|
| První příprava | `copy .env.example .env` a nastavit `POSTGRES_PASSWORD` v `.env` |
| Spustit (foreground) | `docker compose up --build` |
| Spustit (na pozadí) | `docker compose up -d --build` |
| Stav služeb | `docker compose ps` |
| Zastavit | `docker compose down` |
| Zastavit a smazat data | `docker compose down -v` |
