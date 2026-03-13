


 Cíl práce

Cílem je navrhnout a prakticky realizovat kompletní DevOps workflow pro zvolenou aplikaci: od správy zdrojového kódu přes automatizované build/test, kontejnerizaci, nasazení do Cloud/Kubernetes až po monitoring/logging, bezpečnostní kontroly a release strategii. Důraz je kladen na opakovatelný, automatizovaný a zdokumentovaný proces.

Výsledkem není jen běžící aplikace, ale hlavně funkční pipeline a provozní připravenost (operovatelnost).

2) Vstupní předpoklady a volba aplikace

Aplikace může být vlastní nebo převzatá kostra (doporučeně Java / Spring Boot REST), ale musí být možné ji:
sestavit automaticky,
otestovat,
zabalit do Docker image,
nasadit do Cloud/Kubernetes.
Minimální doporučení: 1 služba + 1 závislost (např. databáze) – i kdyby jen pro integraci.
Semestrální práci je možno spojit s předmětem BTDD/KTDD.
3) Povinný rozsah výsledku (minimální požadavky)

3.1 Git a práce v repozitáři (povinné)

Projekt v Git repozitáři se smysluplnou historií:
minimálně 20 commitů (žádný „big‑bang“ na konci),
popisné zprávy commitů, dohledatelné kroky vývoje.
Definovaný workflow:
práce na větvích (feature/hotfix),
3.2 CI pipeline (povinné)

CI musí být automaticky spuštěno při push/PR a musí obsahovat minimálně tyto kroky:

Build (např. Maven/Gradle)
Testy
unit testy (rychlé)
integrační testy (např. s DB v kontejneru nebo testovacím prostředí)
Statická kontrola kvality (min. jeden nástroj; může být i jednoduchý)
Sestavení Docker image
Publikace image do registry (nebo artefakt jako výstup pipeline)
Generování artefaktů: test reporty + (pokud existuje) coverage report
Pozn.: konkrétní CI nástroj je na studentovi (GitHub Actions / GitLab CI / Jenkins), ale pipeline musí být reprodukovatelná a čitelná v repozitáři.

3.3 CD a prostředí (povinné)

Nasazení do Cloud/Kubernetes (lokální cluster je akceptovatelný: minikube/kind/k3d apod.; cloud je volitelný).
Minimálně dvě prostředí:
staging (ověření)
production (simulované nebo reálné)
typicky jako namespaces nebo oddělené konfigurační profily
Deployment musí být automatizovaný (součást CD) minimálně pro staging.
3.4 Kontejnerizace (povinné)

Dockerfile pro aplikaci s důrazem na:
reprodukovatelný build,
rozumnou velikost image,
spuštění pod ne-root uživatelem (doporučeno),
healthcheck (pokud dává smysl).
Pro lokální vývoj:
docker compose (nebo ekvivalent) pro spuštění aplikace + závislosti.
3.5 Kubernetes manifesty (povinné)

Minimálně:

Deployment, Service
ConfigMap a práce s konfigurací (oddělení configu od image)
Secret pro citlivé údaje (v repozitáři nesmí být plaintext hesla/tokény)
Ingress nebo port-forward dokumentovaný způsob přístupu
Resource limity/requests (alespoň základní nastavení)
Formát:

manifesty přímo v YAML, nebo Helm chart / Kustomize (volba je na studentovi).
3.6 Infrastructure as Code (volitelné)

Student může ukázat princip IaC alespoň v jedné z podob:

Terraform/Pulumi pro vytvoření infrastruktury (např. cluster, registry, síť, namespace, storage) – i kdyby jen v jednoduché, školní variantě,
nebo Ansible pro konfigurační management (např. příprava hostu, instalace nástrojů, nasazení komponent),
nebo jasně definované deklarativní prostředí (např. Helm/Kustomize + automatizované aplikování) doplněné o odůvodnění.
3.7 Release management a strategie nasazení (volitelné)

Definovaná verze aplikace (např. semver) a způsob tagování/releasů.
Implementovaná a předvedená alespoň jedna strategie:
blue/green nebo
canary
Musí být popsán rollback postup a doloženo, že je reálně proveditelný.
3.8 Observabilita: monitoring + logy (volitelné)

Monitoring:
sběr metrik aplikace i runtime (minimálně HTTP latence/počty požadavků + základní systémové metriky),
dashboard (např. Grafana) nebo alespoň dokumentované grafy / metriky.
Logy:
strukturované logování (min. konzistentní formát),
centralizace nebo alespoň prokazatelně vyhledatelné logy z clusteru (např. přes stack typu ELK/Loki – volitelné, ale doporučené).
Alerting (minimálně 1 alert):
např. vysoká chybovost, nedostupnost, restart loop, saturace paměti.
3.9 Bezpečnost (povinné)

Tajné údaje:
nesmí být v repozitáři v plaintextu,
popsané řešení práce se secrets (Kubernetes Secrets, CI secrets apod.).
4) Povinné výstupy k odevzdání

Odkaz na repozitář
README (nebo /docs) obsahující:
architekturu (min. diagram komponent a datových toků),
popis prostředí (staging/prod) a rozdílů konfigurace,
popis nasazení lokálně/cloud/kubernetes (dle realizovaných možností)
Konfigurační soubory:
CI konfigurace (.github/workflows/... nebo .gitlab-ci.yml nebo Jenkinsfile)
Dockerfile, compose
Kubernetes manifesty (nebo Helm/Kustomize)
IaC (Terraform/Ansible/…)