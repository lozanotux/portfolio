from flask import make_response, request

def auth_required(func):
    def wrapper(self):
        if request.authorization and request.authorization['username'] == 'admin' and request.authorization['password'] == '1234':
            return func(self)
        
        return make_response('No pudimos verificar su login!', 401, {'WWW-Autencticate': 'Basic reaml="Login Required"'})

    return wrapper