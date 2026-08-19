# 📚 Library API

API REST développée avec **Spring Boot** pour gérer une bibliothèque de livres.

L'application permet actuellement de réaliser les principales opérations CRUD sur les livres :

* Créer un livre
* Récupérer tous les livres
* Récupérer un livre par son ID
* Modifier un livre
* Supprimer un livre

---

## 🛠️ Technologies utilisées

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* Jakarta Validation
* Lombok
* Base de données relationnelle
* Maven

---

## 📁 Structure du projet

```text
src/
└── main/
    └── java/
        └── com/
            └── samba/
                └── library/
                    └── book/
                        ├── controllers/
                        │   └── BookRestController.java
                        │
                        ├── dto/
                        │   └── BookDTO.java
                        │
                        ├── model/
                        │   ├── BookEntity.java
                        │   └── exception/
                        │       └── BookCreationException.java
                        │
                        ├── persistence/
                        │   └── BookRepository.java
                        │
                        └── service/
                            └── BookService.java
```

---

# 🏗️ Architecture

Le projet utilise une architecture en plusieurs couches :

```text
Client
   │
   ▼
BookRestController
   │
   ▼
BookService
   │
   ▼
BookRepository
   │
   ▼
Base de données
```

### Controller

Le `BookRestController` reçoit les requêtes HTTP et renvoie les réponses au client.

### Service

Le `BookService` contient la logique métier :

* validation des données
* vérification de l'existence d'un livre
* création
* modification
* suppression

### Repository

Le `BookRepository` utilise Spring Data JPA pour communiquer avec la base de données.

---

# 📖 Modèle Book

Un livre possède les informations suivantes :

| Champ         | Type    | Description          |
| ------------- | ------- | -------------------- |
| `id`          | Long    | Identifiant unique   |
| `isbn`        | String  | ISBN-13 du livre     |
| `name`        | String  | Nom du livre         |
| `pages`       | Integer | Nombre de pages      |
| `year`        | Integer | Année de publication |
| `description` | String  | Description du livre |

---

# 🚀 Installation

## 1. Cloner le projet

```bash
git clone <URL_DU_REPOSITORY>
cd library
```

## 2. Installer les dépendances

Avec Maven :

```bash
./mvnw clean install
```

Sous Windows :

```bash
mvnw.cmd clean install
```

## 3. Configurer la base de données

Configurer les informations de connexion dans :

```text
src/main/resources/application.properties
```

Exemple :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Adapte ces paramètres à ta base de données.

---

# ▶️ Lancer l'application

Avec Maven :

```bash
./mvnw spring-boot:run
```

L'application sera disponible par défaut à :

```text
http://localhost:8080
```

---

# 🔌 API REST

La ressource principale est :

```text
/book
```

---

## 1. Créer un livre

### Requête

```http
POST /book
```

### Body

```json
{
    "isbn": "9780132350884",
    "bookName": "Clean Code",
    "bookPages": 464,
    "year": 2008,
    "description": "A Handbook of Agile Software Craftsmanship"
}
```

### Réponse

```http
201 Created
```

```json
{
    "id": 1,
    "isbn": "9780132350884",
    "bookName": "Clean Code",
    "bookPages": 464,
    "year": 2008,
    "description": "A Handbook of Agile Software Craftsmanship"
}
```

---

# 2. Récupérer tous les livres

### Requête

```http
GET /book/all
```

### Exemple

```text
GET http://localhost:8080/book/all
```

### Réponse

```json
[
    {
        "id": 1,
        "isbn": "9780132350884",
        "bookName": "Clean Code",
        "bookPages": 464,
        "year": 2008,
        "description": "A Handbook of Agile Software Craftsmanship"
    },
    {
        "id": 2,
        "isbn": "9780134494166",
        "bookName": "Clean Architecture",
        "bookPages": 432,
        "year": 2017,
        "description": "A Craftsman's Guide to Software Structure"
    }
]
```

---

# 3. Récupérer un livre par son ID

### Requête

```http
GET /book/{id}
```

### Exemple

```text
GET http://localhost:8080/book/1
```

### Réponse

