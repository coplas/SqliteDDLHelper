SqliteDDLHelper
===============

A lightweight Android library for Sqlite DDL Generation


# Installation

Use it using jitpack.io

[![](https://jitpack.io/v/coplas/SqliteDDLHelper.svg)](https://jitpack.io/#coplas/SqliteDDLHelper)

https://jitpack.io/#coplas/SqliteDDLHelper

```groovy
  allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

```groovy
  dependencies {
	  compile 'com.github.coplas:SqliteDDLHelper:0.2.1'
  }
```

# Usage
Builds SQL DDL statement to create or alter SQLite database, I used it personally in my Android projects.


## Generate Create table statement
```java
final String sql = DDLBuilderFactory.createTable("items")
                .integer("id").pk().autoIncrement()
                .real("lat")
                .real("lon")
                .text("name")
                .bool("level2")
                .integer("price")
                .build();
```

## Generate alter table - add column
```java
 final String sql = DDLBuilderFactory.alterTable("items")
                .text("value")
                .build();
```

## Possible column types

```java
enum ColumnType {
    TEXT,
    NUMERIC,
    INTEGER,
    REAL,
    BOOLEAN,
    NONE;
}
```
