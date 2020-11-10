from django.db import models
from clients.models import Client


class Product(models.Model):
	"""Product model."""
	name = models.CharField(max_length=255)
	description = models.CharField(max_length=255)
	category = models.CharField(max_length=255)
	price = models.DecimalField(max_digits=8, decimal_places=2)
	image = models.ImageField(blank=True)

	def __str__(self):
		"""Return the name of product."""
		return self.name


class Favorite(models.Model):
	user = models.ForeignKey(Client, on_delete=models.RESTRICT)
	product = models.ForeignKey(Product, on_delete=models.RESTRICT)

	class Meta:
		verbose_name = 'Favorite'
		verbose_name_plural = 'Favorites'

	def __str__(self):
		return '%s %s'.format(self.user.name, self.product.name)
