# Volba domény a doménový model

## Zvolená doména: Půjčovna nářadí (Tool Rental)

Systém pro evidenci nářadí, rezervací a půjček. Uživatelé rezervují nářadí, zaměstnanci zapůjčí a přijímají vrácení. Platí pravidla stavů, kapacity a cen.

---

## Doménové entity (min. 3)

| Entita | Popis |
|--------|--------|
| **User** | Uživatel systému – zákazník nebo zaměstnanec. Id, jméno, role (CUSTOMER, EMPLOYEE), kontakt. |
| **Tool** | Půjčované nářadí. Id, název, denní cena, dostupnost. |
| **Rental** | Půjčka – vazba mezi User a Tool na období. Id, uživatel, nářadí, datum začátku/konce, stav, vypočtená cena. |

## Vztahy (min. 1)

- **User 1 : N Rental** – jeden uživatel může mít více půjček (v čase).
- **Tool 1 : N Rental** – jedno nářadí může mít více půjček (po sobě jdoucích).

---

## Netriviální business pravidla (min. 5)

1. **Validace stavového přechodu**  
   Rental může mít stavy: `RESERVED` → `ACTIVE` → `RETURNED` nebo `CANCELLED`.  
   Nelze přejít např. z `RESERVED` na `RETURNED` bez `ACTIVE`; nelze „vrátit“ již vrácenou půjčku.

2. **Výpočet ceny**  
   Cena = denní sazba × počet dnů. Při pozdním vrácení (po plánovaném konci) přičíst X Kč/den jako pozdní poplatek.

3. **Kontrola kapacity / kolize**  
   Stejné nářadí (Tool) nesmí mít dvě půjčky s překrývajícími se intervaly (datum od–do). Při vytvoření rezervace nebo aktivaci půjčky se kontroluje kolize.

4. **Omezení rolí**  
   Pouze uživatel s rolí `EMPLOYEE` smí: aktivovat rezervaci na půjčku (`RESERVED` → `ACTIVE`), evidovat vrácení (`ACTIVE` → `RETURNED`). Zákazník smí vytvořit rezervaci a zrušit vlastní rezervaci.

5. **Idempotence / zabránění duplicitě**  
   Pro stejného uživatele a stejné nářadí v daném časovém rozmezí nesmí existovat druhá rezervace (nebo aktivní půjčka) – zabránění dvojité rezervaci stejné věci na stejné období.

---

## Stavový diagram Rental

```
[RESERVED] --(zaměstnanec potvrdí)--> [ACTIVE]
[RESERVED] --(zrušení)--> [CANCELLED]
[ACTIVE]   --(vrátí nářadí)--> [RETURNED]
```

---

## Rozhraní a perzistence (plán)

- **API:** REST (např. Spring Boot nebo jednoduchý Javový HTTP server).
- **Perzistence:** Relační DB přes ORM (JPA/Hibernate); pro testy in-memory (H2), pro běh volitelně H2/SQLite/PostgreSQL.
- **Build:** Maven; testy JUnit 5, JaCoCo pro coverage.
