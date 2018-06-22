#!/usr/bin/python3
from flask import Flask, jsonify, request
from Users import User
from database import Session, engine, Base

app = Flask(__name__)


@app.route('/user/add', methods=['POST'])
def add_user():

    try:
        # generate database schema
        Base.metadata.create_all(engine)

        # create a new session
        session = Session()

        name = request.args.get('name')
        age = request.args.get('age')
        dni = request.args.get('dni')
        ctry = request.args.get('country')

        # create an employee
        user = User(name, age, dni, ctry)

        # persists data
        session.add(user)

        # commit and close session
        session.commit()
        session.close()

        return 'User added'
    except Exception as error:
        return str(error)


@app.route('/user/delete', methods=['DELETE'])
def delete_user():
    try:
        # generate database schema
        Base.metadata.create_all(engine)

        # create a new session
        session = Session()

        i = request.args.get('id')

        session.query(User).filter(User.id == i).delete()

        # commit and close session
        session.commit()
        session.close()

        return 'User removed'
    except Exception as error:
        return str(error)


if __name__ == '__main__':
    app.run(debug=True)