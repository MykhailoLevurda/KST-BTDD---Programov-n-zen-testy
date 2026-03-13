# Prezentace aplikace: Půjčovna nářadí (Tool Rental)

Dokument pro odprezentování semestrální práce před vyučujícím. Obsahuje popis aplikace a podrobnou funkcionalitu.

---

## 1. Název a účel aplikace

**Název:** Půjčovna nářadí (Tool Rental)

**Účel:** Aplikace slouží k **evidenci nářadí, rezervací a půjček**. Umožňuje zákazníkům rezervovat nářadí na konkrétní období a zaměstnancům potvrzovat půjčky a evidovat vrácení včetně výpočtu ceny. Systém vynucuje business pravidla: platné stavy půjčky, kontrolu kapacity (žádné překrývání období), omezení podle rolí a zabránění duplicitním rezervacím.

**Typ:** REST API služba (backend). Aplikace neobsahuje vlastní webové rozhraní; komunikace probíhá přes HTTP endpointy (např. z Postmanu, curl nebo jiného klienta).

---

## 2. Doména a kontext

- **Zákazník (CUSTOMER):** Prohlíží nářadí a uživatele, vytváří rezervace na nářadí na zvolené období, může zrušit **vlastní** rezervaci (ještě neaktivovanou).
- **Zaměstnanec (EMPLOYEE):** Kromě toho může **aktivovat** rezervaci (změnit stav na „půjčka probíhá“) a **evidovat vrácení** včetně data vrácení; systém dopočte cenu včetně pozdního poplatku.
- **Nářadí (Tool):** Má denní cenu a volitelný poplatek za pozdní vrácení (Kč/den). Jedno nářadí nemůže být ve stejném časovém úseku zapůjčeno dvakrát (kontrola kolize).

---

## 3. Doménový model

### 3.1 Entity (min. 3)

| Entita | Popis a atributy |
|--------|------------------|
| **User (Uživatel)** | Id, jméno, role (`CUSTOMER` nebo `EMPLOYEE`). Reprezentuje zákazníka nebo zaměstnance. |
| **Tool (Nářadí)** | Id, název, denní cena v Kč (`dailyPriceCzk`), pozdní poplatek za den v Kč (`lateFeePerDay`, volitelný). |
| **Rental (Půjčka)** | Id, uživatel, nářadí, datum začátku a konce půjčky, stav (`RESERVED` / `ACTIVE` / `RETURNED` / `CANCELLED`), datum skutečného vrácení (po vrácení), celková cena v Kč (vypočtená při vrácení). |

### 3.2 Vztahy (min. 1)

- **User 1 : N Rental** – jeden uživatel může mít více půjček (v čase).
- **Tool 1 : N Rental** – jedno nářadí může mít více půjček (po sobě jdoucích, bez překrývání).

---

## 4. Business pravidla (podrobná funkcionalita)

### 4.1 Validace stavového přechodu

- Půjčka má čtyři stavy: **RESERVED** → **ACTIVE** → **RETURNED**, nebo **RESERVED** → **CANCELLED**.
- **Povolené přechody:**
  - **RESERVED → ACTIVE:** pouze zaměstnanec („aktivace“ = předání nářadí).
  - **RESERVED → CANCELLED:** zákazník může zrušit vlastní rezervaci.
  - **ACTIVE → RETURNED:** pouze zaměstnanec (eviduje vrácení).
- **Nepovolené:** např. z RESERVED přímo na RETURNED, z CANCELLED na ACTIVE, z RETURNED znovu na RETURNED. Při pokusu o nepovolený přechod systém vyhodí výjimku (v API odpoví chybou 400).

### 4.2 Výpočet ceny

- **Základ:** cena = **denní sazba × počet dnů** (dnů od začátku do plánovaného konce).
- **Pozdní vrácení:** pokud je skutečné datum vrácení po plánovaném konci, přičte se za každý den prodlení **pozdní poplatek** (např. 50 Kč/den). Celková cena se uloží do půjčky při operaci „vrácení“.
- Příklad: půjčka 2 dny (200 Kč), vrácení 3 dny po termínu → 200 + 3×50 = 350 Kč.

### 4.3 Kontrola kapacity / kolize

