from flask import Flask, jsonify, make_response
from flask_restful import Resource, Api, abort, request
from auth import auth_required

app = Flask(__name__)
api = Api(app)

books = {
    '1': {
        'titulo': 'Cien años de soledad',
        'id_autor': 2,
        'id_genero': 3
    },
    '2': {
        'titulo': 'De animales a dioses',
        'id_autor': 1,
        'id_genero': 7
    }
}

allowed_resource_types = [
    "books",
    "authors",
    "genres"
]

def abort_if_book_doesnt_exist(book_id):
    if book_id not in books:
        abort(404, message='El libro con ID {} no existe'.format(book_id))


class Root(Resource):
    def get(self, resource_type):
        if resource_type not in allowed_resource_types:
            return '', 400


class BookList(Resource):
    @auth_required
    def get(self):
        """
            To consume this endpoint, must be authorize the request
            with the next curl invokation:
            curl http://admin:1234@localhost:5000/books
        """
        return jsonify(books)

    def post(self):
        json = request.get_json(force=True)
        # The +1 is for create subsecuent index in the list of books
        index = len(books) + 1 
        books.update( {'{}'.format(index): json } )
        return make_response(jsonify({'message': 'Libro agregado correctamente con ID: ' + str(index)}), 201)


class Book(Resource):
    def get(self, book_id):
        abort_if_book_doesnt_exist(book_id)
        return make_response( jsonify(books[book_id]), 200 )
    
    def put(self, book_id):
        abort_if_book_doesnt_exist(book_id)
        json = request.get_json(force=True)
        books.update( {'{}'.format(book_id): json} )
        return jsonify(books)
    
    def delete(self, book_id):
        abort_if_book_doesnt_exist(book_id)
        del books[ book_id ]
        return jsonify({'message':'Libro con ID {} eliminado correctamente'.format(book_id)})


class Authors(Resource):
    pass


class Genres(Resource):
    pass


api.add_resource(Root, '/<resource_type>')
api.add_resource(BookList, '/books')
api.add_resource(Book, '/books/<book_id>')
api.add_resource(Authors, '/authors')
api.add_resource(Genres, '/genres')


if __name__ == '__main__':
    app.run(debug=True)