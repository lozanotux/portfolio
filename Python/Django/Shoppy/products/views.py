from django.shortcuts import render, get_object_or_404, redirect
from django.contrib.auth.decorators import login_required
from django.views.generic import ListView, DetailView

from .models import Product
from .forms import ProductForm


class ProductList(ListView):
	model = Product


class ProductDetail(DetailView):
	model = Product


@login_required(login_url='/login/')
def new_product(request):
	if request.method == 'POST':
		form = ProductForm(request.POST, request.FILES)
		if form.is_valid():
			product = form.save()
			product.save()
			return redirect('/')
	else:
		form = ProductForm()

	return render(
		request=request,
		template_name='new_product.html',
		context = {
			'form': form
		}
	)
