# Postman – návod krok za krokem (demo aplikace)

Aplikace musí být **spuštěná** (`mvn spring-boot:run`). Po startu jsou v databázi 2 uživatelé a 2 nářadí (viz `data.sql`). Základní adresa: **http://localhost:8080**.

---

## Příprava

1. Spusť aplikaci: v kořeni projektu v terminálu zadej `mvn spring-boot:run` a počkej, až se objeví něco jako „Started ToolRentalApplication“.
2. Otevři **Postman** (nebo jiný klient pro HTTP).
3. Všechny požadavky posílej na **http://localhost:8080** (před cestu endpointu).

---

## Krok 1: Seznam uživatelů (GET)

- **Metoda:** GET  
- **URL:** `http://localhost:8080/api/users`  
- **Body:** žádné (u GET se nepoužívá).  
- **Odpověď:** JSON pole se dvěma uživateli.  
  - První: id **1**, name „Jan Novak“, role **CUSTOMER** (zákazník).  
  - Druhý: id **2**, name „Marie Zamestnankyne“, role **EMPLOYEE** (zaměstnanec).  
- **Účel:** Ověříš, že API běží a že v DB jsou uživatelé. Pro rezervaci použiješ **userId 1**, pro aktivaci a vrácení **userId 2**.

---

## Krok 2: Detail jednoho uživatele (GET)

- **Metoda:** GET  
- **URL:** `http://localhost:8080/api/users/1`  
- **Body:** žádné.  
- **Odpověď:** Jeden objekt uživatele (id 1, Jan Novak, CUSTOMER).  
- **Účel:** Ukázka endpointu pro detail podle id.

---

## Krok 3: Seznam nářadí (GET)

- **Metoda:** GET  
- **URL:** `http://localhost:8080/api/tools`  
- **Body:** žádné.  
- **Odpověď:** JSON pole se dvěma nářadími.  
  - První: id **1**, name „Kladivo“, dailyPriceCzk 100, lateFeePerDay 50.  
  - Druhý: id **2**, name „Sroubovak“, atd.  
- **Účel:** Ověříš nářadí. Pro rezervaci použiješ **toolId 1** (kladivo).

---

## Krok 4: Detail jednoho nářadí (GET)

- **Metoda:** GET  
- **URL:** `http://localhost:8080/api/tools/1`  
- **Body:** žádné.  
- **Odpověď:** Jeden objekt nářadí (id 1, Kladivo, ceny).  
- **Účel:** Ukázka detailu nářadí.

---

## Krok 5: Vytvoření rezervace (POST)

- **Metoda:** POST  
- **URL:** `http://localhost:8080/api/rentals/reservations`  
- **Headers:**  
  - **Content-Type:** `application/json`  
- **Body:** vyber **raw** a **JSON**, napiš:

```json
{
  "userId": 1,
  "toolId": 1,
  "startDate": "2025-04-01",
  "endDate": "2025-04-05"
}
```

- **Odpověď:** Status **201 Created**. V těle je vytvořená půjčka (rental): **id** (např. 1), status **RESERVED**, user, tool, startDate, endDate.  
- **Důležité:** Z odpovědi si **zkopíruj `id`** vytvořené půjčky (např. **1**). Budeš ho potřebovat v dalších krocích jako **rentalId**.  
- **Účel:** Zákazník (userId 1) rezervuje kladivo (toolId 1) na 1.–5. 4. 2025.

---

## Krok 6: Aktivace rezervace – předání nářadí (POST)

- **Metoda:** POST  
- **URL:**  
  `http://localhost:8080/api/rentals/1/activate?employeeUserId=2`  
  - Místo **1** napiš **rentalId** z předchozího kroku (pokud měla půjčka jiné id, např. 2, použij to).  
  - **employeeUserId=2** = zaměstnanec (Marie) potvrzuje předání.  
- **Body:** žádné.  
- **Odpověď:** Status **200 OK**. V těle je půjčka se stavem **ACTIVE**.  
- **Účel:** Zaměstnanec aktivuje rezervaci (nářadí je „vypůjčené“).

