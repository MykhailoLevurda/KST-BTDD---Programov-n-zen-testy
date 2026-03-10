# 20 smysluplných commitů – návod krok za krokem

Repozitář: **https://github.com/MykhailoLevurda/KST-BTDD---Programov-n-zen-testy.git**

---

## Příprava (udělej na začátku)

1. **Otevři terminál** a přejdi do **kořene projektu** (tam, kde je složka `.git` a složka `docs`):
   ```powershell
   cd "C:\Users\Levur\BTDD\KST-BTDD---Programov-n-zen-testy"
   ```
   (Uprav cestu, pokud máš projekt jinde.)

2. **Ověř, že jsi ve správné složce** – měla by existovat složka `docs` a v ní soubory `POZADAVKY.md`, `DOMENA-A-MODEL.md`:
   ```powershell
   dir docs
   ```

3. **Nastav remote** (pokud ještě nemáš):
   ```powershell
   git remote add origin https://github.com/MykhailoLevurda/KST-BTDD---Programov-n-zen-testy.git
   ```

4. **Pokud už máš jeden velký commit** a chceš ho nahradit 20 malými:
   ```bash
   git reset --soft HEAD~1
   git reset HEAD .
   ```
   (Tím zrušíš poslední commit, soubory zůstanou v pracovní složce, ale nebudou staged.)

5. **Pokud nemáš žádný commit**, nic neresetuj, jen přidávej soubory podle kroků níže.

---

## Máš už 10 commitů a chceš doplnit na 20?

Pokud máš historii jako: *feat(persistence): JPA entities* → … → *docs: README…* (10 commitů) a **chybí ti prvních 10** (docs požadavků, POM, doména, testy domény, OverlapChecker), udělej toto:

1. V kořeni projektu vytvoř novou větev „bez historie“, ale se všemi soubory:
   ```powershell
   git checkout --orphan temp main
   git rm -r --cached .
   ```
   (`git rm -r --cached .` odstraní všechny soubory z indexu, na disku zůstanou. Na větvi `temp` zatím není žádný commit, takže `git reset HEAD .` by selhal.)
2. Teď máš **všechny soubory v pracovní složce**, ale **žádný commit** a **nic není připraveno ke commitu**. Přidej a commitni **všech 20 kroků** od Commitu 1 do Commitu 20 (viz sekce níže). Nevynechávej žádný krok.
3. Až budeš mít 20 commitů na větvi `temp`, přepni zpět na `main` a nahraď ji:
   ```powershell
   git branch -D main
   git branch -m main
   ```
4. Nahraj novou historii na GitHub (přepíše se vzdálená větev):
   ```powershell
   git push --force origin main
   ```

---

## Jak správně udělat 20 commitů (aby se zobrazovalo všech 20)

**Princip:** Jeden commit = **jeden** blok `git add` + **jeden** `git commit`. Celkem 20 takových bloků za sebou.

1. **Běž na větvi `temp`** (po `checkout --orphan temp main` a `git rm -r --cached -f .`). V té chvíli máš 0 commitů.

2. **Pro každý z 20 kroků níže:**
   - zkopíruj a spusť **nejdřív** příkaz `git add ...` (přidají se jen vybrané soubory),
   - hned potom spusť **jediný** `git commit -m "..."`.
   - Tím vznikne **jeden** nový commit. Po 1. kroku máš 1 commit, po 2. máš 2 commity, … po 20. máš 20 commitů.

3. **Nevynechávej žádný krok** a **nepřidávej do jednoho commitu víc souborů**, než je v návodu – jinak by se ti „slily“ dva kroky do jednoho a skončíš s méně než 20 commity.

4. **Kontrola:** Po každých pěti commitech můžeš spustit:
   ```powershell
   git log --oneline
   ```
   Po 5. commitu uvidíš 5 řádků, po 10. commitu 10 řádků, po 20. commitu **20 řádků**. To znamená, že máš všech 20 commitů.

