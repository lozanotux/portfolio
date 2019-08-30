from flask import session
from flask_socketio import emit, join_room, leave_room
from .. import socketio, connected_users
from datetime import datetime

@socketio.on('joined', namespace='/chat')
def joined(message):
    room = session.get('room')
    name = session.get('name')
    join_room(room)
    if name not in connected_users: connected_users.append(name)
    emit('status', {'msg': session.get('name') + ' joined the room', 'users': connected_users}, room=room)


@socketio.on('text', namespace='/chat')
def text(message):
    room = session.get('room')
    time_stamp = datetime.now().strftime("%H:%M:%S")
    emit('message', {'msg': '<span class="personName" style="color: ' + session.get('color') + ';">' + session.get('name') + '</span><br><span class="personSay">' + message['msg'] + '</span><span class="messageTime">' + time_stamp + '</span>'}, room=room)

@socketio.on('left', namespace='/chat')
def left(message):
    room = session.get('room')
    name = session.get('name')
    leave_room(room)
    if name in connected_users: connected_users.remove(name)
    emit('status', {'msg': session.get('name') + ' left the room', 'users': connected_users}, room=room)
