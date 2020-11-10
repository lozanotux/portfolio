# Shoppy

This Django project is an extremely simple eCommerce application to know the basis of Django

## Screenshot

![shoppy_screenshot](https://raw.githubusercontent.com/lozanotux/portfolio/master/Python/Django/Shoppy/images/screenshot.png)

### Deploy And Execution (Development Environment)

It is a requirement to have the following:

* Python 3.4 or higher
* Git

1. Change to the project directory:
```bash
$ cd Shoppy/
```

2. Make a python virtual environment:
```
$ python3 -m venv .env
```

3. Activate the virtual environment:
```
$ .env\Scripts\activate
```

4. Install the dependencies and requirements:
```
(.env) $ pip install -r requirements.txt
```

5. Do a Sanity Check and create an administrator user:
```
(.env) $ python manage.py migrate
(.env) $ python manage.py createsuperuser
```

6. Run the server and enjoy Shoppy:
```
(.env) $ python manage.py runserver
```

7. Open a web browser and navigate to:
> http://127.0.0.1:8000/