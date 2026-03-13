# Analýza: spojení BTDD + DevOps semestrální práce

Tento dokument vychází z **ZADANI.md** (DevOps workflow) a ze stávajícího stavu projektu (půjčovna nářadí z BTDD). Cíl: zhodnotit, co už je hotové, a popsat kroky k doplnění DevOps požadavků.

---

Zmeny na zkousku

## Část A: Co už máme hotové (BTDD + základ CI)

### Z aplikace a vývoje (BTDD/KTDD)

| Požadavek | Stav | Poznámka |
|-----------|------|----------|
| Aplikace sestavitelná a testovatelná | ✅ | Maven, `mvn verify`, JUnit + integrační testy |
| Java/Spring Boot REST + databáze | ✅ | Spring Boot 3, JPA, H2 (1 služba + 1 DB) |
| Doménová logika (entity, pravidla) | ✅ | User, Tool, Rental; 5 business pravidel |
| Unit + integrační testy | ✅ | RentalServiceTest, RentalControllerIT, doménové testy |
| Code coverage (JaCoCo, report v CI) | ✅ | JaCoCo v pom.xml, artefakt v GitHub Actions |
| Git, historie commitů | ✅ | Repozitář, min. 20 commitů (podle tvého vyjádření) |
| README + dokumentace | ✅ | README, docs (doména, architektura, testy, Postman) |

### Z CI (současný workflow)

| Požadavek (ZADANI.md 3.2) | Stav | Poznámka |
|---------------------------|------|----------|
| CI při push/PR | ✅ | `on: push, pull_request` na main/master |
| Build (Maven) | ✅ | `mvn -B verify -Pci` |
| Unit testy | ✅ | Součást `mvn verify` |
| Integrační testy | ✅ | RentalControllerIT v `mvn verify` |
| Statická kontrola kvality (min. 1 nástroj) | ❌ | Chybí (Checkstyle, SpotBugs, Sonar atd.) |
| Sestavení Docker image | ❌ | Chybí krok v CI (Dockerfile už existuje) |
| Publikace image do registry | ❌ | Chybí |
| Artefakty: test reporty + coverage | ✅ | JaCoCo report jako artefakt; test reporty lze doplnit |

---

## Část B: Co musíme doplnit (DevOps podle ZADANI.md)

### 3.1 Git a workflow

| Požadavek | Stav | Co udělat |
|-----------|------|-----------|
| Min. 20 commitů, popisné zprávy | ✅ | Už máte |
| Workflow na větvích (feature/hotfix) | ⚠️ | Doložit: práce na feature větvi, merge do main (např. pro Docker/K8s úkoly) |

**Krok:** Při doplňování DevOps úkolů dělej změny na větvi (např. `feature/docker` nebo `feature/k8s`), pak merge do `main`. V README nebo docs stručně popsat: „Vývoj probíhá na větvích, merge po revizi.“

---

### 3.2 CI pipeline – rozšíření

| Co chybí | Jak doplnit |
|----------|-------------|
| Statická kontrola kvality | Přidat jeden nástroj: např. **SpotBugs** nebo **Checkstyle** (Maven plugin) a krok v CI, který ho spustí. Případně SonarCloud (volitelně). |
| Sestavení Docker image | Po úspěšném buildu spustit `docker build`, image pojmenovat např. podle commitu nebo tagu. |
| Publikace image do registry | Push image do **GitHub Container Registry** (ghcr.io) nebo Docker Hub; v CI použít GitHub Actions login do GHCR. |

**Kroky:**  
1. Přidat do `pom.xml` plugin pro statickou analýzu (SpotBugs nebo Checkstyle).  
2. V CI přidat krok „Run static analysis“ (např. `mvn spotbugs:check` nebo `mvn checkstyle:check`).  
3. Vytvořit Dockerfile (viz 3.4).  
4. V CI přidat job/kroky: build image, push do ghcr.io (nebo jiného registry).  
5. Generování test reportů: Surefire vytváří reporty v `target/surefire-reports`; lze je nahrát jako artefakt.

---

### 3.3 CD a prostředí (staging + production)

