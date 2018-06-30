from models import *

user1 = User(name='Gabriel', password='123456', email='menendez.gabriel@gmail.com')
user2 = User(name='Juan Carlos', password='123456', email='jc.purrinos@gmail.com')
#db.session.add(user1)
#db.session.commit()


student1 = Student(name='Gabriel', surname='Menendez', dni=34480651,\
                   age=29, nationality='Argentino', division='E',\
                   lective_year='2018')

student2 = Student(name='Juan Pablo', surname='Lozano', dni=34875921,\
                   age=28, nationality='Argentino', division='E',\
                   lective_year='2018')

student3 = Student(name='Jonathan', surname='Sierra', dni=12345678,\
                   age=28, nationality='Argentino', division='E',\
                   lective_year='2018')


materia1 = Subject(name='Gestion de proyectos informaticos')
materia2 = Subject(name='Estructura de las Organizaciones')
materia3 = Subject(name='Disenio de sistemas')
materia4 = Subject(name='Analisis de sistemas')
materia1.subjects.append(student1)
materia2.subjects.append(student1)
materia1.subjects.append(student2)
materia2.subjects.append(student2)
materia1.subjects.append(student3)
materia2.subjects.append(student3)


academicstate = AcademicState(notes=9, repeating="no", status="Aprobado",\
                              correlative=1, student_name="Gabriel", \
                              subject_name='Gestion de Proyectos Informaticos')

academicstate1 = AcademicState(notes=7, repeating="no", status="Aprobado",\
                              correlative=0, student_name="Gabriel", \
                              subject_name='Estructura de las Organizaciones')

academicstate2 = AcademicState(notes=8, repeating="no", status="Aprobado",\
                              correlative=1, student_name="Jonathan", \
                              subject_name='Gestion de Proyectos Informaticos')

academicstate3 = AcademicState(notes=9, repeating="no", status="Aprobado",\
                              correlative=0, student_name="Jonathan", \
                              subject_name='Estructura de las Organizaciones')

academicstate4 = AcademicState(notes=9, repeating="no", status="Aprobado",\
                              correlative=1, student_name="Juan Pablo", \
                              subject_name='Gestion de Proyectos Informaticos')

academicstate5 = AcademicState(notes=8, repeating="no", status="Aprobado",\
                              correlative=0, student_name="Juan Pablo", \
                              subject_name='Estructura de las Organizaciones')


db.session.add(user1)
db.session.add(user2)
db.session.add(student1)
db.session.add(student2)
db.session.add(student3)
db.session.add(materia1)
db.session.add(materia2)
db.session.add(materia3)
db.session.add(materia4)
db.session.add(academicstate)
db.session.add(academicstate1)
db.session.add(academicstate2)
db.session.add(academicstate3)
db.session.add(academicstate4)
db.session.add(academicstate5)
#subject.subjects.append(user1)
db.session.commit()

