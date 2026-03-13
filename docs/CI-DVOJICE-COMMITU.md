# Dvojice commitů pro CI (inspirace od kolegy)

U kolegy je v GitHub Actions vidět vzor: **nejdřív commit, u kterého CI padne** (červené X), **pak commit s opravou/doplněním**, u kterého **CI projde** (zelená fajfka). Commit zprávy jsou v češtině a ve dvojicích: „test X“ → „X s doplněnou funkcí“ nebo „oprava …“.

Pro tvůj projekt (půjčovna nářadí) můžeš použít níže uvedené dvojice. První řádek = záměrně nechat CI spadnout, druhý = oprava tak, aby CI prošla.

---

## Vzor jako u kolegy

| 1. commit (CI ❌) | 2. commit (CI ✅) |
|------------------|-------------------|
| test kolize casu | kolize rezervací s doplněnou funkcí |
| test zrušení rezervace po skončení | zrušení rezervace po skončení s doplněnou funkcí |
| test omezení roli | omezení roli s doplněnou funkcí |
| test výpočet a zaokrouhlení ceny rezervace | výpočet a zaokrouhlení ceny rezervace s doplněnou funkcí |
| test idempotence | idempotence s doplněnou funkcí |
| přidán integrační test | oprava chyb v integračních testech |
| implementace rest controllerů | implementace rest controllerů + závislosti |

---

## Konkrétní dvojice pro tvůj projekt (tool-rental)

Přizpůsobené tvé doméně (stavy, cena, kolize, role, idempotence, integrační testy).

### 1. Kolize rezervací
- **CI ❌** `test kolize rezervací`  
  Přidáš unit test (např. v `RentalServiceTest` nebo `OverlapCheckerTest`), který kontroluje, že při překrývajícím se období se vyhodí výjimka. Zatím bez implementace nebo s chybným očekáváním → test padá, push, CI červené.
- **CI ✅** `kolize rezervací s doplněnou funkcí`  
  Doplníš/opravíš logiku (OverlapChecker, RentalService) a očekávání v testu → push, CI zelené.

### 2. Omezení rolí
- **CI ❌** `test omezení roli`  
  Přidáš test, že pouze zaměstnanec může aktivovat půjčku / evidovat vrácení. Test volá službu s rolí CUSTOMER a očekává výjimku. Zatím bez kontroly role ve službě → CI červené.
- **CI ✅** `omezení roli s doplněnou funkcí`  
  Doplníš ve službě kontrolu role (requireEmployee) a případně opravíš test → CI zelené.

### 3. Výpočet a zaokrouhlení ceny
- **CI ❌** `test výpočet a zaokrouhlení ceny rezervace`  
  Přidáš test na výpočet ceny (denní sazba × dny, pozdní poplatek). Záměrně špatný expected nebo chybějící implementace → CI červené.
- **CI ✅** `výpočet a zaokrouhlení ceny rezervace s doplněnou funkcí`  
  Doplníš/opravíš `RentalPriceCalculator` a test → CI zelené.

### 4. Zrušení rezervace (vlastní / po skončení)
- **CI ❌** `test zrušení rezervace po skončení`  
  Přidáš test na pravidlo „nelze zrušit cizí rezervaci“ nebo hraniční případ. Test padá → push, CI červené.
- **CI ✅** `zrušení rezervace po skončení s doplněnou funkcí`  
  Opravíš logiku v `cancelReservation` a test → CI zelené.

### 5. Idempotence / duplicity
- **CI ❌** `test idempotence`  
  Přidáš test, že druhá rezervace stejného nářadí ve stejném období není možná. Zatím bez kontroly v službě → CI červené.
- **CI ✅** `idempotence s doplněnou funkcí`  
  Doplníš kontrolu (findOverlappingForTool) a test → CI zelené.

### 6. Integrační testy
- **CI ❌** `přidán integrační test`  
  Přidáš nový integrační test (např. v `RentalControllerIT`), který zatím padá (špatné URL, očekávání nebo chybějící data) → push, CI červené.
- **CI ✅** `oprava chyb v integračních testech`  
  Opravíš test (URL, očekávaný status, setup) → CI zelené.

### 7. REST controllery
- **CI ❌** `implementace rest controllerů`  
  Přidáš nový endpoint nebo změníš stávající bez úpravy testů / závislostí → něco se rozbije, CI červené.
- **CI ✅** `implementace rest controllerů + závislosti`  
  Doplníš konfiguraci, testy nebo závislosti tak, aby build a testy prošly → CI zelené.

---

## Jak to pushovat (postup)

1. **Udělej změnu** odpovídající prvnímu řádku dvojice (např. přidáš test, který padá).
2. **Commit + push:**
   ```bash
   git add <soubory>
   git commit -m "test kolize rezervací"
   git push origin main
   ```
3. **Kontrola:** GitHub → Actions → tento běh by měl skončit **červeně** (❌).
4. **Oprav** kód tak, aby test prošel (nebo doplň implementaci).
5. **Druhý commit + push:**
   ```bash
   git add <soubory>
   git commit -m "kolize rezervací s doplněnou funkcí"
   git push origin main
   ```
6. **Kontrola:** GitHub → Actions → tento běh by měl skončit **zeleně** (✅).

Takhle zopakuješ pro další dvojice. Každá dvojice = 2 commity, část z nich červená a část zelená – přesně jako u kolegy na screenech.

---

## Rychlý přehled zpráv pro commity

| # | 1. commit (červené) | 2. commit (zelené) |
|---|----------------------|---------------------|
| 1 | test kolize rezervací | kolize rezervací s doplněnou funkcí |
| 2 | test omezení roli | omezení roli s doplněnou funkcí |
| 3 | test výpočet a zaokrouhlení ceny rezervace | výpočet a zaokrouhlení ceny rezervace s doplněnou funkcí |
| 4 | test zrušení rezervace po skončení | zrušení rezervace po skončení s doplněnou funkcí |
| 5 | test idempotence | idempotence s doplněnou funkcí |
| 6 | přidán integrační test | oprava chyb v integračních testech |
| 7 | implementace rest controllerů | implementace rest controllerů + závislosti |

K tomu přidej další smysluplné commity (dokumentace, úpravy CI, refactor), aby celkový počet byl **min. 20**. V Actions pak bude vidět mix červených a zelených běhů jako u kolegy.
