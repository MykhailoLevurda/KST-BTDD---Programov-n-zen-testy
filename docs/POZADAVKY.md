# Semestrální práce – analýza požadavků

## 1) Cíl práce

- Navrhnout, implementovat a obhájit vlastní softwarovou aplikaci.
- **Prokazatelně** použít proces **TDD** během vývoje.
- Projekt řízen pomocí **Gitu**, automatizované ověřování přes **CI/CD pipeline**.
- Výsledek = doložitelný proces (testy, historie commitů, pipeline, refaktoring), ne jen „hotová aplikace“.

---

## 2) Volba tématu (doména)

- Doménu volí student (po dohodě).
- Aplikace musí mít **smysluplnou doménovou logiku** – ne jen kalkulačka nebo CRUD bez pravidel.
- Inspirace: rezervace, půjčovna, mini e-shop, evidence úkolů/habit tracker, správa kurzů.

---

## 3) Povinný rozsah funkcionality

### 3.1 Doménový model a pravidla

| Požadavek | Minimální rozsah |
|-----------|------------------|
| **Entity** | Minimálně **3** doménové entity (např. User, Order, Item). |
| **Vztahy** | Alespoň **1** vztah **1:N** nebo **N:M**. |
| **Business pravidla** | Minimálně **5 netriviálních** pravidel, např.: |
| | • validace stavového přechodu (např. objednávku nelze „doručit“ před „zaplaceno“), |
| | • výpočet ceny se slevami/pravidly, |
| | • kontrola kapacity/kolizí rezervací, |
| | • omezení rolí (kdo co smí), |
| | • idempotence / zabránění duplicitě. |

### 3.2 Rozhraní aplikace

- Typ: **konzolová / grafická / webová aplikace** nebo **služba se srozumitelným API** (typicky REST).
- **Validace vstupů** a **konzistentní error handling** (smysluplné HTTP kódy / chybové zprávy).

### 3.3 Perzistence a prostředí

- Použití **databáze** (např. relační přes ORM).
- Pro vývoj může být in-memory, pro integraci/produkci doporučená „reálná“.
- Aplikace musí jít **spustit dle návodu** (lokálně i v CI).

---

## 4) Povinné techniky a kvalita

### 4.1 TDD (povinné)

- Klíčová doménová logika vzniká cyklem **red–green–refactor**.
- Testy psány podle zásad **FIRST** a struktury **AAA** (Arrange, Act, Assert).
- Součástí práce je **refaktoring** (viditelný v historii a v kvalitě kódu).

### 4.2 Testy – minimální skladba (povinné)

| Typ | Požadavek |
|-----|-----------|
| **Jednotkové testy** | Pokrývají business pravidla a hraniční stavy. |
| **Integrační testy** | Ověřují integraci vrstev (např. controller–service–DB) v realistickém prostředí. |
| **Mocking / test doubles** | Doložené použití (mock/stub/fake/spy) tam, kde to dává smysl (externí služba, čas, e-mail notifikace). |

### 4.3 Code coverage (povinné měření)

- V projektu **zapnuté měření pokrytí** (např. JaCoCo).
- Výsledek **viditelný v CI**.
- Student **stanoví a obhájí** vlastní cíle (doporučení: např. ≥ 70 % line, ≥ 50 % branch).
- Vysvětlit **výjimky** – co a proč netestuje.

---

## 5) Git – povinné požadavky

- Práce v repozitáři (Git) s **průběžnými commity**.
- Minimálně **20 smysluplných commitů** (žádný „big-bang“ na konci).
- V historii **čitelné kroky odpovídající TDD** (ideálně i „refactor“ commity).

---

## 6) CI/CD – povinné požadavky

Pipeline (např. **GitHub Actions** / **GitLab CI**) musí automaticky provést:

1. **Build**
2. **Spuštění unit a integration testů**
3. **Vyhodnocení code coverage** a **publikace reportu jako artefaktu**

*Volitelné (bonus):* artefakt (image), automatické nasazení do test/prod.

---

## 7) Dokumentace a odevzdání

Odevzdává se **odkaz na repozitář** + **dokumentace** (README nebo `/docs`), minimálně:

- Stručný popis **domény a funkcí**.
- **Jak projekt spustit lokálně** (včetně testů).
- Popis **architektury** (vrstvy, hlavní komponenty).
- **Testovací strategie** (co je unit/integration/BDD, co se mockuje a proč).

---

## Checklist – povinné části (splněno / krok)

| # | Povinnost | Stav |
|---|-----------|------|
| 1 | 3+ entity, 1+ vztah (1:N nebo N:M) | ✅ User, Tool, Rental; User 1:N Rental, Tool 1:N Rental |
| 2 | 5+ netriviálních business pravidel | ✅ stavy, cena, kapacity/kolize, role, duplicity |
| 3 | Rozhraní (REST/konzole/GUI) + validace + error handling | ✅ REST, @Valid, GlobalExceptionHandler → 400 |
| 4 | Databáze (ORM), spustitelnost dle návodu | ✅ JPA/H2, README + mvn verify / spring-boot:run |
| 5 | TDD (red–green–refactor), testy FIRST + AAA | ✅ doména a služba pokryty testy v AAA |
| 6 | Unit testy (pravidla, hraniční stavy) | ✅ RentalStateTransitionTest, Price, Overlap, RentalServiceTest |
| 7 | Integrační testy (vrstvy) | ✅ RentalControllerIT |
| 8 | Mocking / test doubles doloženy | ✅ RentalServiceTest – mocky repozitářů |
| 9 | Code coverage zapnuto, cíle stanoveny, report v CI | ✅ JaCoCo, 70 % line / 50 % branch, artefakt v CI |
| 10 | ≥ 20 smysluplných commitů, TDD v historii | ⏳ provést průběžné commity |
| 11 | CI: build, testy, coverage artefakt | ✅ GitHub Actions: verify + upload jacoco-report |
| 12 | README/docs: doména, spuštění, architektura, testovací strategie | ✅ README + docs/ |
