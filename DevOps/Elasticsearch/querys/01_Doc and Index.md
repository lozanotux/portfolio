# Documents and Indexes

## Create our first model: Users

```javascript
PUT /users/_doc/1
{
  "name": "Rick",
  "last_name": "Sanchez"
}
```

## Create another user with POST

```java
POST /usuarios/_doc?refresh
{
  "name": "Morty Smith"
}
```

- ID **self-generated**
- With **POST** we don't have to indicate an ID, while with **PUT** yes, we do.

- `?refresh` is used to have changes immediately (without waiting 1 sec.)
- Not recommended for **performance**

## To be considered
- You did not create an index explicitly
- ElasticSearch **by default** creates one if it doesn't exist
- You can **add or omit** fields when creating a document

## Let's look at how to create several users at the same time
- You are going to create the file [users.json](../users.json)

- In Postman, you select **binary**
- Then you select the file **users.json**
- Then you send a **POST** to **`_bulk`**

```java
POST /usuarios/_bulk
```
