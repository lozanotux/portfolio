"""socialproject URL Configuration

    https://python-social-auth.readthedocs.io/en/latest/configuration/django.html
"""
from django.contrib import admin
from django.urls import path, include
from django.views.generic import TemplateView
from django.contrib.auth import views

urlpatterns = [
    path('admin/', admin.site.urls),
    # Python Social Auth URLs
    path('', include('social_django.urls', namespace='social')),
    path('users/login/', TemplateView.as_view(template_name="home.html"), name="home"),
    path('users/logout/', views.LogoutView.as_view(), name="user-logout"),
]
