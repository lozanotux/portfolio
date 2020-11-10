from urllib import request

url = "http://lorempixel.com"
tipos = [
    "abstract", "animals", "business", "cats", "city",
    "food", "nightlife", "fashion", "people", "nature",
    "sports", "technics", "transport",
]

ancho = input('Cual es el ancho?: ')
alto  = input('Cual es el alto?: ')
tipo = input('Cual es el tipo?: ')

for img in range(10):
    url_req = "{}/{}/{}/{}/{}".format(
        url,
        ancho,
        alto,
        tipos[int(tipo)],
        img
    )
    resultado = request.urlopen(url_req)
    lectura = resultado.read()

    f = open("holder_{}.jpg".format(img), "wb")
    f.write(lectura)
    f.close()
