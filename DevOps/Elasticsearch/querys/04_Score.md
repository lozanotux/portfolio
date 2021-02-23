# Scoring (FASTER)

## Let's add a new plate

```java
PUT /plates/_doc/3
{
  "name": "Nachos XL",
  "desc": "Nachos con carne, guacamole, pico de gallo, salsa picante y queso",
  "state": "activo",
  "lastMinuteOrders": 11,
  "lastModification": {
    "user": "jerry@mail.com",
    "date": "2020-03-01"
  }
}
```

## Search No. 1: Nachos con queso

- Let's perform a simple search: **nachos con queso**
- For simple searches we use **simple_query_string**

```java
GET /plates/_search
{
  "query": {
    "simple_query_string" : { "query": "nachos con queso" }
  }
}
```

## Query No. 2: Bowl Pollo Saludable

```java
GET /plates/_search
{
  "query": {
    "simple_query_string" : { "query": "bowl pollo saludable" }
  }
}
```

## Query No. 3: Guacamole Picante

- You are going to modify the score by adding **fields**.
- With **^** you modify the weight of a field

```java
GET /plates/_search
{
  "query": {
    "simple_query_string" : {
        "query": "guacamole picante",
        "fields": ["name^2", "desc"]
    }
  }
}
```

### If **`name^2`**
1. Bowl Picante (Word `picante` in title)
2. Nachos XL

### If **`desc^2`**
1. Nachos XL (`guacamole` and `picante` in desc)
2. Bowl Picante
