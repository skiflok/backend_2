### Тут будут ссылки и материалы по задаче

## БД

### Маппинг коллекций

[Маппинг коллекций](https://javarush.com/quests/lectures/questhibernate.level13.lecture00)
[@ManyToOne](https://javarush.com/quests/lectures/questhibernate.level13.lecture01)

[Liquibase vs Flyway](https://www.baeldung.com/liquibase-vs-flyway)
[Using Liquibase with Spring Boot Tutorial](https://medium.com/@cat.edelveis/using-liquibase-with-spring-boot-tutorial-79245a0b79a6)

### H2

[Spring Boot With H2 Database]{https://www.baeldung.com/spring-boot-h2-database}

### Spring boot configuration

[Spring Boot DataSource Configuration](https://howtodoinjava.com/spring-boot2/datasource-configuration/)

[Common Application Properties](https://docs.spring.io/spring-boot/appendix/application-properties/index.html#appendix.application-properties.core)

[properties-with-spring](https://www.baeldung.com/properties-with-spring)

## REST API

[Аннотированные контроллеры](https://javarush.com/quests/lectures/questspring.level05.lecture02)

[Building a RESTful Web Service](https://github.com/spring-guides/gs-rest-service)

[Error Handling for REST with Spring](https://www.baeldung.com/exception-handling-for-rest-with-spring)

[Problem Details Specification [RFC 7807]](https://howtodoinjava.com/spring-mvc/spring-problemdetail-errorresponse/)

[Пагинация и сортировка с использованием Spring Data JPA](https://www.baeldung.com/spring-data-jpa-pagination-sorting)

[Spring – Log Incoming Requests](https://www.baeldung.com/spring-http-logging)

## Swagger

### Swagger UI

[Генерация OpenAPI из Spring Boot MVC](https://habr.com/ru/articles/814061/)

### favicon

[favicon](https://www.favicon.cc/)

### проблема с маппингом енама в кастомный тип в постгрес

[Java Enums, JPA and PostgreSQL Enums](https://www.baeldung.com/java-enums-jpa-postgresql)
[Ошибка создания bean-компонента с именем «entityManagerFactory»: org/hibernate/dialect/PostgreSQL82Dialect](https://stackoverflow.com/questions/76493173/error-creating-bean-with-name-entitymanagerfactory-org-hibernate-dialect-pos)
[hypersistence-utils](https://github.com/vladmihalcea/hypersistence-utils)

### JIB GOOGLE

[Jib - Containerize your Maven project]{https://github.com/GoogleContainerTools/jib/blob/master/jib-maven-plugin/README.md}

### NGINX

[Алфавитный указатель переменных](http://nginx.org/ru/docs/varindex.html)

[Раскрываем возможности map в nginx](https://habr.com/ru/articles/231277/)

## Docker

[How to del docker image](https://stackoverflow.com/questions/65895928/how-to-delete-a-docker-image)

[healthcheck for pgAdmin](https://stackoverflow.com/questions/72272476/how-to-perform-docker-compose-healthcheck-for-pgadmin4)

## Linux

### create local DNS

* Отредактируйте файл /etc/hosts с правами администратора:
* `sudo nano /etc/hosts`
* add `127.0.0.1   s21backend`

### create certificate

```bash
sudo openssl req -x509 -nodes -days 7 -newkey rsa:2048 \
-keyout ./src/main/resources/cert/s21backend.key \
-out ./src/main/resources/cert/s21backend.crt
```

## PLANT UML

[plant uml site](https://plantuml.com/ru/)

## Auth service

[Сервер авторизации для микросервисов на Spring Boot](https://habr.com/ru/companies/otus/articles/681448/)

[Простой сервис аутентификации и авторизации по JWT на основе фильтров SpringSecurity](https://habr.com/ru/articles/781066/)

## GRPC

[gRPC DOCUMENTATION](https://grpc.io/)

[gRPC examples](https://github.com/grpc/grpc-java/tree/master/examples/example-tls)

[gRPC-Java - An RPC library and framework](https://github.com/grpc/grpc-java)

[grpc-spring](https://github.com/grpc-ecosystem/grpc-spring?tab=readme-ov-file#features)

[Введение в gRPC с Spring Boot](https://www.baeldung.com/spring-boot-grpc)

[Introduction to gRPC](https://www.baeldung.com/grpc-introduction)

[Java и gRPC: быстрый старт со Spring Boot | Преимущества, Настройка и Использование
](https://www.youtube.com/watch?v=Bj7g8voWJNU)

#### git

[grpc-spring-boot-3](https://github.com/hemicharly/grpc-spring-boot-3/blob/main/grpc-client/src/main/resources/application.yml)

## JWT

[jwtk](https://github.com/jwtk/jjwt#installation)

[Улучшенная аутентификация Java с помощью JSON Web Tokens (JWT)](https://www.baeldung.com/java-json-web-tokens-jjwt)

## Security

[secure-random](https://www.baeldung.com/java-secure-random)
