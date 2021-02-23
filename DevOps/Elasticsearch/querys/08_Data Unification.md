# Project: Data Unification

## Step 1 - Query the current index mapping

```java
GET /restaurants/_mapping
```

```java
GET /plates/_mapping
```

## Step 2 - Update restaurants with plates and new fields

```java
PUT /restaurants/_mapping
{
  "properties":{
    "rating": { "type": "double" },
    "address": { "type": "text" },
    "plates": {
      "type": "nested",
      "properties": {
        "desc": { "type": "text" },
        "state": { "type": "keyword" },
        "name": { "type": "text" },
        "lastMinuteOrders": { "type": "integer" }
      }
    },
    "lastModification": {
      "properties": {
        "user": { "type": "text" },
        "date": { "type": "date" }
      }
    }
  }
}
```

## Step 3 - Updating existing restaurants with **POST and `_update`**

```java
POST /restaurants/_update/1
{
  "doc": {
    "rating": 4.22,
    "address": "Calle Primera, Barrio Centro",
    "plates": [
      {
        "name": "Bowl Picante",
        "desc": "Pollo, salsa picante, frijoles negros, plátano maduro y aguacate.",
        "state": "activo",
        "lastMinuteOrders": 42
      }
    ],
    "lastModification": {
      "user": "rick@mail.com",
      "date": "2020-02-19"
    }
  }
}
```

```java
POST /restaurants/_update/2
{
  "doc": {
    "rating": 3.75,
    "address": "Carrera Segunda, Barrio Occidental",
    "plates": [
      {
        "name": "Nachos XL",
        "desc": "Nachos con carne, guacamole, pico de gallo, salsa picante, queso y frijoles",
        "state": "activo",
        "lastMinuteOrders": 11
      }
    ],
    "lastModification": {
      "user": "jerry@mail.com",
      "date": "2020-03-01"
    }
  }
}
```
- **SUBWAY WITHOUT RATING**

```java
POST /restaurants/_update/3
{
  "doc": {
    "address": "Carrera Tercera, Barrio Norte",
    "plates": [
      {
        "name": "Ensaladísima",
        "desc": "Aceitunas, cebolla, queso, tomate, aguacate. (vegetariano y saludable)",
        "state": "activo",
        "lastMinuteOrders": 0
      }
    ],
    "lastModification": {
      "user": "rick@mail.com",
      "date": "2020-01-22"
    }
  }
}
```
