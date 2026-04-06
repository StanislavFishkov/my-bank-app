# my-bank-app

## Преднастройка

1. Add "keycloak" alias into hosts as keycloak global address (browser & docker):
```
127.0.0.1 keycloak
```

## Сборка
2. Соберите проект через команду
```bash
./mvnw clean package
```

## Запуск
### С помощью Docker Compose
3. Соберем образы и запустим контейнеры в detached режиме:
```
   docker compose up -d
```

## Использование
4. Приложение стартует по умолчанию на порте 8080.