| Požadavek | Stav | Co udělat |
|-----------|------|-----------|
| Nasazení do K8s (lokální cluster OK) | ❌ | Připravit manifesty a nasazení (viz 3.5). |
| Dvě prostředí: staging, production | ❌ | Dva namespaces nebo dva sady manifestů (Kustomize overlay / Helm values). |
| Automatizované nasazení do staging | ❌ | CD krok v pipeline: po úspěšném buildu a image push nasadit do staging (např. `kubectl apply` nebo Helm upgrade). |

**Kroky:**  
1. Zřídit lokální cluster (minikube/kind/k3d).  
2. Připravit Kubernetes manifesty včetně staging a production (např. `k8s/base` + `k8s/staging`, `k8s/production`).  
3. V CI/CD přidat job „Deploy to staging“ (např. při push na main): build image → push → `kubectl apply -f k8s/staging` (nebo ekvivalent).  
4. Production může být ruční nebo na tag (např. `v1.0.0`).

---

### 3.4 Kontejnerizace

| Požadavek | Stav | Co udělat |
|-----------|------|-----------|
| Dockerfile | ✅ | Hotovo: multi-stage (Maven → eclipse-temurin JRE), ne-root user `app`, HEALTHCHECK na `/api/users`. |
| Reprodukovatelný build, rozumná velikost | ✅ | JRE Alpine, pevné base image. |
| Spuštění pod ne-root uživatelem | ✅ | USER app v Dockerfile. |
| Healthcheck | ✅ | HEALTHCHECK s wget na http://127.0.0.1:8080/api/users. |
| docker-compose pro lokální vývoj | ✅ | Hotovo: `app` (build z Dockerfile) + `db` (PostgreSQL), env pro JDBC. |

**Kroky (již splněno):**  
Dockerfile i docker-compose.yml jsou v repozitáři a ověřené. Zbývá jen zapojit build image do CI a push do registry (viz 3.2).

---

### 3.5 Kubernetes manifesty

| Požadavek | Stav | Co udělat |
|-----------|------|-----------|
| Deployment | ❌ | YAML: deployment pro aplikaci (image z registry), replicas, env z ConfigMap/Secret. |
| Service | ❌ | YAML: ClusterIP (nebo NodePort) pro přístup k podům. |
| ConfigMap | ❌ | Konfigurace (např. JDBC URL bez hesla, názvy, log level). |
| Secret | ❌ | Heslo k DB (ne plaintext v repo – hodnoty z env nebo sealed/cipher). |
| Ingress nebo port-forward | ❌ | Ingress (např. pro staging) nebo dokumentovaný `kubectl port-forward`. |
| Resource limity/requests | ❌ | V Deployment: resources.requests a resources.limits (CPU, memory). |

**Kroky:**  
1. Složka `k8s/` (nebo `deploy/`): `deployment.yaml`, `service.yaml`, `configmap.yaml`, `secret.yaml` (šablona s placeholderem – skutečné hodnoty necommittovat).  
2. V README/docs popsat: jak vytvořit Secret lokálně, jak nasadit (kubectl apply), jak přistupovat (port-forward nebo Ingress).  
3. Dva overlayy/prostředí: staging (např. jiný image tag, namespace staging), production (namespace production).

---

### 3.9 Bezpečnost

| Požadavek | Stav | Co udělat |
|-----------|------|-----------|
| Žádná plaintext hesla v repozitáři | ⚠️ | Zkontrolovat, že v kódu a v YAML nejsou hesla; DB heslo jen v Secret / env. |
| Dokumentace práce se secrets | ❌ | V docs popsat: K8s Secret pro DB heslo, GitHub Secrets pro CI (registry login, K8s kubeconfig pokud CD), jak nastavit lokálně. |

**Kroky:**  
1. Projít repo: žádné heslo v `application.yml`, v YAML jen placeholder (např. `DB_PASSWORD` z env).  
2. Do README nebo `docs/DEVOPS.md` přidat sekci „Secrets“: kde se ukládají (K8s Secret, GitHub Secrets), jak je naplnit při nasazení.

---

### 4) Povinné výstupy k odevzdání

