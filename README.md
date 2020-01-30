# CurrencyAPI

```
- mkdir db-docker
- cd db-docker
- touch docker-compose.yml
```

**Add the following in the docker-compose.yml file we just created:**

```JSON
version: '3'

services:

  mysql-development:
    image: mysql:8.0.17
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: currency_conversion_db
    ports:
      - "3306:3306"
```
**Clone the project and execute the below command**

```mvn spring-boot:run```
