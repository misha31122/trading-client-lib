![Logo of the project](https://raw.githubusercontent.com/jehna/readme-best-practices/master/sample-logo.png)

# Name of the project

> Custom uuid generator

Служебная библиотека гнерации uuid версии v7, v6, v1

## Installing / Getting started

Подтяните в свой проект зависимость библиотека для uuid generator:

```xml

<dependency>
  <groupId>ru.vtb.obuf</groupId>
  <artifactId>obuf-custom-uuid-generator</artifactId>
  <version>${obuf-custom-uuid-generator.version}</version>
</dependency>
```

${obuf-custom-uuid-generator.version} - выберите самую последнюю актуальную версию в репозитории.

!!! За генерацию uuid отвечате библиотека :

```xml

<dependency>
  <groupId>com.fasterxml.uuid</groupId>
  <artifactId>java-uuid-generator</artifactId>
  <version>5.0.0</version>
</dependency>
```

Материал был взят отсюда https://www.baeldung.com/java-generating-time-based-uuids , пункт 5.

## Developing

После добавления библиотеки в свой проект, настройте свой entity следующим образом:

```java

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Example")
public class ExampleEntity {

  @Id
  @CustomUuidGenerator
  private UUID id;

  @Column(name = "something", nullable = false)
  private String something;
}
```

Аннотация @CustomUuidGenerator принимает параметры : V7, V6, V1. Каждая означает тип требемого UUID.
По умолчанию генерится V7

## Links

- исходный код библиотеки
  https://github.com/misha31122/trading-helping-lib/tree/master/custom-uuid-generator