# Лабораторная работа по базам данных №3

Бенчмарк был написан на Kotlin. Поэтому тестировались библиотеки/фреймворки для JVM языков:
- JDBC (Native SQL queries)
- Spring JDBC (Native SQL queries)
- Hibernate (ORM)
- Spring Data Jpa (ORM)
- MyBatis Framework (ORM)

## Запуск бенчмарка

1) Клонируем репозиторий командой:
```
git clone https://github.com/IlyaSekunov/database_laba_3.git
```

2) Для работы бд понадобится docker image, который можно скачать по ссылке: https://hub.docker.com/repository/docker/ilyasekunov/preloaded_db/general

3) Далее запускаем контейнер командой:
```
docker container run -d -p 5433:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres --name db preloaded_db:latest
```

4) Далее в корневой папке проекта запускаем приложение командой:
```
./gradlew bootRun
```

Чтобы конфигурировать бенчмарк в папке с проектов должен быть файл <b>config.json</b>:
```json
// config.json
{
    "tests-count": , // количество тестов на каждую библиотеку и запрос.
    "libraries": [ // библиотеки для теста
        {
            "name": , // имя библиотеки
        }
        ...
    ]
}
```
Доступны следующие имена библиотек:
1) jdbc
2) spring-jdbc
3) hibernate
4) spring-data-jpa
5) my-batis

В противном случае программа выдаст UnknownLibraryException

## Результаты

В зависимости от количества тестов, указанных в конфиге каждая библиотека тестируется на каждом запросе определенное количество раз, берется среднее по запросу и идет в результат.

Реузльтат работы бенчмара лежит в папке <b>results</b>

Если посмотрим на результаты, то можно увидеть следующую картину: в целом все библиотеки на данных запросах работают почти одинаково, на 2-м, 3-м и 4-м запросе все библиотеки выдают почти одинаковый результат работы (на моем пк - 0.2 c., 0.65 с., 0.85 с. соответсвенно). Больше всего разница заметна на 1-м запросе: как и ожидалось быстрее всех отрабатывает самый низкоуровневый способ связи с базами данных - JDBC, далее идет надстройка над JDBC - Spring JDBC, далее ORM-фреймворки: Hibernate, Spring Data Jpa и MyBatis.

## JDBC

Работа с самым низкоуровневым API для SQL приносит некоторые неудобства - ручной контроль соединений с базой данных (установление, закрытие), необходимость ручного парсинга результатов запроса. Это все может привести к непридвиденным багам (небезопасно). Данные минусы очень сильно заметны особенно при масштабировании приложения. Поэтому данный способ практически никто уже не использует отдельно в крупных проектах.

Однако низкоуровневость позволяет более гибкую конфигурацию и скорость выполнения запросов (особенно это заметно на 1-м запросе).

## Spring JDBC

Web-фреймворк Spring предоставляет тонкую надстройку над обычным JDBC. Теперь не нужно саморучно контролировать соединения с БД (Spring предоставляет потокобезопасное использование и переиспользование соединений); Spring предоставляет новый API для работы с БД, упрощающий запросы и предотвращающий возможность SQL-инъекций. Помимо этого это все еще является низкоуровневым способом для работы с БД, что все еще не очень удобно в использовании.

В то же время скорость работы в среднем данного решения будет медленнее обыкновенного JDBC, но быстрее ORM-аналогов.

## Hibernate

Наиболее популярный ORM-фреймворк для Java предоставляет реализацию стандартов Java JPA (Java Persistence API). На ступень выше уровень абстракции позволяет пользователю (посредством аннотация и XML сонфигураций) связывать сущности (entity) с реальными SQL таблицами. Помимо этого Hibernate предоставляет автоматическое и гибко конфигурируемое управление транзакциями. Более высокий уровень абстракции позволяет заметно сократить количество кода, что уже очень удобно.

Однако за облегчение использования приходится платить скоростью запросов. Заметно становится на 1-м запросе, на остальных разница минимальна (возможно не очень удачные запросы для того, чтобы заметить разницу).

## Spring Data Jpa

Еще более высокий уровень абстракции позволяется также связывать сущности с таблицами. Помимо этого Spring Data Jpa предоставляет возможность создания умных CRUD-репозиториев для выполнения запросов и автоматического маппинга с классами-сущностями (однако в данной лабе здесь такие репозитории не уместны из-за специфичности возвращаемых запросами данных, что только бы усложнило конфигурацию).

В целом, также как и Hibernate, высокий уровень абстракции ведет к уменьшению производительности, что опять же заметно более на 1-м запросе.

## MyBatis Framework

MyBatis Framework - более экзотическое решение ORM парадигмы для Java (Hibernate используют подавляющее большинство пользователей). Однако данный фремворк реализуют ORM немного по-другому: вместо связи конкретной таблицы с классом-сущности MyBatis маппит отдельный запрос SQL к возвращаемому значению, что в теории должно улучщить производительность (MyBatis позиционируется как более быстрая версия Hibernate).

Однако на практике происходит какая-то мистика - на 1-м запросе MyBatis справился аж за целых 8 секунд, что прям очень плохо по сравнению с аналогами (почему такое странное поведение у данного решения только богу ясно). На остальных же запросах MyBatis ведет себя почти так же как и другие ORM аналоги. 

Но если не брать в расчет странность времени 1 запроса, то конфигурация и использование MyBatis очень приятное и удобное (по удобству обходит всех конкурентов данных сравнений).

## Личный топ решений
1) Spring Data Jpa:
- удобство - 9
- скорость - 8
2) MyBatis:
- удобство - 10
- скорость - 7
3) Hibernate:
- удобство - 8
- скорость - 8
4) Spring JDBC:
- удобство - 7
- скорость - 9 
5) JDBC
- удобство - 5
- скорость - 10