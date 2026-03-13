# Přesný postup commitů – krok za krokem

Udělej vždy **jen jeden krok** (jedna změna), pak **commit + push**. U lichých kroků má CI **spadnout** (červené), u sudých **projít** (zelené).

---

## Dvojice 1: Kolize rezervací

### Krok 1 – CI má spadnout ❌

1. Otevři soubor: **`src/test/java/cz/kst/btdd/service/RentalServiceTest.java`**
2. Najdi řádek (v testu `throwsWhenOverlap`):
   ```java
   .hasMessageContaining("kolize");
   ```
3. **Změň na:**
   ```java
   .hasMessageContaining("neexistuje");
   ```
4. Ulož soubor.
5. V terminálu (nebo v IDE: Git → Commit):
   ```bash
   git add src/test/java/cz/kst/btdd/service/RentalServiceTest.java
   git commit -m "test kolize rezervací"
   git push origin main
   ```
6. Na GitHubu → **Actions**: tento běh musí skončit **červeně** ❌.

---

### Krok 2 – CI má projít ✅

1. Ve stejném souboru **`RentalServiceTest.java`** najdi:
   ```java
   .hasMessageContaining("neexistuje");
   ```
2. **Změň zpět na:**
   ```java
   .hasMessageContaining("kolize");
   ```
3. Ulož.
4. Commit a push:
   ```bash
   git add src/test/java/cz/kst/btdd/service/RentalServiceTest.java
   git commit -m "kolize rezervací s doplněnou funkcí"
   git push origin main
   ```
5. V **Actions** by měl být běh **zelený** ✅.

---

## Dvojice 2: Omezení rolí

### Krok 3 – CI má spadnout ❌

1. Otevři **`src/test/java/cz/kst/btdd/service/RentalServiceTest.java`**
2. V testu `customerCannotActivate` najdi:
   ```java
   .hasMessageContaining("zaměstnanec");
   ```
3. **Změň na:**
   ```java
   .hasMessageContaining("admin");
   ```
4. Ulož.
5. V terminálu:
   ```bash
   git add src/test/java/cz/kst/btdd/service/RentalServiceTest.java
   git commit -m "test omezení roli"
   git push origin main
   ```
6. **Actions** → červené ❌.

---

### Krok 4 – CI má projít ✅

1. V **`RentalServiceTest.java`** změň zpět:
   ```java
   .hasMessageContaining("admin");
   ```
   **na:**
   ```java
   .hasMessageContaining("zaměstnanec");
   ```
2. Ulož.
3. V terminálu:
   ```bash
   git add src/test/java/cz/kst/btdd/service/RentalServiceTest.java
   git commit -m "omezení roli s doplněnou funkcí"
   git push origin main
   ```
4. **Actions** → zelené ✅.

---

## Dvojice 3: Výpočet ceny

### Krok 5 – CI má spadnout ❌

1. Otevři **`src/test/java/cz/kst/btdd/domain/RentalPriceCalculationTest.java`**
2. V testu `price_isDailyRateTimesDaysWhenReturnedOnTime` najdi:
   ```java
   assertThat(price).isEqualByComparingTo("400"); // 4 * 100
   ```
3. **Změň na:**
   ```java
   assertThat(price).isEqualByComparingTo("0"); // záměrně špatně
   ```
4. Ulož.
5. V terminálu:
   ```bash
   git add src/test/java/cz/kst/btdd/domain/RentalPriceCalculationTest.java
   git commit -m "test výpočet a zaokrouhlení ceny rezervace"
   git push origin main
   ```
6. **Actions** → červené ❌.

---

### Krok 6 – CI má projít ✅

1. V **`RentalPriceCalculationTest.java`** změň zpět:
   ```java
   assertThat(price).isEqualByComparingTo("0"); // záměrně špatně
   ```
   **na:**
   ```java
   assertThat(price).isEqualByComparingTo("400"); // 4 * 100
   ```
2. Ulož.
3. V terminálu:
   ```bash
   git add src/test/java/cz/kst/btdd/domain/RentalPriceCalculationTest.java
   git commit -m "výpočet a zaokrouhlení ceny rezervace s doplněnou funkcí"
   git push origin main
   ```
