""" Routes for main page.
"""
from flask import Blueprint, render_template
# login_required protects if you that is supposed to be for logged in users only
from flask_login import login_required, current_user

# Blueprint Configuration
index_bp = Blueprint('index_bp', 'index_bp')

@index_bp.route('/', methods=['GET'])
def index():
    """ Homepage Route definition.

        Returns:
            The template named index.html rendered 
    """
    return render_template('index.html')

@index_bp.route('/profile')
@login_required
def profile():
    """ Profile Route definition.
        
        Returns:
            The template named profile.html rendered 
    """
    # current_user represents the object of the User (model)
    return render_template('profile.html', name=current_user.name)