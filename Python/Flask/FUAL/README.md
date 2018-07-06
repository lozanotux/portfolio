# FUAL
## Formulario Unico Alumnos

Proyecto de la facultad para la materia Gestion de Proyectos. Es una app que permite a los profesores ver el estado academico de sus alumnos filtrando los mismos por medio de varios criterios. Incluye Registrar un usuario, Iniciar Sesión, Consultar Alumnos, Consultar Estado Academico, entre otros.

**__Como ejecutar FUAL?__**

**__On Linux:__**
```
$ pip3 install virtualenv
$ virtualenv venv --python=python3.5
$ source venv/bin/activate
$ pip3 install -r requirements.txt
$ python app.py
```

**__On Windows:__**
1. Download Python: https://www.python.org/downloads/release/python-370/
2. Install python
   1. Enable the option "Add python to Path variable": https://raw.githubusercontent.com/lozanotux/mis-cosas/master/Python/Flask/FUAL/installation/Python_Install.gif
   2. Make sure enable pip installation 
3. Clone the FUAL repo: https://gitlab.com/gmenendez/fual.git
4. For Windows OS, remove dependency "psycopg2==2.7.4" from requirements.txt
5. Ejecutar los siguientes comandos:
```
   $ pip install virtualenv
   $ cd C:/Path/to/FUAL
   $ virtualenv venv --python=C:\Users\jlozanop\AppData\Local\Programs\Python\Python37\python.exe
   $ venv\Scripts\activate.bat
   $ pip install -r requirements.txt
   $ python app.py
```
5. The second time only run this commands:
```
   $ cd C:/Path/to/FUAL
   $ venv\Scripts\activate.bat
   $ python app.py
```
6. Access to FUAL opening the next URI in a Web Browser: http://localhost:5000/