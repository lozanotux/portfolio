from django.shortcuts import render, redirect
from django.contrib.auth import authenticate, login
from django.contrib.auth import views as auth_views
from django.contrib.auth.mixins import LoginRequiredMixin
from django.contrib.auth.models import User
from django.views.generic import View
from .mixins import AuthRedirectMixin


class LoginView(AuthRedirectMixin, View):
	def get(self, request, *args, **kwargs):
		if str(request.user) == 'AnonymousUser':
			context = {}
			return render(request, 'login/login.html', context)
		else:
			return redirect('/')

	def post(self, request, *args, **kwargs):
		action = request.POST.get('action', None)
		username = request.POST.get('username', None)
		password = request.POST.get('password', None)

		if action == 'signup':
			user = User.objects.create_user(username=username,
											password=password)
			user.save()
		elif action == 'login':
			user = authenticate(username=username, password=password)
			login(request, user)
			return redirect('/')


class LogoutView(LoginRequiredMixin, auth_views.LogoutView):
    """ Logout view."""

    next_page = '/'
