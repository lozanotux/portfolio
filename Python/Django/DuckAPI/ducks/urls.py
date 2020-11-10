from django.urls import path
from ducks.views import *

urlpatterns = [
    path('ducks/', DuckList.as_view(), name='ducks'),
]
