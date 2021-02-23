# Project: Final Directory Review

## Step 1 - We see all the data in our restaurant directory

```java
GET /restaurants/_search
```

- We have all the necessary fields
- Plates and Categories are nested objects of a Restaurant
- We have the basic data of the restaurant
- We store the information of the User who last modified the restaurant

## Step 2 - Filter the directory using data from Restaurants and Plates at the same time

- Get all the restaurants that:
1. Are located in the **Barrio Centro** or **Barrio Occidental**
2. Are **comida rápida** or sell **nachos**

```java
GET /restaurants/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "bool": {
            "should": [
              {
                "match": {
                  "address": "centro"
                }
              },
              {
                "match": {
                  "address": "occidental"
                }
              }
            ]
          }
        },
        {
          "bool": {
            "should": [
              {
                "nested": {
                  "path": "categories",
                  "query": {
                    "bool": {
                      "must": {
                        "term": {
                          "categories.name": "Comida Rápida"
                        }
                      }
                    }
                  }
                }
              },
              {
                "nested": {
                  "path": "plates",
                  "query": {
                    "bool": {
                      "must": {
                        "match": {
                          "plates.desc": "nachos"
                        }
                      }
                    }
                  }
                }
              }
            ]
          }
        }
      ]
    }
  }
}
```
