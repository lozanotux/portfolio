""" Initialize the app.
"""
from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask_login import LoginManager

# Global Variables
db = SQLAlchemy()

def create_app():
    """ Construct the core application.

        Returns:
            app: Flask application
    """
    app = Flask(__name__)

    # Application Configuration
    app.config.from_object('config.DevelopmentConfig')

    db.init_app(app)

    # Initialize flask_login
    login_manager = LoginManager()
    """ With that way whenever someone isn't  authenticated they will be
        automatically redirected to the login view, so they can login.
    """
    login_manager.login_view = 'auth_bp.login'
    login_manager.init_app(app)

    """ User Loader is what flask_login uses to actually find the user
        whose session is currently active, so basically what happens is
        flask_login will create a cookie when the user is logged in and
        inside that cookie will be the User ID.
        So every time the User performs a request on your app it sends the
        cookie along with our request so flask_login looks in that cookie,
        looks for the User ID and then takes that and then uses this User
        Loader that we write to actually find the user in your database and
        that is how flask_login creates the connection between the cookie in
        your actual data in the database so that's part of where the UserMixin
        comes in and the other part is the User Loader. 
    """
    # The import occurs here to avoid circular import with db variable
    from .models.user import User

    @login_manager.user_loader
    def load_user(user_id):
        """ This function is used to search a user in the database

            Args:
                user_id (int or string): the user ID to search the correct User
                                         taken from the cookie.
            Returns:
                User: the user searched in the database through the user_id

        """
        return User.query.get(int(user_id))

    with app.app_context():
        # Import parts of our application
        from .controllers.index import index_bp
        # Another way to import a blueprint is rename it with AS keyword
        from .controllers.auth import auth_bp as authentication_blueprint
        from .controllers.about import about_bp
        app.register_blueprint(index_bp)
        # Maybe in this way is more clear to understood
        app.register_blueprint(authentication_blueprint)
        app.register_blueprint(about_bp)

        return app