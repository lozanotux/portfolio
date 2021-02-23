# Data Mapping

## Create index with mapping
- Let's create our second model: Plates
- We will use an **explicit mapping** to create the index.

```java
PUT /plates
{
  "mappings":{
    "properties":{
      "name":{ "type":"text" },
      "desc":{ "type":"text" },
      "lastMinuteOrders":{ "type":"integer" },
      "lastModification":{
        "properties": {
          "user":  { "type": "text" },
          "date":  { "type": "date" }
        }
      }
    }
  }
}
```

- Suppose it's time to add a new field
- We can update the mapping with **PUT**.

```java
PUT /plates/_mapping
{
  "properties":{
    "state":{ "type":"keyword" }
  }
}
```

## Let's create example plates

```java
PUT /plates/_doc/1
{
  "name": "Bowl Picante",
  "desc": "Pollo, salsa picante, frijoles, plátano y aguacate",
  "state": "activo",
  "lastMinuteOrders": 42,
  "lastModification": {
    "user": "rick@mail.com",
    "date": "2020-02-19"
  }
}
```

```java
PUT /plates/_doc/2
{
  "name": "Ensaladísima",
  "desc": "Aceitunas, cebolla, queso, tomate, aguacate (saludable)",
  "state": "activo",
  "lastMinuteOrders": 0,
  "lastModification": {
    "user": "rick@mail.com",
    "date": "2020-01-22"
  }
}
```
