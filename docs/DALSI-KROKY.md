# Další kroky – semestrální práce

## 1. Ověření Mavenu v IntelliJ

Maven z IntelliJ **není** automaticky v systémové PATH (PowerShell/CMD ho nevidí). V IDE ale funguje.

**Jak ověřit:**

- Otevři projekt v IntelliJ.
- Pravý panel: **Maven** (ikona slona). Pokud ho nevidíš: **View → Tool Windows → Maven**.
- Rozbal **Lifecycle**. Dvojklik na **compile** nebo **test** – mělo by to proběhnout bez chyby.

Pokud IntelliJ nabídne „Load Maven Project“ nebo „Import“, potvrď to. JDK musí být nastavená na **17** (File → Project Structure → Project SDK).

---

## 2. Vygenerování Maven Wrapperu (volitelné, pro příkazovou řádku)

Aby šlo spouštět Maven i **mimo IntelliJ** (PowerShell, CI už má vlastní Maven):

1. V IntelliJ otevři **Maven** (pravý panel).
2. Klikni na ikonu **Execute Maven Goal** (běžící člověk) nebo **Ctrl+Shift+A** a napiš: `Execute Maven Goal`.
3. Do políčka zadej: **`wrapper:wrapper`** a potvrď Enter.
4. Po dokončení v projektu přibudou soubory **`mvnw.cmd`** a **`mvnw`** (a složka `.mvn/wrapper` s JAR).
5. Od té chvíle můžeš v kořenu projektu spouštět např.:
   - **`.\mvnw.cmd verify`** – build a testy včetně coverage
   - **`.\mvnw.cmd test`** – jen testy
   - **`.\mvnw.cmd spring-boot:run`** – spuštění aplikace

---

## 3. Spuštění testů a buildu

**V IntelliJ:**

- Maven → Lifecycle → dvojklik na **verify**  
  (provede compile, test, a JaCoCo check; při nedostatečném pokrytí build selže).

**Nebo z příkazové řádky** (po vygenerování wrapperu):

```powershell
cd "c:\Users\Levur\BTDD\KST-BTDD---Programov-n-zen-testy"
.\mvnw.cmd verify
```

**Výstup:**

- Report pokrytí: **`target\site\jacoco\index.html`** (otevři v prohlížeči).
- Pokud JaCoCo check selže (nízké pokrytí), doplň testy nebo dočasně sniž limity v `pom.xml` v sekci `jacoco-maven-plugin` → `check`.

---

## 4. Spuštění aplikace

**V IntelliJ:**

- Maven → Lifecycle → **spring-boot:run** (dvojklik),  
  nebo spusť hlavní třídu **`ToolRentalApplication`** (zelená šipka Run).

**Z příkazové řádky:**

```powershell
.\mvnw.cmd spring-boot:run
```

API pak běží na **http://localhost:8080** (např. `GET http://localhost:8080/api/users`).

---

## 5. Git – alespoň 20 smysluplných commitů

Požadavek: **minimálně 20 commitů**, v historii čitelné kroky TDD (ideálně i refaktor).

**Jak na to:**

- Nedávej všechno do jednoho „Initial commit“. Rozděl změny do logických kroků, např.:
  - Požadavky a dokumentace (README, docs)
  - Doménový model (entity, enumy)
  - Testy stavových přechodů + implementace
  - Výpočet ceny + testy
  - Overlap checker + testy
  - Repozitáře a JPA entity
  - Služba + unit testy s mocky
  - REST API a validace
  - Integrační testy
  - CI pipeline
  - Refaktoring (např. přejmenování, rozdělení metod)

**V IntelliJ:**  
Commit často (Commit → vyber jen část souborů nebo malé změny → smysluplná zpráva, např. „Add rental state transition tests and domain logic“).

---

## 6. GitHub a CI

1. Vytvoř repozitář na GitHubu (např. **KST-BTDD---Programov-n-zen-testy**).
2. Nastav remote a pushni větev (např. `main`):

   ```powershell
   git remote add origin https://github.com/TVUJ_LOGIN/KST-BTDD---Programov-n-zen-testy.git
   git branch -M main
   git push -u origin main
   ```

3. V repozitáři je workflow **`.github/workflows/ci.yml`**. Po pushni na **main** nebo **master** se spustí CI:
   - build (`mvn verify`),
   - testy,
   - nahrání JaCoCo reportu jako artefakt **jacoco-report** (Actions → vybraný run → Artifacts).

---

## 7. Odevzdání

- Odevzdej **odkaz na repozitář**.
- V repozitáři musí být **README** (popis domény, jak spustit, architektura, testovací strategie) – už je připraven v kořenu a v **docs/**.

---

## Rychlý checklist

| Krok | Akce |
|------|------|
| 1 | Ověř Maven v IntelliJ (Maven panel → compile / test). |
| 2 | (Volitelně) Vygeneruj wrapper: Maven Goal **`wrapper:wrapper`**. |
| 3 | Spusť **verify** v IntelliJ nebo **`.\mvnw.cmd verify`**. |
| 4 | Prohlédni coverage: **target\site\jacoco\index.html**. |
| 5 | Rozděl práci do **≥ 20 commitů** s jasnými zprávami. |
| 6 | Pushni na GitHub a zkontroluj, že CI proběhne a artefakt **jacoco-report** je k dispozici. |
