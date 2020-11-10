from django.shortcuts import redirect


class AuthRedirectMixin(object):
	def get(self, request, *args, **kwargs):
		if request.user.is_authenticated():
			return redirect('/')
		else:
			return super(AuthRedirectMixin, self).get(self, request, *args, **kwargs)
