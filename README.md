# DirectoryModule
Справочники: Человек и Виза.

Колонки у справочника Человек: номер паспорта, имя, фамилия, дата рождения, национальность.

У Визы: идентификационный номер, дата выдачи, срок, категория, страна/страны (или шенген например), номер паспорта человека.

Подразумевается, что номер визы абсолютно уникален, то есть не найдется 2 визы с одинаковыми идентификационными номерами.

СУБД - PostgreSQL.

Схема:

```postgresql
CREATE TABLE Person
(
    passport_number varchar primary key check (passport_number <> ''),
    first_name      varchar not null check (first_name <> ''),
    last_name       varchar not null check (last_name <> ''),
    birth_date      date    not null,
    nationality     varchar not null check (nationality <> '')
);

CREATE TABLE Visa
(
    id                     bigint primary key,
    issue_date             date          not null,
    validity_period        decimal(3, 1) not null,
    type                   CHAR(1)       not null check (type between 'A' and 'D'),
    countries              VARCHAR       not null check (countries <> ''),
    person_passport_number varchar references Person (passport_number)
);
```

Пример заполнения:
```postgresql
INSERT INTO Person (passport_number, first_name, last_name, birth_date, nationality) VALUES
                ('MP1234567', 'Peter', 'Petrov', DATE '2002-12-12', 'BLR'),
                ('MP1234568', 'Peter', 'Volkov', DATE '2002-01-12', 'PL'),
                ('MP1234569', 'Peter', 'Ivanov', DATE '2002-12-19', 'RU'),
                ('MP1234577', 'Ivan', 'Petrov', DATE '2001-12-12', 'UKR'),
                ('MP1234587', 'Ivan', 'Ivanov', DATE '2000-01-12', 'BLR');

INSERT INTO Visa (id, issue_date, validity_period, type, countries, person_passport_number) VALUES
                (23446736464, DATE '2023-01-14', 1.5, 'C', 'Schengen area', 'MP1234567'),
                (84748594554, DATE '2023-01-14', 1.5, 'C', 'Schengen area', 'MP1234568'),
                (34434764455, DATE '2023-10-14', 0.5, 'A', 'Italy', 'MP1234569'),
                (66589658568, DATE '2023-09-14', 1.5, 'B', 'USA', 'MP1234587'),
                (47437477423, DATE '2023-01-14', 2.5, 'D', 'Japan', 'MP1234587');
```

Человек
|passport_number|first_name|last_name|birth_date|nationality|
|---|---|---|---|---|
|MP1234567|Peter|Petrov|2002-12-12|BLR|
|MP1234568|Peter|Volkov|2002-01-12|PL|
|MP1234569|Peter|Ivanov|2002-12-19|RU|
|MP1234577|Ivan|Petrov|2001-12-12|UKR|
|MP1234587|Ivan|Ivanov|2000-01-12|BLR|


Виза
|id|issue_date|validity_period|type|countries|person_passport_number|
|---|---|---|---|---|---|
|23446736464|2023-01-14|1.5|C|Schengen area|MP1234567|
|84748594554|2023-01-14|1.5|C|Schengen area|MP1234568|
|34434764455|2023-10-14|0.5|A|Italy|MP1234569|
|66589658568|2023-09-14|1.5|B|USA|MP1234587|
|47437477423|2023-01-14|2.5|D|Japan|MP1234587|
