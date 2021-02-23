# Range Queries and Aggregations

## Range Queries

### Search No. 1 - 3.5 <= rating <= 4.5
- We use **`_source`** to indicate which fields we want to get.

```java
GET /restaurants/_search
{
  "_source": ["name", "rating"],
  "query": {
    "range": {
      "rating": {
        "gt": 3.5,
        "lte": 4.5
      }
    }
  }
}
```

### Query No. 2 - Last modification from the second half of January to the end of February

```java
GET /restaurants/_search
{
  "_source": ["name", "lastModification.date"],
  "query": {
    "range": {
      "lastModification.date": {
        "gte": "2020-01-15",
        "lt": "2020-03-01"
      }
    }
  }
}
```

## Aggregations

### We get metrics from current restaurants

```java
GET /restaurants/_search
{
    "aggs" : {
        "averageRating": { "avg" : { "field" : "rating" } },
        "maximumRating": { "max" : { "field" : "rating" } },
        "minimumRating": { "min" : { "field" : "rating" } }
    }
}
```

### We indicate that for restaurants with no rating, default is **3.0** (SUBWAY)


```java
GET /restaurants/_search
{
    "aggs" : {
        "averageRating" : {
            "avg" : {
                "field" : "rating",
                "missing": 3.0
            } 
        }
    }
}
```
