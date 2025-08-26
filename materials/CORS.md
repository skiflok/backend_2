# CORS

## what is CORS

**CORS (Cross-Origin Resource Sharing)** — это механизм безопасности в браузерах, который ограничивает веб-страницам (
клиентскому JavaScript) возможность делать запросы к ресурсам на другом домене (origin).

---

## Почему CORS нужен

1. **Same-Origin Policy**
   По умолчанию браузер разрешает скриптам на странице обращаться только к тому же origin (протокол + хост + порт), с
   которого они были загружены.

2. **Cross-Origin Запросы**
   Когда JavaScript пытается обратиться к API на другом origin, браузер сначала проверяет, разрешил ли сервер такой
   запрос. Если не разрешил — блокирует.

---

## Как это работает

1. **Preflight запрос**
   Для «сложных» запросов (не GET/POST с простыми заголовками) браузер сначала шлёт `OPTIONS` запрос на тот же URL, с
   заголовком

   ```
   Origin: http://localhost:8080
   Access-Control-Request-Method: GET
   ```
2. **Ответ сервера**
   Сервер отвечает с заголовками:

   ```
   Access-Control-Allow-Origin: http://localhost:8080
   Access-Control-Allow-Methods: GET,POST
   Access-Control-Allow-Headers: Content-Type
   ```

   Если браузер видит, что нужные заголовки разрешают запрос — он выполняет настоящий `GET` или `POST`. Иначе —
   блокирует.

---

## `CorsGlobalConfig` в Spring WebFlux

Когда в твоём приложении реактивный стек (`spring-boot-starter-webflux`), ты создаёшь бин `CorsWebFilter`, который
автоматически перехватывает все входящие запросы и добавляет нужные CORS-заголовки.

```java

@Configuration
public class CorsGlobalConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        // 1) Создаём политику
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");   // разрешаем всё (*), можно указать конкретный домен
        config.addAllowedMethod("*");   // все HTTP-методы: GET, POST, PUT, DELETE...
        config.addAllowedHeader("*");   // все заголовки

        // 2) Регистрируем её на все пути /**  
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        // 3) Возвращаем фильтр, который применит политику
        return new CorsWebFilter(source);
    }
}
```

* **`CorsWebFilter`** автоматически обрабатывает и `OPTIONS` preflight, и добавляет ответные заголовки на все запросы.
* Если ты хочешь более тонко настраивать (например, разрешить только `https://example.com` или только метод `GET`),
  меняешь `addAllowedOrigin` или `addAllowedMethod`.

---

## CORS в Spring MVC

Если вместо WebFlux у тебя классический Spring MVC (`spring-boot-starter-web`), то конфиг чуть отличается:

```java

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")          // на все пути
                .allowedOrigins("*")        // или конкретные домены
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
}
```

Spring автоматически добавит заголовки `Access-Control-Allow-*` в ответы.

---

## Итог

* **CORS** защищает браузер от непреднамеренной отправки данных на чужие сервера и контролирует, какие сайты могут
  обращаться к твоему API.
* **`CorsGlobalConfig` в WebFlux** — это способ глобально разрешить (или ограничить) cross-origin запросы, настроив
  `CorsWebFilter`.
* В классическом **Spring MVC** ты используешь `WebMvcConfigurer#addCorsMappings`.

Добавив такую конфигурацию, твой SSE-эндпоинт (`/kafka-sse` или любой другой) станет доступен JavaScript-клиентам,
запущенным из других origin, без блокировок CORS.
