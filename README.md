# 🍔 QuickOrder

**Plateforme de commande et de livraison en microservices**

`Spring Boot` · `Kafka` · `PostgreSQL` · `Docker` · `Kubernetes (AKS)` · `Azure` · `Terraform`

> 🚧 Projet en cours de développement module full stack

---

## 📌 Présentation

QuickOrder est une plateforme de commande et de livraison en ligne, conçue sur une **architecture événementielle en microservices**. Elle permet à un client de parcourir un catalogue, de passer une commande, de la payer, puis de suivre sa livraison en temps réel, tout en notifiant chaque acteur à chaque étape.

Le projet a pour objectif de mettre en pratique une chaîne complète de développement moderne : conception de microservices découplés, communication asynchrone, conteneurisation, déploiement cloud automatisé, qualité logicielle et supervision.

---

## 🏗 Architecture

L'application est composée de **8 microservices** qui communiquent entre eux de manière asynchrone via **Apache Kafka**. Chaque service est autonome et possède sa **propre base de données PostgreSQL**, dont le schéma est versionné avec **Flyway**.

| Service | Rôle |
|---|---|
| **API Gateway** | Point d'entrée unique de la plateforme : routage des requêtes, validation du jeton JWT, gestion du CORS et limitation de débit. |
| **Authentification** | Inscription et connexion des utilisateurs, émission et renouvellement des jetons JWT, gestion des rôles (client, livreur, administrateur). |
| **Catalogue** | Gestion des restaurants, des catégories et des produits : prix, descriptions et disponibilité. |
| **Commandes** | Création et suivi des commandes, de leur création jusqu'à leur livraison ou leur annulation. |
| **Paiement** | Traitement des paiements, remboursements et historique des transactions. |
| **Livraison** | Assignation d'un livreur, suivi des statuts de livraison et finalisation de la course. |
| **Notifications** | Envoi d'emails, SMS ou notifications push aux clients et livreurs selon les événements reçus. |
| **Analytics** | Agrégation des événements pour produire des indicateurs : chiffre d'affaires, délais de livraison, produits les plus commandés. |

### Fonctionnement événementiel

Les services ne s'appellent pas directement : ils publient et consomment des **événements Kafka**. Lorsqu'un client passe commande, le service Commandes publie un événement ; le service Paiement le consomme et traite le règlement ; si celui-ci est accepté, la commande est confirmée et le service Livraison assigne un livreur ; les Notifications informent le client à chaque étape, pendant qu'Analytics enregistre l'ensemble des événements. En cas d'échec de paiement, la commande est automatiquement annulée.

Cette approche garantit un **faible couplage** entre les services, une meilleure **résilience** et une **scalabilité** indépendante de chaque composant.

---

## ⚙️ Industrialisation et déploiement

- **Conteneurisation** : chaque service est packagé dans une image **Docker** construite en *multi-stage* pour obtenir des images légères et sécurisées.
- **Orchestration** : déploiement sur **Kubernetes** via **Azure Kubernetes Service (AKS)**.
- **Infrastructure as Code** : l'infrastructure Azure est provisionnée avec **Terraform** et la configuration des machines automatisée avec **Ansible**.
- **Supervision** : métriques collectées par **Prometheus** et visualisées dans **Grafana** (santé des services, performances, flux Kafka).

---

## 🔄 CI/CD et qualité

Les pipelines **GitHub Actions** et **Jenkins** automatisent l'ensemble de la chaîne de livraison :

1. **Build** du projet
2. **Tests** unitaires et d'intégration (**JUnit**, **Testcontainers**)
3. **Analyse de la qualité** du code avec **SonarQube**
4. **Scan de sécurité** des images avec **Trivy**
5. **Publication** des images sur **DockerHub**
6. **Déploiement automatisé** sur le cluster AKS

---

## 👥 Organisation du projet

- **Méthodologie** : Scrum, avec suivi des tickets et des sprints sur **Jira**.
- **Gestion du code** : **GitFlow** (`main`, `develop`, branches `feature/*`, `release/*`, `hotfix/*`) avec revue de code par Pull Request.

---

## 🧰 Technologies

**Backend** : Java, Spring Boot, Spring Cloud Gateway, Spring Security (JWT)
**Messagerie** : Apache Kafka
**Données** : PostgreSQL, Flyway
**Conteneurs & cloud** : Docker, Kubernetes (AKS), Azure
**Infrastructure** : Terraform, Ansible
**CI/CD & qualité** : GitHub Actions, Jenkins, JUnit, Testcontainers, SonarQube, Trivy, DockerHub
**Supervision** : Prometheus, Grafana
**Gestion de projet** : Jira, Scrum, GitFlow
