# express-demo API

This is a simple CRUD example API using `expressjs`.

## **Build and Run**

1. To run this example locally, you need to install the dependencies first. So, execute the next command:
    ```bash
    $ npm install
    ```

2. Then, execute the node.js project running the next command:
    ```bash
    $ node index.js
    ```

    > **NOTE:** optionally you can use `nodemon` to develop new features and get a live process to watch for new changes. For this, you can run the command: `npx nodemon index.js`.

## **How to use this API?**

To use this API, you can consume its methods like this:

### **Resource:** _/api/courses_

- GET (get all courses): `curl localhost:3000/api/courses`
    ```bash
    expected result:
    [
        {
            "id": 1,
            "name": "course1"
        },
        {
            "id": 2,
            "name": "course2"
        },
        {
            "id": 3,
            "name": "course3"
        }
    ]
    ```

- GET (get a specific course): `curl localhost:3000/api/courses/1`
    ```bash
    expected result:
    {
        "id": 1,
        "name": "course1"
    }
    ```

- POST (add new course): `curl -X POST -d '{ "name": "new course" }' -H "Content-Type: application/json" localhost:3000/api/courses`
    ```bash
    expected result:
    {
        "id": 4,
        "name": "new course"
    }
    ```

- PUT (update a course): `curl -X PUT -d '{ "name": "updated course" }' -H "Content-Type: application/json" localhost:3000/api/courses/1`
    ```bash
    expected result:
    {
        "id": 1,
        "name": "updated course"
    }
    ```

- DELETE (delete a course): `curl -X DELETE localhost:3000/api/courses/1`
    ```bash
    expected result:
    {
        "id": 1,
        "name": "updated course"
    }
    ```