5. **Na konci** (až budeš mít všech 20): přejmenuj větev na `main`, smaž starou `main`, pushni:
   ```powershell
   git branch -D main
   git branch -m main
   git log --oneline
   git push --force origin main
   ```

**Proč dřív bylo jen 10?** Protože prvních 10 commitů (docs, POM, doména, testy domény) se nikdy neudělalo – buď se začalo až od „JPA entities“, nebo se několik kroků slilo do jednoho. Teď musíš projít **všech 20** kroků od Commitu 1 do Commitu 20.

---

## 20 commitů – přesně v tomto pořadí

Příkazy jsou pro **PowerShell** v kořeni projektu. Cesty jsou relativní ke kořeni repozitáře.

---

### Commit 1 – Dokumentace požadavků a domény

(Ujisti se, že jsi v kořeni projektu – příkaz `cd` z přípravy.)

```powershell
git add "docs/POZADAVKY.md" "docs/DOMENA-A-MODEL.md"
git commit -m "docs: add requirements and domain model documentation"
```

Pokud stále vidíš „pathspec did not match any files“, zkus přidat celou složku docs a commitnout jen tyto dva soubory tak, že je přidáš po jednom:
```powershell
git add docs\POZADAVKY.md
git add docs\DOMENA-A-MODEL.md
git status
git commit -m "docs: add requirements and domain model documentation"
```

---

### Commit 2 – Maven POM, konfigurace a .gitignore

```powershell
git add .gitignore pom.xml src/main/resources/application.yml src/test/resources/application.yml
git commit -m "build: add Maven POM, Spring Boot config and gitignore"
```

---

### Commit 3 – Hlavní třída aplikace

```powershell
git add src/main/java/cz/kst/btdd/ToolRentalApplication.java
git commit -m "feat: add Spring Boot application entry point"
```

---

### Commit 4 – Doménové enumy

```powershell
git add src/main/java/cz/kst/btdd/domain/RentalStatus.java src/main/java/cz/kst/btdd/domain/UserRole.java
git commit -m "feat(domain): add RentalStatus and UserRole enums"
```

---

### Commit 5 – Doménové entity User a Tool

```powershell
git add src/main/java/cz/kst/btdd/domain/User.java src/main/java/cz/kst/btdd/domain/Tool.java
git commit -m "feat(domain): add User and Tool entities"
```

---

### Commit 6 – Doménová třída Rental a přechody stavů

```powershell
git add src/main/java/cz/kst/btdd/domain/Rental.java
git commit -m "feat(domain): add Rental with state transition logic (activate, cancel, markReturned)"
```

---

### Commit 7 – Unit testy: stavové přechody (TDD)

```powershell
git add src/test/java/cz/kst/btdd/domain/RentalStateTransitionTest.java
git commit -m "test(domain): add Rental state transition unit tests"
```

---

### Commit 8 – Výpočet ceny půjčky

```powershell
git add src/main/java/cz/kst/btdd/domain/RentalPriceCalculator.java
git commit -m "feat(domain): add RentalPriceCalculator with late fee"
```

---

### Commit 9 – Unit testy: výpočet ceny

```powershell
git add src/test/java/cz/kst/btdd/domain/RentalPriceCalculationTest.java
git commit -m "test(domain): add RentalPriceCalculator unit tests"
```

---

### Commit 10 – Kontrola překrývání období

```powershell
git add src/main/java/cz/kst/btdd/domain/OverlapChecker.java
git commit -m "feat(domain): add OverlapChecker for date ranges"
```

---

### Commit 11 – Unit testy: OverlapChecker

```powershell
git add src/test/java/cz/kst/btdd/domain/OverlapCheckerTest.java
git commit -m "test(domain): add OverlapChecker unit tests"
```

---

### Commit 12 – JPA entity

```powershell
git add src/main/java/cz/kst/btdd/persistence/EntityUser.java src/main/java/cz/kst/btdd/persistence/EntityTool.java src/main/java/cz/kst/btdd/persistence/EntityRental.java
git commit -m "feat(persistence): add JPA entities for User, Tool, Rental"
```

