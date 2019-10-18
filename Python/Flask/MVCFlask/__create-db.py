""" This script written in Python 3 is needed to create
    the neccessary database and tables. Execute it 
    before use the application.
"""
from application import db, create_app

db.create_all(app=create_app())