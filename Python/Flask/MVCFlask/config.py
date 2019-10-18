""" App configuration.
"""
from os import environ


class DevelopmentConfig:
    """ Set Flask configuration for development environment.
    """
    # General Config
    PORT = 5000
    SECRET_KEY = "MySuperSecretKey0123456789"
    DEBUG = True
    DEVELOPMENT = True

    # DataBase
    SQLALCHEMY_DATABASE_URI = "sqlite:///db.sqlite3"
    """ Deprecation Warning: SQLALCHEMY_TRACK_MODIFICATIONS adds significant overhead
        and will be disabled by default in the future.
    """
    SQLALCHEMY_TRACK_MODIFICATIONS = False


class ProductionConfig:
    """ Set Flask configuration for production environment.
    """
    # General Config
    SECRET_KEY = environ.get('SECRET_KEY')
    FLASK_ENV = environ.get('FLASK_ENV')
    DEBUG = False

    # Static Assets
    STATIC_FOLDER = environ.get('STATIC_FOLDER')
    TEMPLATES_FOLDER = environ.get('TEMPLATES_FOLDER')