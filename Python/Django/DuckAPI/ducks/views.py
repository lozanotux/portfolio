from rest_framework import generics
from .models import Duck
from .serializers import DuckSerializer
from django.shortcuts import get_object_or_404


class DuckList(generics.ListCreateAPIView):
    queryset = Duck.objects.all()
    serializer_class = DuckSerializer

    def get_object(self):
        queryset = self.get_queryset()
        obj = get_object_or_404(
            queryset,
            pk=self.kwargs['pk'],
        )
        return obj
