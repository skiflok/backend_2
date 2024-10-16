# Swagger

## integrations

### как добавить сваггенр к контроллеру что бы создавались доки

Чтобы добавить Swagger к вашему контроллеру в приложении Spring, вы можете использовать библиотеку **Springfox** или **Springdoc OpenAPI**. Оба инструмента позволяют автоматически генерировать документацию для вашего REST API. Ниже приведены шаги для настройки Swagger с использованием обоих подходов.

### 1. Использование Springfox

#### Шаг 1: Добавьте зависимости

Если вы используете Maven, добавьте следующие зависимости в ваш `pom.xml`:

```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```

Если вы используете Gradle, добавьте следующую зависимость в ваш `build.gradle`:

```groovy
implementation 'io.springfox:springfox-boot-starter:3.0.0'
```

#### Шаг 2: Настройка Swagger

Создайте класс конфигурации для Swagger:

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yourpackage.controller")) // Укажите пакет с вашими контроллерами
                .paths(PathSelectors.any())
                .build();
    }
}
```

#### Шаг 3: Аннотации в контроллере

Добавьте аннотации Swagger к вашему контроллеру и методам:

```java
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Client API")
public class ClientController {

    @GetMapping("/clients")
    @ApiOperation(value = "Get all clients", response = ClientDto.class)
    public List<ClientDto> getAllClients() {
        // Ваш код
    }
}
```

#### Шаг 4: Доступ к документации

После запуска вашего приложения вы сможете получить доступ к Swagger UI по адресу:

```
http://localhost:8080/swagger-ui/
```

### 2. Использование Springdoc OpenAPI

#### Шаг 1: Добавьте зависимости

Если вы используете Maven, добавьте следующую зависимость в ваш `pom.xml`:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
    <version>1.6.6</version>
</dependency>
```

Если вы используете Gradle, добавьте следующую зависимость в ваш `build.gradle`:

```groovy
implementation 'org.springdoc:springdoc-openapi-ui:1.6.6'
```

#### Шаг 2: Аннотации в контроллере

Добавьте аннотации OpenAPI к вашему контроллеру и методам:

```java
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Client API", description = "Operations related to clients")
public class ClientController {

    @GetMapping("/clients")
    @Operation(summary = "Get all clients", description = "Returns a list of all clients")
    public List<ClientDto> getAllClients() {
        // Ваш код
    }
}
```

#### Шаг 3: Доступ к документации

После запуска вашего приложения вы сможете получить доступ к Swagger UI по адресу:

```
http://localhost:8080/swagger-ui.html
```

### Заключение

Оба подхода позволяют легко интегрировать Swagger в ваше приложение Spring и автоматически генерировать документацию для вашего API. Выбор между Springfox и Springdoc OpenAPI зависит от ваших предпочтений и требований проекта. Springdoc OpenAPI является более современным и поддерживает OpenAPI 3.0, в то время как Springfox в основном поддерживает OpenAPI 2.0.

### Вот рабочий вариант

[Генерация OpenAPI из Spring Boot MVC](https://habr.com/ru/articles/814061/)
