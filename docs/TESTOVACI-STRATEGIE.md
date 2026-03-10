# Testovací strategie

## Cíle pokrytí (obhajitelné)

- **Line coverage:** ≥ 70 %
- **Branch coverage:** ≥ 50 %

Výjimky z měření (v JaCoCo check):

- `ToolRentalApplication` – pouze vstupní bod, netestujeme běh Spring Boot.
- `api.dto.**` – DTO bez logiky.
- Rozhraní `*Repository` – implementace Spring Data, testována integračně přes DB.

## Skladba testů

### Jednotkové testy (unit)

- **RentalStateTransitionTest** – přechody stavů (RESERVED→ACTIVE→RETURNED, RESERVED→CANCELLED), nepovolené přechody. Doménová třída `Rental`.
- **RentalPriceCalculationTest** – výpočet ceny (denní sazba × dny, pozdní poplatek). `RentalPriceCalculator`.
- **OverlapCheckerTest** – překrývání intervalů. `OverlapChecker`.
- **RentalServiceTest** – služba s **mockovanými** repozitáři: vytvoření rezervace (bez kolize / s kolizí), aktivace jen zaměstnancem, vrácení jen zaměstnancem a dopočítání ceny, zrušení vlastní/cizí rezervace.

Testy jsou psány podle **FIRST** (Fast, Independent, Repeatable, Self-validating, Timely) a struktury **AAA** (Arrange, Act, Assert).

### Integrační testy

- **RentalControllerIT** – plný kontext Spring Boot, H2 DB. Scénáře: POST rezervace → 201, validace (chybějící userId) → 400, kolize období → 400. Ověřuje vrstvy controller → service → DB.

### Mocking (test doubles)

- V **RentalServiceTest** jsou použity **mocky** (Mockito): `RentalRepository`, `UserRepository`, `ToolRepository`. Důvod: testovat pouze logiku služby bez závislosti na DB; testy jsou rychlé a deterministické.
- V integračních testech se nemockuje DB – používá se reálná H2 v paměti.

## TDD a refaktoring

Klíčová doménová logika (stavové přechody, výpočet ceny, kontrola překrývání, pravidla ve službě) byla navržena s ohledem na testovatelnost: nejdřív test (red), pak implementace (green), případně refaktoring. V historii commitů by měly být vidět kroky odpovídající TDD a refaktoringu (min. 20 smysluplných commitů).
