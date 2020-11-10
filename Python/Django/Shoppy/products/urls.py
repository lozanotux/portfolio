from django.urls import path
from . import views


urlpatterns = [
    path('', views.ProductList.as_view(), name='list_products'),
    path('product/<int:pk>/', views.ProductDetail.as_view(), name='product_detail'),
    path('product/new', views.new_product, name='new_product'),
]
