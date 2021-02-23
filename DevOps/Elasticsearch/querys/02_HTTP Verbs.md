# HTTP Verbs (FASTER)
- Let's take a look at the use of HTTP verbs within ElasticSearch

## **GET**
- Get a document.
- Perform a search.
- View the index mapping.
- Etc.

```java
GET /users/_search
```

```java
GET /users/_doc/1
```

```java
GET /users/_source/1
```

```java
GET /users/_mapping
```

- The mapping is the definition of how documents are to be stored within an index.
- ElasticSearch creates it **by default** if not specified.

## **POST / PUT**

- You must have the documents created for the **users** *index*.
- Let's update user 1: **age=60**

```java
PUT /users/_doc/1
{
    "age": 60
}
```

- Now let's consult the document

```java
GET /users/_source/1
```

- The operation is an **indexing**, not an **update**.
- To update a part of the document we do a **POST** to **`_update`**.

```java
POST /users/_update/1
{
	"doc": {
	    "name": "Rick",
	    "last_name": "Sanchez",
	    "age": 50
	}
}
```

```java
GET /users/_source/1
```

## **DELETE**
- Delete **documents or indexes**.
- Let's delete the document with ID 1

```java
DELETE /users/_doc/1
```

- Let's create a **test-index** to be able to delete it

```java
POST /test_index/_doc
{
	"name": "deprecated"
}
```

```java
DELETE /test_index
```
- **Acknowledged**: True