---

## Krok 7: Vrácení nářadí a dopočtení ceny (POST)

- **Metoda:** POST  
- **URL:**  
  `http://localhost:8080/api/rentals/1/return?employeeUserId=2&actualReturnDate=2025-04-05`  
  - **1** = stejné rentalId jako v kroku 6.  
  - **employeeUserId=2** = zaměstnanec eviduje vrácení.  
  - **actualReturnDate=2025-04-05** = vráceno v plánovaný den (bez pozdního poplatku).  
- **Body:** žádné.  
- **Odpověď:** Status **200 OK**. V těle je půjčka se stavem **RETURNED**, **actualReturnDate** a **totalPriceCzk** (např. 400 = 4 dny × 100 Kč).  
- **Účel:** Ukázka vrácení a výpočtu ceny.

---

## Krok 8 (volitelný): Pozdní vrácení – pozdní poplatek

- Nejprve znovu vytvoř rezervaci (Krok 5), ale s jinými daty, např.:  
  `"startDate": "2025-04-01", "endDate": "2025-04-03"` (2 dny).  
- Aktivuj ji (Krok 6) se stejným rentalId z odpovědi.  
- U vrácení (Krok 7) nastav **actualReturnDate** na později než konec, např.:  
  `http://localhost:8080/api/rentals/2/return?employeeUserId=2&actualReturnDate=2025-04-06`  
- V odpovědi by měla být **totalPriceCzk** vyšší (základ 2×100 + např. 3×50 pozdní = 350 Kč).  
- **Účel:** Ukázka business pravidla „pozdní poplatek“.

---

## Krok 9 (volitelný): Zrušení rezervace

- Vytvoř novou rezervaci (Krok 5), např. userId 1, toolId 2, nějaké datum.  
- **Neaktivuj** ji.  
- Zrušení:  
  - **Metoda:** POST  
  - **URL:**  
    `http://localhost:8080/api/rentals/3/cancel?userId=1`  
    (3 = rentalId z odpovědi vytvoření, 1 = zákazník Jan – jen vlastník může zrušit.)  
- **Odpověď:** 200 OK, stav půjčky **CANCELLED**.  
- **Účel:** Ukázka zrušení vlastní rezervace zákazníkem.

---

## Krok 10 (volitelný): Chyba – kolize

- Zkus vytvořit **druhou** rezervaci na **stejné nářadí** (toolId 1) ve **stejném období** (např. 2025-04-01 až 2025-04-05), jak už jednou existuje.  
- **Metoda:** POST  
- **URL:** `http://localhost:8080/api/rentals/reservations`  
- **Body:** stejný JSON jako v Kroku 5 (userId 1, toolId 1, 2025-04-01, 2025-04-05).  
- **Odpověď:** **400 Bad Request**, v těle zpráva o kolizi („Nářadí je v tomto období již rezervované nebo zapůjčené …“).  
- **Účel:** Ukázka business pravidla kolize.

---

## Krok 11 (volitelný): Chyba – validace

- Pošli vytvoření rezervace **bez povinného pole**, např. bez `userId`:  
  `{"toolId": 1, "startDate": "2025-04-01", "endDate": "2025-04-05"}`  
- **Odpověď:** **400 Bad Request**, zpráva o validaci (např. „userId je povinný“).  
- **Účel:** Ukázka validace vstupu.

---

## Shrnutí pořadí pro rychlé demo

1. **GET** `/api/users` → ověření uživatelů.  
2. **GET** `/api/tools` → ověření nářadí.  
3. **POST** `/api/rentals/reservations` s JSON tělem → vytvoření rezervace, poznamenej si **id** půjčky.  
4. **POST** `/api/rentals/{id}/activate?employeeUserId=2` → aktivace (zaměstnanec).  
5. **POST** `/api/rentals/{id}/return?employeeUserId=2&actualReturnDate=2025-04-05` → vrácení a cena.  

Tím máš kompletní tok: rezervace → aktivace → vrácení a výpočet ceny. V Postmanu můžeš každý požadavek uložit do kolekce a spouštět je po pořadí.