- Pro **stejné nářadí** nesmí existovat dvě půjčky se **překrývajícími se** intervaly (datum od–do).
- Kontrola probíhá při **vytvoření rezervace**: pokud v daném intervalu již existuje rezervace nebo aktivní půjčka pro toto nářadí, rezervace se nevytvoří a API vrátí chybu (např. „Nářadí je v tomto období již rezervované nebo zapůjčené (kolize / duplicita)“).

### 4.4 Omezení rolí

- **Aktivovat** rezervaci (RESERVED → ACTIVE) smí pouze uživatel s rolí **EMPLOYEE**. Zákazník dostane chybu typu „Operaci smí provést pouze zaměstnanec“.
- **Evidovat vrácení** (ACTIVE → RETURNED) smí pouze **EMPLOYEE**.
- **Vytvořit rezervaci** a **zrušit vlastní rezervaci** (RESERVED → CANCELLED) může **CUSTOMER** i EMPLOYEE (zrušení jen u vlastní rezervace).

### 4.5 Idempotence / zabránění duplicitě

- Pro **stejné nářadí** a **překrývající se období** nesmí vzniknout druhá rezervace ani aktivní půjčka. Toto je zajištěno stejnou kontrolou kolize jako v bodě 4.3 – tedy zabránění dvojité rezervaci stejného nářadí na stejné období.

### 4.6 Další pravidla

- **Neplatné období:** pokud `start` nebo `end` chybí, nebo `end` je před `start`, služba vyhodí výjimku („Neplatné období: start a end musí být vyplněny a end >= start“).
- **Zrušení jen vlastní rezervace:** pouze uživatel, který rezervaci vytvořil, ji smí zrušit (ověření podle `userId`). Jinak chyba „Zrušit lze pouze vlastní rezervaci“.
- **Neexistující entita:** pokud se volá operace s neexistujícím userId, toolId nebo rentalId, služba vrátí odpovídající chybovou zprávu (např. „Uživatel neexistuje“, „Půjčka neexistuje“).

---

## 5. REST API – přehled endpointů

Základní URL: `http://localhost:8080/api/` (po spuštění aplikace).

### 5.1 Uživatelé (UserController)

| Metoda | URL | Popis |
|--------|-----|--------|
| GET | `/api/users` | Seznam všech uživatelů. |
| GET | `/api/users/{id}` | Detail uživatele podle id. Odpověď 200 nebo 404. |

### 5.2 Nářadí (ToolController)

| Metoda | URL | Popis |
|--------|-----|--------|
| GET | `/api/tools` | Seznam všech nářadí. |
| GET | `/api/tools/{id}` | Detail nářadí podle id. Odpověď 200 nebo 404. |

### 5.3 Rezervace a půjčky (RentalController)

| Metoda | URL | Popis |
|--------|-----|--------|
| POST | `/api/rentals/reservations` | Vytvoření rezervace. Tělo: JSON s `userId`, `toolId`, `startDate`, `endDate`. Odpověď 201 + vytvořená půjčka, nebo 400 při validaci / business chybě. |
| POST | `/api/rentals/{rentalId}/activate?employeeUserId=...` | Aktivace rezervace (RESERVED → ACTIVE). Pouze zaměstnanec. Odpověď 200 nebo 400. |
| POST | `/api/rentals/{rentalId}/return?employeeUserId=...&actualReturnDate=...` | Eviduje vrácení (ACTIVE → RETURNED), volitelně dopočte cenu podle `actualReturnDate`. Pouze zaměstnanec. Odpověď 200 nebo 400. |
| POST | `/api/rentals/{rentalId}/cancel?userId=...` | Zrušení rezervace (RESERVED → CANCELLED). Jen vlastník rezervace. Odpověď 200 nebo 400. |

### 5.4 Příklad: vytvoření rezervace

**Požadavek:**
```http
POST /api/rentals/reservations
Content-Type: application/json

{
  "userId": 1,
  "toolId": 10,
  "startDate": "2025-04-01",
  "endDate": "2025-04-05"
}
```

**Odpověď při úspěchu (201 Created):** JSON objekt půjčky se stavem `RESERVED`, vyplněnými daty a id.

**Odpověď při chybě (400 Bad Request):** JSON s polem `message` (např. chybějící pole, neplatné období, kolize, neexistující uživatel/nářadí).

---

## 6. Validace vstupů a ošetření chyb

