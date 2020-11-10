from django.urls import path

from .views import (
    CourseList,
    CourseDetail,
    CourseCreation,
    CourseUpdate,
    CourseDelete
)


urlpatterns = [
    path('', CourseList.as_view(), name='list'),
    path('<int:pk>/', CourseDetail.as_view(), name='detail'),
    path('nuevo/', CourseCreation.as_view(), name='new'),
    path('editar/<int:pk>/', CourseUpdate.as_view(), name='edit'),
    path('borrar/<int:pk>/', CourseDelete.as_view(), name='delete'),

]
