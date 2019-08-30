from flask import session, redirect, url_for, render_template, request
from . import main
from .. import connected_users
from .forms import LoginForm
import random

chat_colors = ["#5586e5",
               "#1f7aec",
               "#ffa97a",
               "#b4876e",
               "#029d00",
               "#dfb610",
               "#8b7add",
               "#ba33dc",
               "#3bdec3",
               "#35cd96",
               "#91ab01",
               "#fe7c7f",
               "#fe7c7f",
               "#cf0b23",
               "#607a77",
               "#5c7184"]

used_colors = chat_colors

@main.route('/', methods=['GET', 'POST'])
def index():
    form = LoginForm()
    global used_colors
    if form.validate_on_submit():
        session['name'] = form.name.data
        session['room'] = form.room.data
        # Color Selection For Each User
        color_id = random.randint(0, len(used_colors))
        if color_id == 0:
            used_colors = chat_colors
            color_id = random.randint(0, len(used_colors))
        session['color'] = used_colors[color_id]
        used_colors.remove(used_colors[color_id])
        return redirect(url_for('.chat'))
    elif request.method == 'GET':
        form.name.data = session.get('name', '')
        form.room.data = session.get('room', '')
    return render_template('index.html', form=form)


@main.route('/chat')
def chat():
    name = session.get('name', '')
    room = session.get('room', '')
    if name == '' or room == '':
        return redirect(url_for('.index'))
    return render_template('chat.html', name=name, room=room)