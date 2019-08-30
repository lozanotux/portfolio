from flask_wtf import FlaskForm
from wtforms.fields import StringField, SubmitField
from wtforms.validators import Required

class LoginForm(FlaskForm):
    name = StringField('', validators=[Required()])
    room = StringField('', validators=[Required()])
    submit = SubmitField('Enter to the chat room')