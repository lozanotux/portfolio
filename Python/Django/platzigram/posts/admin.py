from django.contrib import admin

# Custom models
from posts.models import Post


class PostAdmin(admin.ModelAdmin):
    """Post model admin."""
    list_display = ('user', 'title', 'photo', )


admin.site.register(Post, PostAdmin)