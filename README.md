# Půjčovna nářadí (Tool Rental)

Semestrální práce – vývoj aplikace s využitím TDD/BDD/ATDD, Git a CI/CD.

## Doména a funkce

Aplikace slouží k **správě rezervací a půjček nářadí**. Uživatelé (zákazníci) vytvářejí rezervace, zaměstnanci potvrzují půjčku a evidují vrácení. Platí pravidla stavů, kapacity, rolí a výpočtu ceny včetně pozdních poplatků.

- **Entity:** User (zákazník/zaměstnanec), Tool (nářadí), Rental (půjčka).
- **Vztahy:** User 1:N Rental, Tool 1:N Rental.
- **Funkce:** vytvoření rezervace, aktivace půjčky (zaměstnanec), vrácení a dopočítání ceny, zrušení rezervace.

Detailní popis domény a pravidel je v [docs/DOMENA-A-MODEL.md](docs/DOMENA-A-MODEL.md). Přehled požadavků semestrální práce v [docs/POZADAVKY.md](docs/POZADAVKY.md).

## Jak projekt spustit lokálně
(Odkaz na repo: https://github.com/MykhailoLevurda/KST-BTDD---Programov-n-zen-testy.git )

### Požadavky

- **Java 17**
- **Maven 3.8+** (nebo [Maven Wrapper](https://maven.apache.org/wrapper/) – v projektu lze vygenerovat přes `mvn wrapper:wrapper`)

### Build a testy

```bash
# build včetně testů a JaCoCo reportu
mvn verify

# pouze testy
mvn test

# spuštění aplikace (H2 in-memory)
mvn spring-boot:run
```

Po spuštění aplikace:

- API: `http://localhost:8080/api/` (např. `GET /api/users`, `GET /api/tools`, `POST /api/rentals/reservations`)
- H2 konzole (volitelně): `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:rental`)

### Coverage

Report po `mvn test` (nebo `mvn verify`):  
`target/site/jacoco/index.html`

V CI je report publikován jako artefakt (viz níže). Cíle pokrytí: **line ≥ 70 %**, **branch ≥ 50 %**; výjimky (např. hlavní třída, DTO) jsou obhajitelné.

## Architektura

- **api** – REST controllery, DTO, validace vstupů, globální ošetření chyb (HTTP 400 při business/validaci).
- **service** – doménová logika (RentalService): rezervace, aktivace, vrácení, zrušení; aplikuje pravidla stavů, kapacity, rolí.
- **domain** – entity a pravidla nezávislé na DB (RentalStatus, UserRole, Rental přechody stavů, RentalPriceCalculator, OverlapChecker).
- **persistence** – JPA entity (EntityUser, EntityTool, EntityRental), Spring Data JPA repozitáře; běh na H2 (in-memory pro vývoj/testy).

Aplikace je postavená na **Spring Boot 3** (Web, Data JPA, Validation), databáze H2.

## Testovací strategie

| Typ | Co testuje | Nástroje |
|-----|------------|----------|
| **Unit** | Business pravidla a hraniční stavy (stavové přechody, výpočet ceny, překrývání období, pravidla v RentalService) | JUnit 5, AssertJ |
| **Unit s mocky** | RentalService s mockovanými repozitáři (aktivace jen zaměstnancem, kolize, zrušení cizí rezervace) | Mockito |
| **Integrační** | Celý tok controller → service → DB (vytvoření rezervace, validace 400, kolize 400) | @SpringBootTest, MockMvc |

**Co se mockuje a proč:** V unit testech služby mockujeme repozitáře, abychom testovali pouze logiku služby bez DB. V integračních testech DB používáme (H2 in-memory), mockujeme pouze externí systémy (v této aplikaci žádné – např. e-mail služba by se mockovala).

## CI/CD

Pipeline (GitHub Actions) při push/PR na `main`/`master`:

1. **Build** – `mvn verify`
2. **Unit a integrační testy** – součást `mvn verify`
3. **Code coverage** – JaCoCo report se nahraje jako **artefakt** `jacoco-report` (složka `target/site/jacoco/`), retention 30 dní.

Workflow: [.github/workflows/ci.yml](.github/workflows/ci.yml).

## Dokumentace

- [docs/POZADAVKY.md](docs/POZADAVKY.md) – rozepsané požadavky semestrální práce a checklist.
- [docs/DOMENA-A-MODEL.md](docs/DOMENA-A-MODEL.md) – doména, entity, vztahy a business pravidla.

## Licence

V rámci semestrální práce (KST/BTDD).
