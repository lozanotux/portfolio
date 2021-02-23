# Nested Queries (FAST)

## Restaurants Model
- We create the index with **name and categories** (one of them main)

```java
PUT /restaurants
{
  "mappings":{
    "properties":{
      "name":{ "type":"text" },
      "categories" : {
        "type" : "nested",
        "properties": {
          "name": { "type" : "keyword" },
          "main": { "type" : "boolean" }
        }
      }
    }
  }
}
```

## Let's add restaurants

```java
PUT /restaurants/_doc/1
{
  "name": "McDonalds",
  "categories": [
    { "name": "Comida Rápida", "main": true },
    { "name": "Desayunos", "main": false },
    { "name": "Hamburguesas", "main": false }
  ]
}
```

```java
PUT /restaurants/_doc/2
{
  "name": "Burger King",
  "categories": [
    { "name": "Comida Rápida", "main": false },
    { "name": "Hamburguesas", "main": true }
  ]
}
```

```java
PUT /restaurants/_doc/3
{
  "name": "Subway",
  "categories": [
    { "name": "Comida Saludable", "main": false },
    { "name": "Sándwiches", "main": true }
  ]
}
```

# Search No. 1: Category=Comida Saludable

```java
GET /restaurants/_search
{
  "query": {
    "nested": {
      "path": "categories",
      "query": {
        "bool": {
          "must": {
            "term": { "categories.name": "Comida Saludable" }
          }
        }
      }
    }
  }
}
```

# Query No. 2: Category=Comida Rápida 

```java
GET /restaurants/_search
{
  "query": {
    "nested": {
      "path": "categories",
      "query": {
        "bool": {
          "must": {
            "term": { "categories.name": "Comida Rápida" }
          }
        }
      }
    }
  }
}
```

# Query No. 3: Category=Comida Rápida, main=true


```java
GET /restaurants/_search
{
  "query": {
    "nested": {
      "path": "categories",
      "query": {
        "bool": {
          "must": [
            { "term": { "categories.name": "Comida Rápida" } },
            { "term": { "categories.main": true } }
          ]
        }
      }
    }
  }
}
```
