# Single-app OAuth2 roles demo

Один Spring Boot процесс для учебного сравнения трёх ролей:

1. **Authorization Server / JWT issuer** — Spring Authorization Server выпускает JWT и публикует JWK metadata.
2. **OAuth2 Client** — browser login через Authorization Code, получает access token.
3. **Resource Server** — принимает Bearer JWT и защищает `/api/user/**`.

Это учебный вариант, намеренно объединяющий роли в одном приложении. В реальной архитектуре Authorization Server, Client и Resource Server обычно являются отдельными приложениями/сервисами.

Полная инструкция и безопасный walkthrough находятся в `start.txt`.

## Важно о localhost

Issuer, redirect URL и client registration настроены на `http://127.0.0.1:8080`. Используйте этот адрес везде (не смешивайте с `localhost`), чтобы совпали issuer и redirect URI.

## Запуск

```bash
gradle bootRun
```

Открой http://127.0.0.1:8080, войди через локальную форму `alice` / `alice-local-only`, затем начни OAuth2 Client flow по ссылке из главного JSON. После callback client получает access token. Resource Server endpoint доступен на `/api/user/me` только с Bearer токеном.

Примерное локальное учебное приложение, не production-конфигурация. Не используй встроенные пароли или signing key вне демо.
