# Серверная часть системы автоматизации учета услуг ЖКХ

Структура проекта

```
diploma
├──dataBase
│  ├──DatabaseFactory.kt
├──databaseMutationController
│  ├──ApartmentMutation.kt
│  ├──BranchMutation.kt
│  ├──CategoryMutation.kt
│  ├──CounterReferenceMutation.kt
│  ├──EmployeeMutation.kt
│  ├──MeasureReferenceMutation.kt
│  ├──OrganizationMutation.kt
│  ├──PaymentHistoryMutation.kt
│  ├──PositionMutation.kt
│  ├──ReadingsMutation.kt
│  ├──ServiceCollectionMutation.kt
│  ├──ServiceMutation.kt
│  ├──ServiceRecordMutation.kt
│  ├──TypeMutation.kt
│  ├──UserMutation.kt
├──graphQLShema
│  ├──SchemaBuilder.kt
├──model
│  ├──ApartmentModel.kt
│  ├──BranchModel.kt
│  ├──CategoryModel.kt
│  ├──CounterReferenceModel.kt
│  ├──EmployeeModel.kt
│  ├──MeasureReferenceModel.kt
│  ├──OrganizationModel.kt
│  ├──PaymentHistoryModel.kt
│  ├──PositionModel.kt
│  ├──ReadingsModel.kt
│  ├──ServiceCollectionModel.kt
│  ├──ServiceModel.kt
│  ├──ServiceRecordModel.kt
│  ├──TypeModel.kt
│  ├──UserModel.kt
├──utils
│  ├──getLocalDate.kt
│  ├──JwtConfig.kt
└──Application.kt
```

## dataBase

DatabaseFactory.kt — Файл с подключением базы данных с помощью HicariCP

## databaseMutationController

ApartmentMutation.kt — Описание методов взаимодействия с данными таблицы Apartment в базе данных

BranchMutation.kt — Описание методов взаимодействия с данными таблицы Branch в базе данных

CategoryMutation.kt — Описание методов взаимодействия с данными таблицы Category в базе данных

CounterReferenceMutation.kt — Описание методов взаимодействия с данными таблицы CounterReference в базе данных

EmployeeMutation.kt — Описание методов взаимодействия с данными таблицы Employee в базе данных

MeasureReferenceMutation.kt — Описание методов взаимодействия с данными таблицы MeasureReference в базе данных

OrganizationMutation.kt — Описание методов взаимодействия с данными таблицы Organization в базе данных

PaymentHistoryMutation.kt — Описание методов взаимодействия с данными таблицы PaymentHistory в базе данных

PositionMutation.kt — Описание методов взаимодействия с данными таблицы Position в базе данных

ReadingsMutation.kt — Описание методов взаимодействия с данными таблицы Readings в базе данных

ServiceCollectionMutation.kt — Описание методов взаимодействия с данными таблицы ServiceCollection в базе данных

ServiceMutation.kt — Описание методов взаимодействия с данными таблицы Service в базе данных

ServiceRecordMutation.kt — Описание методов взаимодействия с данными таблицы ServiceRecord в базе данных

TypeMutation.kt — Описание методов взаимодействия с данными таблицы Type в базе данных

UserMutation.kt — Описание методов взаимодействия с данными таблицы User в базе данных

## graphQLShema

SchemaBuilder.kt — Файл с реализацией мутаций и запросов GraphQL

## model

ApartmentModel.kt — Модель данных Apartment

BranchModel.kt — Модель данных Branch

CategoryModel.kt — Модель данных Category

CounterReferenceModel.kt — Модель данных CounterReference

EmployeeModel.kt — Модель данных Employee

MeasureReferenceModel.kt — Модель данных MeasureReference

OrganizationModel.kt — Модель данных Organization

PaymentHistoryModel.kt — Модель данных PaymentHistory

PositionModel.kt — Модель данных Position

ReadingsModel.kt — Модель данных Readings

ServiceCollectionModel.kt — Модель данных ServiceCollection

ServiceModel.kt — Модель данных Service

ServiceRecordModel.kt — Модель данных ServiceRecord

TypeModel.kt — Модель данных Type

UserModel.kt — Модель данных User

## utils

getLocalDate.kt — Файл получения и форматирования текущей даты

JwtConfig.kt — Файл с генерированием access и refresh токенов


Application.kt — Основной файл с необходимыми свойствами и параметрами для запуска сервера


Для запуска сервера Ktor необходима версия JDK - 18.0.1.0.
Для сборки, в командной строке, необходимо прописать команду «gradlew build» (из корня сервера). После того, как на сервере пройдет сборка, необходимо в той же директории прописать команду «gradlew run». После чего сервер запустится, а в консоли можно будет увидеть лог, в котором описываются все запросы, поступающие на сервер.

