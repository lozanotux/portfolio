"""
@title: FUAL formulario unico alumnos
@author: Menendez Gabriel
@description: web aplication, to registry students of institute
@year: 2018



filtros de busqueda
 por materia
 por nombre de alumno
 por año

"""
from flask import (Flask, request, render_template, session)
from sqlalchemy import exc, or_
from models import User, Student, db, AcademicState, Subject

app = Flask(__name__)
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///fual.db'

db.init_app(app)


@app.route("/")
def login():
    return render_template("login.html")


@app.route("/crearCuenta")
def account():
    return render_template("account.html")


@app.route("/cuenta", methods=['POST'])
def create_account():
    iemail = request.form['inputEmail']
    passwor = request.form['password']
    nameP = request.form['nombreProfesor']
    try:
        user = User(name=nameP, password=passwor, email=iemail)
        db.session.add(user)
        db.session.commit()
        return render_template("login.html")
    except Exception:
        return render_template("404.html",
                               message="No se pudo crear usuario")


@app.route("/main", methods=['POST'])
def main():
    email = request.form['inputEmail']
    password = request.form['password']
    try:
        ret = User.query.filter_by(email=email) \
                .filter_by(password=password).one()
    except exc.SQLAlchemyError:
        return render_template("404.html",
                               message="Por favor revisa el usuario y password que ingresaste, son erroneos")

    return render_template("main.html", name=ret.name)





@app.route("/statusAcademico/<student_name>")
def status(student_name):
    ret = AcademicState.query.filter_by(student_name=student_name).all()
    y = request.args.get('year')
    s = request.args.get('subject')
    return render_template("status.html", reto=ret, year=y, subject=s)


@app.route("/search", methods=['POST', 'GET'])
def search():
    """Search route by year and subject."""
    if request.method == 'POST':
        year = request.form['year']
        subject = request.form['subject']
        if len(year)>0 and len(subject)>0:
            students = Student.query.filter(Student.subject_id.any(name=subject))\
                            .filter(Student.lective_year.like(year)).all()

        if len(year)>0:
            students = Student.query.filter_by(lective_year=year).all()
            
        if len(subject)>0:
            students = Student.query.filter(Student.subject_id.any(name=subject)).all()
            
        if not(len(year)>0) and not(len(subject)>0):
            year = ''
            subject = ''
            students = Student.query.all()
            
        return render_template("search_results.html",
                                students=students,
                                year=year,
                                subject=subject)
    if request.method == 'GET':
        year = request.args.get('year')
        subject = request.args.get('subject')
        alu = []
        if len(year)>0 and len(subject)>0:
            students = Student.query.filter(Student.subject_id.any(name=subject))\
                            .filter(Student.lective_year.like(year)).all()
            for s in students:
                alu.append(s)

        if len(year)>0:
            students = Student.query.filter_by(lective_year=year).all()
            for s in students:
                alu.append(s)
                
        if len(subject)>0:
            students = Student.query.filter(Student.subject_id.any(name=subject)).all()
            for s in students:
                alu.append(s)
            
        if not(len(year)>0) and not(len(subject)>0):
            year = 'N/A'
            subject = 'N/A'
            students = Student.query.all()
            for s in students:
                alu.append(s)
            

        return render_template("search_results.html",
                                students=alu,
                                year=year,
                                subject=subject)

                           
if __name__ == "__main__":
    app.secret_key = 'super secret key'
    app.config['SESSION_TYPE'] = 'filesystem'


    app.run(debug=True)