---

### Commit 13 – Repozitáře

```powershell
git add src/main/java/cz/kst/btdd/persistence/UserRepository.java src/main/java/cz/kst/btdd/persistence/ToolRepository.java src/main/java/cz/kst/btdd/persistence/RentalRepository.java
git commit -m "feat(persistence): add Spring Data JPA repositories"
```

---

### Commit 14 – Služba a business výjimka

```powershell
git add src/main/java/cz/kst/btdd/service/BusinessException.java src/main/java/cz/kst/btdd/service/RentalService.java
git commit -m "feat(service): add RentalService and BusinessException"
```

---

### Commit 15 – Unit testy služby (mocky)

```powershell
git add src/test/java/cz/kst/btdd/service/RentalServiceTest.java
git commit -m "test(service): add RentalService unit tests with mocks"
```

---

### Commit 16 – API DTO a globální ošetření chyb

```powershell
git add src/main/java/cz/kst/btdd/api/dto/CreateReservationRequest.java src/main/java/cz/kst/btdd/api/dto/ErrorResponse.java src/main/java/cz/kst/btdd/api/GlobalExceptionHandler.java
git commit -m "feat(api): add DTOs and global exception handler"
```

---

### Commit 17 – REST controllery

```powershell
git add src/main/java/cz/kst/btdd/api/RentalController.java src/main/java/cz/kst/btdd/api/UserController.java src/main/java/cz/kst/btdd/api/ToolController.java
git commit -m "feat(api): add REST controllers for rentals, users, tools"
```

---

### Commit 18 – Integrační testy

```powershell
git add src/test/java/cz/kst/btdd/api/RentalControllerIT.java
git commit -m "test(api): add RentalController integration tests"
```

---

### Commit 19 – CI/CD a Maven wrapper

```powershell
git add .github/workflows/ci.yml .mvn/wrapper/maven-wrapper.properties
git commit -m "ci: add GitHub Actions workflow and Maven wrapper config"
```

---

### Commit 20 – README a dokumentace testů

```powershell
git add README.md docs/TESTOVACI-STRATEGIE.md docs/DALSI-KROKY.md docs/GIT-20-COMMITU.md
git commit -m "docs: add README, test strategy and next steps guide"
```

---

## Po dokončení všech 20 commitů

1. **Zkontroluj historii:**
   ```powershell
   git log --oneline
   ```
   Mělo by být 20 řádků.

2. **Push na GitHub** (větev `main`):
   ```powershell
   git branch -M main
   git push -u origin main
   ```

3. **Ověř na GitHubu:** záložka **Actions** – workflow by se měl spustit a vytvořit artefakt **jacoco-report**.

---

## Rychlý přehled zpráv commitů

| # | Zpráva |
|---|--------|
| 1 | docs: add requirements and domain model documentation |
| 2 | build: add Maven POM, Spring Boot config and gitignore |
| 3 | feat: add Spring Boot application entry point |
| 4 | feat(domain): add RentalStatus and UserRole enums |
| 5 | feat(domain): add User and Tool entities |
| 6 | feat(domain): add Rental with state transition logic |
| 7 | test(domain): add Rental state transition unit tests |
| 8 | feat(domain): add RentalPriceCalculator with late fee |
| 9 | test(domain): add RentalPriceCalculator unit tests |
| 10 | feat(domain): add OverlapChecker for date ranges |
| 11 | test(domain): add OverlapChecker unit tests |
| 12 | feat(persistence): add JPA entities |
| 13 | feat(persistence): add Spring Data JPA repositories |
| 14 | feat(service): add RentalService and BusinessException |
| 15 | test(service): add RentalService unit tests with mocks |
| 16 | feat(api): add DTOs and global exception handler |
| 17 | feat(api): add REST controllers |
| 18 | test(api): add RentalController integration tests |
| 19 | ci: add GitHub Actions workflow and Maven wrapper config |
| 20 | docs: add README, test strategy and next steps guide |