```json
{
    "id": 1,
    "isbn": "9780132350884",
    "bookName": "Clean Code",
    "bookPages": 464,
    "year": 2008,
    "description": "A Handbook of Agile Software Craftsmanship"
}
```

Si le livre n'existe pas, une erreur est générée.

---

# 4. Modifier un livre

### Requête

```http
PUT /book/{id}
```

### Exemple

```text
PUT http://localhost:8080/book/1
```

### Body

```json
{
    "isbn": "9780132350884",
    "bookName": "Clean Code - Updated",
    "bookPages": 500,
    "year": 2024,
    "description": "Version mise à jour du livre"
}
```

### Fonctionnement

L'API :

1. récupère le livre grâce à son ID ;
2. vérifie qu'il existe ;
3. valide les nouvelles informations ;
4. modifie le `BookEntity` existant ;
5. sauvegarde les modifications ;
6. retourne le livre modifié.

---

# 5. Supprimer un livre

### Requête

```http
DELETE /book/{id}
```

### Exemple

```text
DELETE http://localhost:8080/book/1
```

Le service vérifie d'abord que le livre existe puis le supprime de la base de données.

Exemple de message :

```text
Le livre a été supprimé avec succès
```

---

# ✅ Validation des livres

Lors de la création ou de la modification d'un livre, plusieurs validations sont effectuées.

### ISBN

L'ISBN :

* ne doit pas être vide ;
* doit contenir 13 chiffres après nettoyage des espaces et tirets ;
* doit respecter le format ISBN-13.

### Nom

Le nom du livre :

* ne doit pas être `null` ;
* ne doit pas être vide.

### Nombre de pages

Le nombre de pages :

* ne doit pas être `null` ;
* doit être supérieur à `0`.

### Année

L'année :

* ne doit pas être `null` ;
* ne doit pas être supérieure à l'année actuelle.

---

# 🔢 Validation ISBN-13

Le projet possède une méthode permettant de vérifier le format ISBN-13.

Le processus commence par supprimer les espaces et les tirets :

```text
978-0-13-235088-4
```

devient :

```text
9780132350884
```

Puis le format est vérifié afin de s'assurer qu'il contient exactement 13 chiffres.

Le calcul complet du chiffre de contrôle ISBN-13 peut également être activé dans `BookService`.

---

# 📦 DTO

Le projet utilise des DTO pour séparer les données exposées par l'API des entités utilisées par la base de données.

## PostInput

Utilisé lors de la création d'un livre.

```text
isbn
bookName
bookPages
year
description
```

## PutInput

Utilisé lors de la modification d'un livre.

```text
isbn
bookName
bookPages
year
description
```

## PostOutput

Utilisé pour retourner les informations d'un livre au client.

```text
id
isbn
bookName
bookPages
year
description
```

---

# 🧪 Tester avec Postman

Les principales requêtes à tester sont :

```text
POST    http://localhost:8080/book
GET     http://localhost:8080/book/all
GET     http://localhost:8080/book/1
PUT     http://localhost:8080/book/1
DELETE  http://localhost:8080/book/1
```

Pour les requêtes `POST` et `PUT`, utiliser :

```text
Body
  → raw
  → JSON
```

et envoyer un JSON correspondant au DTO attendu.

---

# 📌 CRUD

| Opération | HTTP   | Endpoint     |
| --------- | ------ | ------------ |
| Créer     | POST   | `/book`      |
| Lire tous | GET    | `/book/all`  |
| Lire un   | GET    | `/book/{id}` |
| Modifier  | PUT    | `/book/{id}` |
| Supprimer | DELETE | `/book/{id}` |

---

# 🎯 Objectif du projet

Ce projet a pour objectif de mettre en pratique la création d'une API REST avec Spring Boot, notamment :

* les Controllers REST ;
* les Services ;
* les Repositories ;
* Spring Data JPA ;
* les DTO ;
* la validation des données ;
* les exceptions ;
* les opérations CRUD ;
* les réponses HTTP ;
* la communication avec une base de données.

---

## 👨‍💻 Auteur

**Samba Gueye**

Projet réalisé dans le cadre de l'apprentissage de **Spring Boot / API REST**.
