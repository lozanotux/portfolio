# Building a compound query (TAKE IT EASY)

```java
GET /plates/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "bool": {
            "should": [
              { "term": { "state": "activo"} },
              { "term": { "state": "agotado"} }
            ]
          }
        },
        {
          "bool": {
            "should": [
              {
                "match": {
                  "lastModification.user": "mail.com"
                }
              },
              {
                "match": {
                  "lastModification.user": "vendor.com"
                }
              }
            ]
          }
        },
        {
          "term": { "lastMinuteOrders": 0 }
        }
      ]
    }
  }
}
```

- Ensaladísima: **activo**, rsanchez@**mail.com**, **zero orders**
