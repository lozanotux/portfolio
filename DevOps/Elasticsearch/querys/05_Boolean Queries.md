# Boolean Queries (TAKE IT EASY)

- Returns documents that match **combinations of occurrences** within it.

## Query using all clause types

```java
GET /plates/_search
{
  "query": {
    "bool" : {
      "must" : {
        "match" : { "desc" : "picante" }
      },
      "filter": {
        "term" : { "state" : "activo" }
      },
      "must_not" : {
        "term" : { "lastMinuteOrders" : 0 }
      },
      "should" : [
        { "match" : { "desc" : "aguacate" } },
        { "match" : { "desc" : "guacamole" } }
      ],
      "minimum_should_match": 1
    }
  }
}
```

- **must** and **filter** accompany **should**.
- We must indicate **minimum_should_match=1**.
- Two results (1.3802519, 1.0470967).
- Neither **state=activo** nor **lastMinuteOrders=0** contribute to the score.
- **must, filter and must_not** contain a single query (**we use {}**) 
- **should** contain multiple queries (**we use []**)

## We make the state affect the score.
- We move **state=activo** to **must** so that it affects the scoring.
- Now must be a list (**[]**)

```java
GET /plates/_search
{
  "query": {
    "bool" : {
      "must" : [
        { "match" : { "desc" : "picante" } },
        { "term" : { "state" : "activo" } }
      ],
      "must_not" : {
        "term" : { "lastMinuteOrders" : 0 }
      },
      "should" : [
        { "match" : { "desc" : "aguacate" } },
        { "match" : { "desc" : "guacamole" } }
      ],
      "minimum_should_match": 1
    }
  }
}
```

- Same two results (1.5137832, 1.1806281).
- Score **increased** because **state** now influences the score.
- Of the two queries in **should**, only one should match (**minimum_should_match=1**).

## **minimum_should_match=2**
- Let's add another query to **should** (**desc=pico de gallo**)

```java
GET /plates/_search
{
  "query": {
    "bool" : {
      "must" : [
        {"match" : { "desc" : "picante" } },
        {"term" : { "state" : "activo" } }
      ],
      "must_not" : {
        "term" : { "lastMinuteOrders" : 0 }
      },
      "should" : [
        { "match" : { "desc" : "aguacate" } },
        { "match" : { "desc" : "guacamole" } },
        { "match" : { "desc" : "pico de gallo" } }
      ],
      "minimum_should_match": 2
    }
  }
}
```

- **Nachos XL**: **guacamole** and **pico de gallo** in description
