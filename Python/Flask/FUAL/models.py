from flask import Flask
from flask_migrate import Migrate
from flask_script import Manager
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///fual.db'

db = SQLAlchemy(app)
# db.init_app(app)
migrate = Migrate(app, db)


subjects = db.Table('subjects',
                    db.Column('students.id', db.Integer,
                              db.ForeignKey('students.id'),
                              primary_key=True),
                    db.Column('subject.id', db.Integer,
                              db.ForeignKey('subject.id'),
                              primary_key=True),
                    )


class User(db.Model):

    """Class user table representation."""

    __tablename__ = 'users'

    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(128))
    password = db.Column(db.String(300))
    email = db.Column(db.String(300))


class Student(db.Model):

    """Class Students Table representation."""

    __tablename__ = 'students'

    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(128))
    surname = db.Column(db.String(128))
    dni = db.Column(db.Integer)
    age = db.Column(db.Integer)
    nationality = db.Column(db.String(50))
    division = db.Column(db.String(2))
    lective_year = db.Column(db.Integer)
    subject_id = db.relationship('Subject', secondary=subjects,
                                 backref=db.backref('subjects',
                                                    lazy='dynamic'))


class Subject(db.Model):

    """Class subject Table representation."""

    __tablename__ = 'subject'

    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(128))


class AcademicState(db.Model):

    """Class Academic State table representation."""

    __tablename__ = 'AcademicStates'

    id = db.Column(db.Integer, primary_key=True)
    notes = db.Column(db.String(128))
    repeating = db.Column(db.String(10))
    status = db.Column(db.String(30))
    correlative = db.Column(db.Integer, default=False)
    student_name = db.Column(db.String(100), default=False)
    subject_name = db.Column(db.String(100), default=False)



if __name__ == '__main__':
    Manager.run()