| Výstup | Stav | Co udělat |
|--------|------|------------|
| Odkaz na repozitář | ✅ | Už máte |
| README/docs: architektura (diagram komponent a datových toků) | ⚠️ | Doplnit diagram (např. Mermaid nebo obrázek): aplikace, DB, CI/CD, K8s. |
| Popis prostředí (staging/prod) a rozdíly konfigurace | ❌ | Doplnit do docs: co je staging vs production, jak se liší config (namespace, image tag, env). |
| Popis nasazení (lokálně / cloud / K8s) | ⚠️ | README má lokální Maven; doplnit: Docker Compose, K8s (lokální cluster), volitelně cloud. |
| CI konfigurace | ✅ | `.github/workflows/ci.yml` – rozšířit o statickou analýzu, Docker build, push, CD. |
| Dockerfile, compose | ✅ | Hotovo (viz 3.4). |
| Kubernetes manifesty | ❌ | Vytvořit (viz 3.5). |
| IaC (volitelné) | — | Lze přeskočit nebo krátce zmínit (např. skripty pro vytvoření clusteru). |

---

## Část C: Co máme hotovo TEĎ vs. co CHYBÍ (shrnutí k realizaci)

### ✅ Hotovo (aktuální stav repozitáře)

- Aplikace, Maven, unit + integrační testy, JaCoCo, 20+ commitů, README/docs.
- **CI:** push/PR → build, testy, JaCoCo artefakt.
- **Dockerfile:** multi-stage, ne-root user, HEALTHCHECK.
- **docker-compose.yml:** app + PostgreSQL, ověřeno běh i opravy (driver, data.sql).

### ❌ Co nám chybí (pořadí podle dokumentu)

| # | Oblast | Konkrétně co udělat |
|---|--------|----------------------|
| 1 | **CI: Statická analýza** | Do `pom.xml` přidat SpotBugs nebo Checkstyle; v `.github/workflows/ci.yml` krok např. `mvn spotbugs:check` (nebo checkstyle). |
| 2 | **CI: Docker image + registry** | V CI po úspěšném buildu: sestavit image, login do GHCR, push image (tag např. podle SHA nebo `latest`). |
| 3 | **Kubernetes: základ** | Složka `k8s/`: Deployment, Service, ConfigMap, Secret (šablona bez plaintext hesel), resource limits; přístup přes Ingress nebo port-forward. |
| 4 | **K8s: staging vs production** | Dva prostředí (namespaces nebo Kustomize overlay): staging, production; zdokumentovat rozdíly (image tag, config). |
| 5 | **CD: deploy do staging** | V pipeline po push image krok „Deploy to staging“ (kubectl apply nebo Helm) – automaticky při push na main. |
| 6 | **Dokumentace** | Diagram architektury (Mermaid); popis prostředí (staging/prod), nasazení (Maven, Docker, K8s); práce se secrets (K8s Secret, GitHub Secrets). |
| 7 | **Git workflow** | Při těchto úkolech pracovat na větvi (např. `feature/ci-static-analysis`, `feature/k8s`), pak merge do main – doložit v historii. |
| 8 | **Bezpečnost / secrets** | Ověřit žádná hesla v repo; v docs popsat: K8s Secret pro DB, GitHub Secrets pro CI (registry, případně kubeconfig). |

---

## Část D: Doporučené pořadí kroků (postupně)

1. ~~**Dockerfile**~~ ✅ Hotovo.  
2. ~~**docker-compose**~~ ✅ Hotovo.  
3. **CI: statická analýza** – SpotBugs/Checkstyle v `pom.xml` + krok v CI.  
4. **CI: build a push Docker image** – build image v CI, push do ghcr.io.  
5. **Kubernetes: základní manifesty** – `k8s/` (Deployment, Service, ConfigMap, Secret, limity).  
6. **Kubernetes: staging vs production** – dva overlay/namespaces.  
7. **CD: deploy do staging** – krok v pipeline.  
8. **Dokumentace** – diagram, prostředí, nasazení, secrets.  
9. **Git workflow** – práce na větvi při doplňování bodů 3–8.

V následujících sekcích můžeš mít podrobné „návody na jeden krok“ (konkrétní příkazy a úryvky YAML), aby bylo jasné, co přesně udělat v IDE a v repozitáři.

Poslední aktualizace: CI má SpotBugs, build a push Docker image do GHCR jsou nastavené a otestované.