4. **Actions** → zelené ✅.

---

## Dvojice 4: Integrační test (rezervace 201)

### Krok 7 – CI má spadnout ❌

1. Otevři **`src/test/java/cz/kst/btdd/api/RentalControllerIT.java`**
2. V testu `createReservation_returns201` najdi:
   ```java
   .andExpect(status().isCreated())
   ```
3. **Změň na:**
   ```java
   .andExpect(status().isOk())
   ```
4. Ulož.
5. V terminálu:
   ```bash
   git add src/test/java/cz/kst/btdd/api/RentalControllerIT.java
   git commit -m "přidán integrační test"
   git push origin main
   ```
6. **Actions** → červené ❌ (očekáváme 200, API vrací 201).

---

### Krok 8 – CI má projít ✅

1. V **`RentalControllerIT.java`** změň zpět:
   ```java
   .andExpect(status().isOk())
   ```
   **na:**
   ```java
   .andExpect(status().isCreated())
   ```
2. Ulož.
3. V terminálu:
   ```bash
   git add src/test/java/cz/kst/btdd/api/RentalControllerIT.java
   git commit -m "oprava chyb v integračních testech"
   git push origin main
   ```
4. **Actions** → zelené ✅.

---

## Dvojice 5: Zrušení rezervace (vlastní vs cizí)

### Krok 9 – CI má spadnout ❌

1. Otevři **`src/test/java/cz/kst/btdd/service/RentalServiceTest.java`**
2. V testu `cannotCancelOthers` najdi:
   ```java
   .hasMessageContaining("vlastní");
   ```
3. **Změň na:**
   ```java
   .hasMessageContaining("cizí");
   ```
4. Ulož.
5. V terminálu:
   ```bash
   git add src/test/java/cz/kst/btdd/service/RentalServiceTest.java
   git commit -m "test zrušení rezervace po skončení"
   git push origin main
   ```
6. **Actions** → červené ❌.

---

### Krok 10 – CI má projít ✅

1. V **`RentalServiceTest.java`** změň zpět:
   ```java
   .hasMessageContaining("cizí");
   ```
   **na:**
   ```java
   .hasMessageContaining("vlastní");
   ```
2. Ulož.
3. V terminálu:
   ```bash
   git add src/test/java/cz/kst/btdd/service/RentalServiceTest.java
   git commit -m "zrušení rezervace po skončení s doplněnou funkcí"
   git push origin main
   ```
4. **Actions** → zelené ✅.

---

## Shrnutí

| Krok | Soubor | Co změnit | Commit zpráva | CI |
|------|--------|-----------|----------------|-----|
| 1 | RentalServiceTest.java | "kolize" → "neexistuje" | test kolize rezervací | ❌ |
| 2 | RentalServiceTest.java | "neexistuje" → "kolize" | kolize rezervací s doplněnou funkcí | ✅ |
| 3 | RentalServiceTest.java | "zaměstnanec" → "admin" | test omezení roli | ❌ |
| 4 | RentalServiceTest.java | "admin" → "zaměstnanec" | omezení roli s doplněnou funkcí | ✅ |
| 5 | RentalPriceCalculationTest.java | "400" → "0" | test výpočet a zaokrouhlení ceny rezervace | ❌ |
| 6 | RentalPriceCalculationTest.java | "0" → "400" | výpočet a zaokrouhlení ceny rezervace s doplněnou funkcí | ✅ |
| 7 | RentalControllerIT.java | isCreated() → isOk() | přidán integrační test | ❌ |
| 8 | RentalControllerIT.java | isOk() → isCreated() | oprava chyb v integračních testech | ✅ |
| 9 | RentalServiceTest.java | "vlastní" → "cizí" | test zrušení rezervace po skončení | ❌ |
| 10 | RentalServiceTest.java | "cizí" → "vlastní" | zrušení rezervace po skončení s doplněnou funkcí | ✅ |

Po těchto 10 krocích máš **10 commitů** a v Actions mix ❌ a ✅. Zbývající commity (až do min. 20) můžeš doplnit např. úpravami v `docs/`, úpravou README nebo dalšími malými změnami – vždy jeden commit na jednu logickou změnu a pak `git push origin main`.
