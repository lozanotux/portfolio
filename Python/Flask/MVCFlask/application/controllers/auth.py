""" Auth blueprint will have the parts for:
        * Logging in
        * Signing in
        * Logging out
"""
from flask import Blueprint, render_template, redirect, url_for, request, flash
from flask_login import login_user, logout_user, login_required
from werkzeug.security import generate_password_hash, check_password_hash
from ..models.user import User
from .. import db

# Blueprint Configuration
auth_bp = Blueprint('auth_bp', 'auth_bp')

# By default this route is accessed via GET
@auth_bp.route('/login')
def login():
    return render_template('login.html')

""" This other route is similar, but is defined to be accessed via POST,
    we can split the calls in different definitions.
"""
@auth_bp.route('/login', methods=['POST'])
def login_post():
    email = request.form.get('email')
    password = request.form.get('password')
    remember = True if request.form.get('remember') else False

    user = User.query.filter_by(email=email).first()

    # This evaluate if the credentials are correctly
    if not user and not check_password_hash(user.password, password):
        flash('Please check you login credentials and try again')
        return redirect(url_for('auth_bp.login'))
    
    # If credentials are correctly (email and password are OK)... logging in
    login_user(user, remember=remember)

    return redirect(url_for('index_bp.profile'))

@auth_bp.route('/signup')
def signup():
    return render_template('signup.html')

@auth_bp.route('/signup', methods=['POST'])
def signup_post():
    email = request.form.get('email')
    name = request.form.get('name')
    password = request.form.get('password')

    """ The next thing to do is go to make sure that the user doesn't already
        exist in the database because we are trying to sign up here and if the
        user already exists then we want to tell the user that they should be
        trying to login maybe accidentally click sign up instead of login and
        they try to log in with their credentials and they need to be told that
        they need to go to the correct place.
    """
    user = User.query.filter_by(email=email).first() # To get first result

    # If the user var has anything that means a user was found in the database
    if user:
        flash('Email address already exists.')
        return redirect(url_for('auth_bp.signup'))
    
    # If the user not exists, we can create them.
    # And the password is hashed it (encripted).
    new_user = User(email=email, name=name, 
                    password=generate_password_hash(password, method='sha256'))
    
    # Add user to the database
    db.session.add(new_user)
    db.session.commit()

    return redirect(url_for('auth_bp.login'))

@auth_bp.route('/logout')
@login_required
def logout():
    logout_user()
    return redirect(url_for('index_bp.index'))