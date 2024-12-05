# GIT

## GIT

### add tag

git tag -a v1.0.0 -m "1.0.0"

[2.6 Основы Git - Работа с тегами](https://git-scm.com/book/ru/v2/%D0%9E%D1%81%D0%BD%D0%BE%D0%B2%D1%8B-Git-%D0%A0%D0%B0%D0%B1%D0%BE%D1%82%D0%B0-%D1%81-%D1%82%D0%B5%D0%B3%D0%B0%D0%BC%D0%B8)

### как заменить пользователя в комитах гита, конкретного пользователя во всех коммитах

Чтобы заменить пользователя в коммитах Git, вы можете использовать команду `git rebase` в сочетании с `git commit --amend`. Однако, если вам нужно изменить автора во всех коммитах, лучше использовать `git filter-branch` или `git filter-repo`. Вот как это сделать с помощью обоих методов:

### Метод 1: Использование `git filter-branch`

1. Откройте терминал и перейдите в ваш репозиторий.
2. Выполните следующую команду, заменив `OLD_EMAIL` на старый адрес электронной почты, а `NEW_NAME` и `NEW_EMAIL` на новое имя и адрес электронной почты:

   ```bash
   git filter-branch --env-filter '
   OLD_EMAIL="old@example.com"
   CORRECT_NAME="New Name"
   CORRECT_EMAIL="new@example.com"

   if [ "$GIT_COMMITTER_EMAIL" = "$OLD_EMAIL" ]
   then
       export GIT_COMMITTER_NAME="$CORRECT_NAME"
       export GIT_COMMITTER_EMAIL="$CORRECT_EMAIL"
   fi
   if [ "$GIT_AUTHOR_EMAIL" = "$OLD_EMAIL" ]
   then
       export GIT_AUTHOR_NAME="$CORRECT_NAME"
       export GIT_AUTHOR_EMAIL="$CORRECT_EMAIL"
   fi
   ' --tag-name-filter cat -- --branches --tags
   ```

3. После выполнения команды вам может понадобиться принудительно обновить удаленный репозиторий:

   ```bash
   git push --force --all
   git push --force --tags
   ```

### Метод 2: Использование `git filter-repo`

`git filter-repo` является более современным и эффективным инструментом, чем `git filter-branch`. Если у вас его нет, вы можете установить его, следуя инструкциям в [документации](https://github.com/newren/git-filter-repo).

1. Установите `git filter-repo`, если он еще не установлен.
2. Выполните следующую команду:

   ```bash
   git filter-repo --mailmap <(echo "New Name <new@example.com> <old@example.com>")
   ```

3. Как и в предыдущем методе, вам может понадобиться принудительно обновить удаленный репозиторий:

   ```bash
   git push --force --all
   git push --force --tags
   ```

### Важно

- Будьте осторожны с изменением истории коммитов, особенно если вы работаете в команде. Это может привести к конфликтам, если другие разработчики уже синхронизировали свои репозитории с удаленным.
- Рекомендуется сделать резервную копию вашего репозитория перед выполнением этих операций.

### а только для конкретной ветки?

Да, вы можете использовать `git filter-branch` для изменения коммитов только в конкретной ветке. Для этого вам нужно указать имя ветки в конце команды. Вот как это сделать:

### Пример команды для конкретной ветки

Допустим, вы хотите изменить коммиты только в ветке `my-branch`. Ваша команда будет выглядеть следующим образом:

```bash
git filter-branch --env-filter '
OLD_EMAIL="old@example.com"
CORRECT_NAME="New Name"
CORRECT_EMAIL="new@example.com"

if [ "$GIT_COMMITTER_EMAIL" = "$OLD_EMAIL" ]
then
    export GIT_COMMITTER_NAME="$CORRECT_NAME"
    export GIT_COMMITTER_EMAIL="$CORRECT_EMAIL"
fi
if [ "$GIT_AUTHOR_EMAIL" = "$OLD_EMAIL" ]
then
    export GIT_AUTHOR_NAME="$CORRECT_NAME"
    export GIT_AUTHOR_EMAIL="$CORRECT_EMAIL"
fi
' --tag-name-filter cat -- my-branch
```

### Обратите внимание

1. **Убедитесь, что вы находитесь в нужном репозитории**: Перед выполнением команды перейдите в каталог вашего репозитория.
2. **Создайте резервную копию**: Изменение истории коммитов может привести к потере данных, поэтому рекомендуется создать резервную копию вашего репозитория перед выполнением этой команды.
3. **Принудительное обновление**: После выполнения команды вам может понадобиться принудительно обновить удаленный репозиторий:

   ```bash
   git push --force origin my-branch
   ```

Если у вас есть дополнительные вопросы или вам нужна помощь, не стесняйтесь спрашивать!