- **Validace vstupu:** U vytvoření rezervace se používá DTO s anotacemi `@NotNull` na `userId`, `toolId`, `startDate`, `endDate`. Při porušení vrátí API **400** a v těle zprávu typu „Validation failed“ s detailem (např. které pole chybí).
- **Business chyby:** Všechny výjimky z business logiky (neexistující entita, kolize, neplatný přechod stavu, omezení role, zrušení cizí rezervace) jsou zachyceny globálním handlerem a vráceny jako **400 Bad Request** s jednotným formátem odpovědi (např. `message`, `error`, `status`, `timestamp`).
- **404:** Pro GET `/api/users/{id}` a GET `/api/tools/{id}` vrací aplikace **404 Not Found**, pokud entita neexistuje.

---

## 7. Technologie a architektura

- **Framework:** Spring Boot 3 (Web, Data JPA, Validation).
- **Databáze:** H2 in-memory (vývoj a testy); konfigurace v `application.yml`.
- **Architektura vrstev:**
  - **api** – REST controllery, DTO, validace, globální výjimkový handler.
  - **service** – `RentalService`: veškerá business logika (rezervace, aktivace, vrácení, zrušení), včetně aplikace pravidel stavů, kapacity, rolí a výpočtu ceny.
  - **domain** – čistá doménová logika nezávislá na DB: enumy `RentalStatus`, `UserRole`, doménový model přechodů stavů (`Rental`), `RentalPriceCalculator`, `OverlapChecker`.
  - **persistence** – JPA entity (`EntityUser`, `EntityTool`, `EntityRental`), Spring Data JPA repozitáře (včetně dotazu pro překrývající se půjčky).

---

## 8. Spuštění a vyzkoušení

- **Požadavky:** Java 17, Maven 3.8+.
- **Build a testy:** `mvn verify` (nebo `mvn test`).
- **Spuštění aplikace:** `mvn spring-boot:run`.
- Po startu:
  - API: `http://localhost:8080/api/` (users, tools, rentals).
  - H2 konzole (volitelně): `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:rental`).

Data se ukládají do paměti; po restartu aplikace začínáte s prázdnou databází. Pro vyzkoušení je potřeba nejdřív vytvořit uživatele a nářadí (např. přes H2 nebo doplnit jednoduché endpointy pro vytvoření – v aktuální verzi lze v dokumentaci uvést, že uživatelé a nářadí se zakládají např. ručně v DB nebo budou doplněny).

*(Pozn.: V aktuálním API chybí endpointy pro vytvoření User/Tool; pro prezentaci lze ukázat GET seznamy a hlavně tok rezervace → aktivace → vrácení za předpokladu, že data už v DB jsou, nebo je doplnit v rámci dalších úkolů.)*

---

## 9. Testování a CI/CD (stručně)

- **Jednotkové testy:** JUnit 5, AssertJ – testují stavové přechody (`Rental`), výpočet ceny (`RentalPriceCalculator`), překrývání intervalů (`OverlapChecker`) a pravidla ve `RentalService` s mockovanými repozitáři (Mockito).
- **Integrační testy:** `RentalControllerIT` – plný Spring kontext, H2; testuje vytvoření rezervace (201), validaci vstupu (400), kolizi (400).
- **Code coverage:** JaCoCo; cíle např. line ≥ 70 %, branch ≥ 50 %; výjimky (hlavní třída, DTO, rozhraní repozitářů) jsou zdokumentované.
- **CI/CD:** GitHub Actions – při pushi na `main`/`master` se spustí build (`mvn verify -Pci`), unit a integrační testy; JaCoCo report se publikuje jako artefakt.

---

## 10. Shrnutí pro prezentaci

- Aplikace **Půjčovna nářadí** je REST API pro evidenci rezervací a půjček s **třemi entitami** (User, Tool, Rental) a **pěti netriviálními business pravidly** (stavové přechody, výpočet ceny a pozdní poplatek, kolize/kapacita, omezení rolí, zabránění duplicitě).
- **Funkcionalita** zahrnuje: vytvoření rezervace s validací a kontrolou kolize, aktivaci rezervace a vrácení pouze zaměstnancem, zrušení vlastní rezervace zákazníkem a dopočítání ceny při vrácení včetně pozdního poplatku.
- **API** je konzistentně ošetřené (validace vstupů, HTTP 400/404, jednotný formát chyb) a pokryté unit a integračními testy s využitím TDD přístupu a CI pipeline na GitHubu.

Tímto dokumentem lze odprezentovat aplikaci včetně podrobné funkcionality před vyučujícím.
