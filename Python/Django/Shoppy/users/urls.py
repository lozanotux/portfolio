from django.urls import path
from . import views


urlpatterns = [
    path('login/', views.LoginView.as_view(), name='authentication'),
    path('logout/', views.LogoutView.as_view(), name='logout'),
]
