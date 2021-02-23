""" Pip3 dependencies:
    * elasticsearch >= 7.6.0
"""
from datetime import datetime
from elasticsearch import Elasticsearch

es = Elasticsearch()

doc = {
    'author': 'Kimchy',
    'text': 'Elasticsearch cool. Bonsai cool',
    'timestamp': datetime.now(),
}

res = es.index(index="test-index", id=1, body=doc)
print(res['result'])

es.indices.refresh(index="test-index")

res = es.search(index="test-index", body={"query": {"match_all": {}}})
print("Got %d Hits:" % res['hits']['total']['value'])

for hit in res['hits']['hits']:
    print("%(timestamp)s - %(author)s - %(text)s" % hit["_source"])
