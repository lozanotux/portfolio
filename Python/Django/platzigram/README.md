# Platzigram

This project is a basic Django app to learn the basis. It's a clone of Instagram and cover the next topics:

* Views, Class-based Views
* Request object
* Template system
* Design Pattern MVT
* Models and the ORM
* Administration Dashboard
* Session Management (login, Logout, Signup)
* Middlewares
* Forms (and validations)
* Among others

## Screenshot

![alt text](url)

### Deploy And Execution (Development Environment)

It is a requirement to have the following:

* Python 3.4 or higher
* Git

1. Clone the source code of the project and change directory to it:
```
$ git clone $(platzigram_git_url)
$ cd platzigram/
```

2. Make a python virtual environment to install the binaries and dependencies:
```
$ python3 -m venv .env
```

3. Activate the virtual environment:
```
$ .env\Scripts\activate
```

4. Install the dependencies and requirements:
```
(.env) $ pip install -r requirements/dev.txt
```

5. Do a Sanity Check:
```
(.env) $ python manage.py migrate
(.env) $ python manage.py createsuperuser
```

6. Run the server and enjoy Platzigram:
```
(.env) $ python manage.py runserver
```

7. Open a web browser and navigate to:
> http://127.0.0.1:8000/


### QA and Prod Architecture

In both environment I have decided to establish the following architecture:

![alt text](url)

### Another Considerations

In the development stage of the project the following IDEs and plugins were used:

* Visual Studio Code (with plugins: git, python, jinja)
* Sublime Text 3 (with plugins: Afterglow-theme, SideBarEnhancements, Alignment, Anaconda)