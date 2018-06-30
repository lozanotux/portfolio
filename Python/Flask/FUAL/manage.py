from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask_script import Manager
from flask_migrate import Migrate, MigrateCommand
from models import db

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///fual.db'

migrate = Migrate(app, db)

manager = Manager(app)
manager.add_command('db', MigrateCommand)


name = db.Column(db.String(128))


if __name__ == '__main__':
    manager.run()
