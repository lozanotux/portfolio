from .. import db
""" UserMixin adds some attributes to the User class to
    help flask to do a login with our User class. Without
    UserMixin flask_login can't read or modify our User
    class in some sense.
"""
from flask_login import UserMixin


class User(UserMixin, db.Model):
    """ Here we define the model for User. It will contain
        some columns (representable in a database table).
    """
    id = db.Column(db.Integer, primary_key=True)
    email = db.Column(db.String(64), unique=True)
    password = db.Column(db.String(100))
    name = db.Column(db.String(100))