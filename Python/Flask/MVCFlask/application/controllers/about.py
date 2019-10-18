""" Routes for about page.
"""
from flask import Blueprint, render_template


# Blueprint Configuration
about_bp = Blueprint('about_bp', 'about_bp')

@about_bp.route('/about', methods=['GET'])
def index():
    """ About route.
    """
    return render_template('about.html')