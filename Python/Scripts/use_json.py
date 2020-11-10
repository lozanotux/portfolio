import json
from urllib import request

url = 'http://www.json-generator.com/api/json/get/clsOIQHWCq?ident=2'

r = request.urlopen(url)
# Convert JSON (string) into Python Objects
data = json.loads(r.read())

print(data[1]["friends"][2]["name"])

# Convert Python Objects into JSON (string)
sjson = json.dumps(data)
print(type(sjson))