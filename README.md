# ShopAPI

* Тематика магазина: магазин бытовой техники.

## Запуск

<details>
  <summary>Описание команд запуска</summary>

* `mvn clean install -DskipTests -X` установка в локальный репозиторий
* `mvn jib:dockerBuild` создание образа докер с помощью jib
* `docker compose up`
* `docker compose dowm`

</details>

### пересборка имейджа

<details>
  <summary>rebuild</summary>

* `docker compose down`
*`mvn clean install`
* `docker rmi backend`
* `mvn jib:dockerBuild`

</details>

## versions history

<details>
  <summary>Описание изменений версий</summary>

* v0.1.0 реализован основной функционал + апи + сваггер, postgress в докере
* v1.0.0 приложение перенесено в контейнер, сборка image через JIB
* v1.1.0 Добавлен NGINX. Реализован реверс прокси (прямой доступ к порту контейнера и бд оставлен для отладки). Маршрутизация /api -> на /api/v1
* 

</details>

## Swagger

<details>
  <summary>вызовы сваггера через реверс прокси</summary>

* http://localhost/api/v1
* http://localhost/docs
* http://localhost/docs/swagger-config

</details>

## Сущности

<details>
  <summary>Описание сущностей</summary>

```
// Клиент
client
{
    id
    client_name
    client_surname
    birthday
    gender
    registration_date
    address_id
}
```

```
// Товар
product
{
    id
    name
    category
    price
    available_stock // число закупленных экземпляров товара
    last_update_date // число последней закупки
    supplier_id
    image_id: UUID
}
```

```
// Поставщики
supplier
{
    id
    name
    address_id
    phone_number
}
```

```
// Изображения товаров
images
{
    id : UUID
    image: bytea
}
```

```
// Адреса

address 
{
    id
    country
    city
    street
}
```

</details>

## Реализованы виды HTTP запросов (GET, POST, PUT, DELETE, PATCH).

<details>
  <summary>Описание API</summary>

Реализованы HTTP запросы (GET, POST, PUT, DELETE, PATCH).

- Для клиентов:

    1) Добавление клиента (на вход подается json, соответствующей структуре, описанной сверху).

    2) Удаление клиента (по его идентификатору)

    3) Получение клиентов по имени и фамилии (параметры - имя и фамилия)

    4) Получение всех клиентов (В данном запросе необходимо предусмотреть опциональные параметры пагинации в строке
       запроса: limit и offset). В случае отсутствия эти параметров возвращать весь список.

    5) Изменение адреса клиента (параметры: Id и новый адрес в виде json в соответствии с выше описанным форматом)

- Для товаров:

    1) Добавление товара (на вход подается json, соответствующей структуре, описанной сверху).

    2) Уменьшение количества товара (на вход запросу подается id товара и на сколько уменьшить)

    3) Получение товара по id

    4) Получение всех доступных товаров

    5) Удаление товара по id

- Для поставщиков:

    1) Добавление поставщика (на вход подается json, соответствующей структуре, описанной сверху).

    2) Изменение адреса поставщика (параметры: Id и новый адрес в виде json в соответствии с выше описанным форматом)

    3) Удаление поставщика по id

    4) Получение всех поставщиков

    5) Получение поставщика по id

- Для изображений:

    1) добавление изображения (на вход подается byte array изображения и id товара).

    2) Изменение изображения (на вход подается id изображения и новая строка для замены)

    3) Удаление изображения по id изображения

    4) Получение изображения конкретного товара (по id товара)

    5) Получение изображения по id изображения

</details>

## Swagger спецификация

> http://localhost:9999/swagger/index.html

## TODO

- [x] Настроить обратное проксирование на порт своего приложения.


- [ ] Настроить Nginx для работы web-приложения в части маршрутизации:
    - [x] Настроить маршрутизацию /api -> на /api/v1.
    - [ ] По пути /api/v1 выдавать swagger.
    - [ ] Настроить раздачу статики по пути /. В корне раздачи статики поместить 2 файла: index.html и image.png.
    - [ ] Настроить /admin на pgAdmin — GUI СУБД POSTGRES.
    - [ ] Настроить /status на отдачу страницы статуса сервера Nginx (nginx status).


- [ ] Настроить Nginx в части балансировки:

    - [ ] Запустить еще 2 инстанса бэкенда на других портах с правами доступа в базу данных только на чтение и настроить
      балансировку GET запросов к /api/v1 (/api/v2) в NGINX на 3 бэкенда в соотношении 2:1:1, где первый — основной
      бэкенд-сервер.


- [ ] Настроить кеширование (для всех GET-запросов, кроме /api).


- [ ] Настроить gzip-сжатие в Nginx. Сжатие не должно распространяться на медиа-типы (jpeg, png и т. д.).

- [ ] Настроить HTTPS на локальном устройстве.

    - [ ] Создать доменное имя в локальном DNS-сервере. У каждого компьютера есть локальное DNS-хранилище, в котором
      можно прописать собственное название сайта и по какому адресу этот адрес резолвится.

    - [ ] Создать самоподписанный сертификат с использованием openssl для созданного доменного имени и привязать его в
      Nginx-конфиге.

    - [ ] Настроить reverse proxy на запущенное приложение.
