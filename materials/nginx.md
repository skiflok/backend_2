# nginx

[docker image](https://hub.docker.com/_/nginx)

## config

[Обратный прокси-сервер NGINX](https://docs.nginx.com/nginx/admin-guide/web-server/reverse-proxy/)

<details>
<summary>revers proxy conf</summary>

```
events {}

http {
    upstream backend {
        server backend:9999;
    }

    server {
        listen 80;

        location / {
            proxy_pass http://backend;
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        }
    }
}

```

Этот конфигурационный файл Nginx задает правила для работы с обратным проксированием, перенаправляя запросы от клиентов
к сервису `backend`, работающему на порту `9999`.

### Разберем файл по частям:

#### 1. **`events {}`**

- Блок `events` необходим для базовой работы Nginx, даже если он пустой.
- Он может содержать настройки обработки соединений, такие как `worker_connections`, которые ограничивают количество
  одновременных соединений.

---

#### 2. **`http {}`**

- Главный блок для настройки HTTP-серверов.
- В нем задаются все правила маршрутизации и проксирования.

---

#### 3. **`upstream backend`**

   ```nginx
   upstream backend {
       server backend:9999;
   }
   ```

- Определяет **группу серверов**, к которым Nginx будет перенаправлять запросы.
- `backend:9999`: имя контейнера Docker (или хоста) с портом, где работает ваше приложение.
- Этот блок абстрагирует настройку целевых серверов и позволяет гибко добавлять их в будущем (например, для балансировки
  нагрузки).

---

#### 4. **`server {}`**

   ```nginx
   server {
       listen 80;
   }
   ```

- Описывает отдельный HTTP-сервер.
- `listen 80`: сервер слушает запросы на порту `80` (стандартный HTTP-порт).

---

#### 5. **`location /`**

   ```nginx
   location / {
       proxy_pass http://backend;
       proxy_set_header Host $host;
       proxy_set_header X-Real-IP $remote_addr;
       proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
   }
   ```

- Указывает маршрут для запросов, пришедших на `/` (корневой путь).
- **`proxy_pass http://backend;`**: перенаправляет все запросы к группе серверов `backend` (определенной выше).
- **Заголовки:**
    - `proxy_set_header Host $host`: передает оригинальный хост, указанный клиентом.
    - `proxy_set_header X-Real-IP $remote_addr`: передает реальный IP-адрес клиента (полезно для логов).
    - `proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for`: добавляет цепочку IP-адресов, через которые прошел
      запрос.

---

### Итоговая работа

1. Клиент отправляет запрос на `http://<ваш-сервер>:80/`.
2. Nginx принимает запрос на порту `80` и перенаправляет его на `backend:9999`.
3. Настройки заголовков обеспечивают, что приложение `backend` получает дополнительную информацию о клиенте.

### Примечание

Если вы используете Docker Compose:

- Убедитесь, что сервисы `backend` и `nginx` находятся в одной сети.
- Имена контейнеров (`backend`) должны совпадать. Если это не так, используйте IP-адрес или измените
  `docker-compose.yml`, чтобы указать правильное имя хоста.

</details>

### Logs

`docker logs nginx`

### reload

Чтобы перезагрузить Nginx и применить изменения в конфигурации, используйте одну из следующих команд в зависимости от ситуации:

### 1. **Перезагрузка Nginx без прерывания работы (рекомендуется)**

```bash
sudo nginx -s reload
```
Эта команда отправляет сигнал **reload** текущему процессу Nginx. Сервер перезагрузит конфигурацию, не прерывая активные соединения.

---

### 2. **Перезапуск сервиса Nginx через systemd**
Если вы используете systemd, выполните:

```bash
sudo systemctl reload nginx
```
Эта команда аналогична `-s reload`, она обновляет конфигурацию без остановки сервера.

---

### 3. **Полный перезапуск Nginx**
Если вам необходимо полностью перезапустить Nginx (например, после установки новых модулей):

```bash
sudo systemctl restart nginx
```
**Важно:** Этот метод разрывает все активные соединения.

---

### 4. **Проверка конфигурации перед перезагрузкой**
Перед перезагрузкой всегда полезно проверить конфигурацию на наличие ошибок:

```bash
sudo nginx -t
```

Если конфигурация корректна, команда выведет:
```
nginx: the configuration file /etc/nginx/nginx.conf syntax is ok
nginx: configuration file /etc/nginx/nginx.conf test is successful
```

Используйте этот подход, чтобы избежать ошибок, которые могут привести к недоступности сервера.

### 

[Spring Boot Swagger through Nginx does not work correctly](https://stackoverflow.com/questions/75838196/spring-boot-swagger-through-nginx-does-not-work-correctly)
