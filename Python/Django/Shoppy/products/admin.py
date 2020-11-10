from django.contrib import admin
from .models import Product, Favorite

@admin.register(Product)
class ProductAdmin(admin.ModelAdmin):
	"""Product model admin."""
	list_display = ('name', 'category', 'description', 'price',)
	list_filter = ('category',)

@admin.register(Favorite)
class FavoriteAdmin(admin.ModelAdmin):
	list_display = ('user', 'product',)
	list_filter = ('user', 'product',)
